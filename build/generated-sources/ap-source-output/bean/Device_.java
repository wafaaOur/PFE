package bean;

import bean.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-17T19:17:50")
@StaticMetamodel(Device.class)
public class Device_ { 

    public static volatile SingularAttribute<Device, Long> id;
    public static volatile SingularAttribute<Device, String> adresseIP;
    public static volatile SingularAttribute<Device, String> adresseMac;
    public static volatile SingularAttribute<Device, String> nom;
    public static volatile SingularAttribute<Device, User> user;

}