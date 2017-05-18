package controller;

import bean.Division;
import bean.Service;
import bean.User;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import java.io.IOException;
import service.UserFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("userController")
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private service.UserFacade ejbFacade;
    private List<User> items = null;
    private User selected;
    private String email;
    private Division division;
    private Service service;
    private List<User> chefDivisions;
    private User chefDivselected;

    public List<User> getChefDivisions() {
        return ejbFacade.findNiveauInferieur((int) SessionUtil.getAttribute("role"));
    }

    public void setChefDivisions(List<User> chefDivisions) {
        this.chefDivisions = chefDivisions;
    }

    public User getChefDivselected() {
        if (chefDivselected == null) {
            chefDivselected = new User();

        }
        return chefDivselected;
    }

    public void setChefDivselected(User chefDivselected) {
        this.chefDivselected = chefDivselected;
    }

//    public void Creation(){
//       User userCreator = (User) SessionUtil.getAttribute("user");
//        System.out.println("userCreator---->"+userCreator);
//        System.out.println("selected---->"+selected);
//        System.out.println("division---->"+division);
//        ejbFacade.creationUser(userCreator, selected, division, service);
//    }
    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String seConnecter() {
        int res = ejbFacade.seConnecter(getSelected());
        switch (res) {
            case (-5):
                JsfUtil.addErrorMessage("Veuilliez saisir votre login");
                break;
            case (-4):
                JsfUtil.addErrorMessage("Login n'existe pas");
                break;
            case (-1):
                JsfUtil.addErrorMessage("User deja connecter veuiller vous deconnecter des autre device ou notifier votre admin ");
                break;
            case (-2):
                JsfUtil.addErrorMessage("Utilisateur est bloqué");
                break;
            case (-3):
                JsfUtil.addErrorMessage("Mot de passe incorrect");
                break;
            default:
                try {
                    SessionUtil.setAttribute("user", getSelected());
                    SessionUtil.redirectNoXhtml("/PFE/faces/user/findChefDiv.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        setSelected(null);
        SessionUtil.registerUser(selected);
        return null;
    }

    public void resetPassword() throws IOException {
        int res = getFacade().resetAndSendPassword(email);
        if (res == -1) {
            JsfUtil.addErrorMessage("Impossible d'effectuer l'operation");
        } else if (res == -2) {
            JsfUtil.addErrorMessage("exepton Send");
        } else {
            JsfUtil.addSuccessMessage("Nouveau password envoyer a votre email");
            SessionUtil.redirectNoXhtml("/Rclamer/faces/user/List.xhtml");
        }

    }

    public UserController() {
    }

//    public void findchefDiv() {
//        System.out.println("haa role de sessionUtil ==> " + SessionUtil.getConnectedUser().getRole());
//        return ejbFacade.findNiveauInferieur((int) SessionUtil.getAttribute("role"));
//    }

    public void setItems(List<User> items) {
        this.items = items;
    }

    public User getSelected() {
        if (selected == null) {
            selected = new User();
        }
        return selected;
    }

    public void setSelected(User selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UserFacade getFacade() {
        return ejbFacade;
    }

    public User prepareCreate() {
        selected = new User();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<User> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    selected.setPassword(controller.util.HashageUtil.sha256(selected.getPassword()));
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public User getUser(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<User> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<User> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = User.class)
    public static class UserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.getUser(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof User) {
                User o = (User) object;
                return getStringKey(o.getLogin());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), User.class.getName()});
                return null;
            }
        }

    }

    public UserFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(UserFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
