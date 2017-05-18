package bean;

import bean.Service;
import bean.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-18T11:07:56")
@StaticMetamodel(Division.class)
public class Division_ { 

    public static volatile SingularAttribute<Division, String> nomDiv;
    public static volatile SingularAttribute<Division, User> chef;
    public static volatile SingularAttribute<Division, Long> id;
    public static volatile ListAttribute<Division, Service> services;
    public static volatile ListAttribute<Division, User> users;

}