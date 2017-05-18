package bean;

import bean.ReclamationItem;
import bean.Technicien;
import bean.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-18T16:33:03")
@StaticMetamodel(Reclamation.class)
public class Reclamation_ { 

    public static volatile SingularAttribute<Reclamation, User> traitor;
    public static volatile ListAttribute<Reclamation, ReclamationItem> reclamationItems;
    public static volatile SingularAttribute<Reclamation, Long> id;
    public static volatile SingularAttribute<Reclamation, Date> dateR;
    public static volatile SingularAttribute<Reclamation, User> declarator;
    public static volatile SingularAttribute<Reclamation, Technicien> technicien;

}