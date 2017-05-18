package bean;

import bean.Division;
import bean.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-17T19:17:50")
@StaticMetamodel(Service.class)
public class Service_ { 

    public static volatile SingularAttribute<Service, Division> division;
    public static volatile SingularAttribute<Service, User> chefserv;
    public static volatile SingularAttribute<Service, Long> id;
    public static volatile SingularAttribute<Service, String> nomService;
    public static volatile ListAttribute<Service, User> users;

}