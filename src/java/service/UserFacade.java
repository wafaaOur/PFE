/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Division;
import bean.Service;
import bean.User;
import controller.util.EmailUtil;
import controller.util.HashageUtil;
import controller.util.JsfUtil;
import controller.util.RandomStringUtil;
import controller.util.SessionUtil;
import java.util.List; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author WAFA
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "PFEPU")
    private EntityManager em;
    @EJB
    DivisionFacade divisionFacade;
    @EJB
    ServiceFacade serviceFacade;
    @EJB
    UserFacade userFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    @Override
    public User find(Object id) {
        try {
            User user = (User) em.createQuery("select u from User u where u.login = '" + id + "'").getSingleResult();
            if (user != null) {
                return user;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Login incorrect");
        }
        return null;
    }

    public int changePassword(String login, String oldPassword, String newPassword, String newPasswordConfirmation) {
        System.out.println("voila hana dkhalt le service verifierPassword");
        User loadedeUser = find(login);

        if (!newPasswordConfirmation.equals(newPassword)) {
            return -1;
        } else if (!loadedeUser.getPassword().equals(HashageUtil.sha256(oldPassword))) {
            return -2;
        } else if (oldPassword.equals(newPassword)) {
            return -3;
        }
        loadedeUser.setPassword(HashageUtil.sha256(newPassword));
        edit(loadedeUser);
        return 1;
    }

    public void changeData(User user) {
        User loadedUser = find(user.getLogin());
        cloneData(user, loadedUser);
        edit(loadedUser);
    }

    public void cloneData(User userSource, User userDestination) {
        userDestination.setLogin(userSource.getLogin());
        userDestination.setBlocked(userSource.isBlocked());
        userDestination.setEmail(userSource.getEmail());
        userDestination.setNbrCnx(userSource.getNbrCnx());
        userDestination.setNom(userSource.getNom());
        userDestination.setPassword(null);
        userDestination.setPrenom(userSource.getPrenom());
        userDestination.setRole(userSource.getRole());
    }

    public int seConnecter(User user) {
        if (user == null || user.getLogin() == null) {
            JsfUtil.addErrorMessage("Veuilliez saisir votre login");
            return -5;
        } else {
            User loadedUser = find(user.getLogin());
            if (loadedUser == null) {
                return -4;
            } else if (!loadedUser.getPassword().equals(HashageUtil.sha256(user.getPassword()))) {
                if (loadedUser.getNbrCnx() < 3) {
                    System.out.println("hana loadedUser.getNbrCnx() < 3 ::: " + loadedUser.getNbrCnx());
                    loadedUser.setNbrCnx(loadedUser.getNbrCnx() + 1);
                } else if (loadedUser.getNbrCnx() >= 3) {
                    System.out.println("hana loadedUser.getNbrCnx() >= 3::: " + loadedUser.getNbrCnx());
                    loadedUser.setBlocked(true);
                    edit(loadedUser);
                }
                JsfUtil.addErrorMessage("Mot de passe incorrect");
                return -3;
            } else if (loadedUser.isBlocked() == true) {
                JsfUtil.addErrorMessage("Cet utilisateur est bloqué");
                return -2;
            } else {
                loadedUser.setNbrCnx(0);
                System.out.println("haaa loaded.getRole ==> " + loadedUser.getRole());
                SessionUtil.registerUser(clone(loadedUser));
                SessionUtil.setAttribute("role", loadedUser.getRole());
                edit(loadedUser);
                return 1;
            }
        }
    }

    public User clone(User user) {
        User clone = new User();
        clone.setLogin(user.getLogin());
        clone.setBlocked(user.isBlocked());
        clone.setEmail(user.getEmail());
        clone.setMdpChanged(user.isMdpChanged());
        clone.setNbrCnx(user.getNbrCnx());
        clone.setNom(user.getNom());
        clone.setPassword(user.getPassword());
        clone.setPrenom(user.getPrenom());
        clone.setTel(user.getTel());
        clone.setRole(user.getRole());
        clone.setNumBureau(user.getNumBureau());
        return clone;
    }

    public int resetAndSendPassword(String email) {
        User user = findByEmail(email);
        if (user == null) {
            return -1;
        } else {
            String password = RandomStringUtil.generate();
            String msg = "Bienvenu Mr. " + user.getNom() + ",<br/>"
                    + "D'après votre demande, le mot de passe de votre compte reclamation a été réinitialiser, nous avons generer ce mot de passe pour vous.\n"
                    + "<br/><br/>"
                    + "      Nouveau Mot de Passe: <br/><center><b>"
                    + password
                    + "</b></center><br/><br/><b><i>PS:</i></b>  SVP changer ce mot de passe apres que vous avez connecter pour des raison du securité .<br/> Cree votre propre mot de passe";

            try {
                //email de site ,password de ce email,le message,distinataire,l'objet;
                System.out.println("resetAndSendPassword :: befor :: sendMail");
                EmailUtil.sendMail("wafaeour@gmail.com", "wafa", msg, email, "Demande de reanitialisation du mot de pass");
                System.out.println("resetAndSendPassword :: after :: sendMail ");
                user.setBlocked(false);
                user.setPassword(HashageUtil.sha256(password));
                edit(user);
                return 1;
            } catch (javax.mail.MessagingException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(" resetAndSendPassword :: MessagingException");
                return -2;
            }
        }
    }

    private User findByEmail(String email) {
        try {
            return (User) getEntityManager().createQuery("SELECT u FROM u WHERE u.email ='" + email + "'").getSingleResult();
        } catch (Exception e) {
            return null;
        }
       
    }
//    public User findConnected(User user){
//         int res = seConnecter(user);
//         if(res>1){
//             return user;
//         }
//         return null;
//    }

//     public int creationUser(User userCreator,User userNew,Division division,Service service){
//         System.out.println("userCreator.getRole()--->"+userCreator.getRole());
//         if(userCreator.getRole()==1 ){// creation chefDiv
//        //Division division=userCreator.getDivision();
//           division.setChef(userNew);
//            userNew.setRole(2);
//            create(userNew);
//            divisionFacade.edit(division);
//        }
//        else if(userCreator.getRole()==2){ // chefservice
//            //Service service=userCreator.getService();
//            service.setDivision(userCreator.getDivision());
//            userNew.setRole(3);
//            create(userNew);
//            serviceFacade.edit(service);
//        }
//          else if(userCreator.getRole()==3){ // creation user
//            userNew.setRole(4);
//             create(userNew);
//             userFacade.edit(userNew);
//        }
//         return 1;
//    }
    public List<User> findNiveauInferieur(int role) {
        List<User> res = em.createQuery("SELECT u FROM User u WHERE u.role=" + (role + 1)).getResultList();
        System.out.println("ha rektte ==> " + "SELECT u FROM User u WHERE u.role=" + (role + 1));
        System.out.println("o haa res= " + res);
        return res;
    }

//    public List<User> findChefDiv() {
//        System.out.println("hhh");
//        return findNiveauInferieur(2);
//    }
//    public List<User> findChefService(){
//        return findNiveauInferieur(3);
//    }
//    public List<User> findUser(){
//        return findNiveauInferieur(4);
//    }
}
