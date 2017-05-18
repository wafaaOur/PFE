package bean;

import bean.Device;
import bean.Division;
import bean.ReclamationItem;
import bean.Service;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-17T19:17:50")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Integer> nbrCnx;
    public static volatile SingularAttribute<User, Integer> role;
    public static volatile ListAttribute<User, Device> devices;
    public static volatile ListAttribute<User, ReclamationItem> reclamationItems;
    public static volatile SingularAttribute<User, String> login;
    public static volatile SingularAttribute<User, String> nom;
    public static volatile SingularAttribute<User, Division> division;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Boolean> mdpChanged;
    public static volatile SingularAttribute<User, Boolean> blocked;
    public static volatile SingularAttribute<User, Service> service;
    public static volatile SingularAttribute<User, String> tel;
    public static volatile SingularAttribute<User, String> prenom;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, Long> numBureau;

}