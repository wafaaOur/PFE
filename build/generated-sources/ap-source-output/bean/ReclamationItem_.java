package bean;

import bean.Produit;
import bean.Reclamation;
import bean.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-17T19:17:50")
@StaticMetamodel(ReclamationItem.class)
public class ReclamationItem_ { 

    public static volatile SingularAttribute<ReclamationItem, Produit> produit;
    public static volatile SingularAttribute<ReclamationItem, String> description;
    public static volatile SingularAttribute<ReclamationItem, Long> id;
    public static volatile SingularAttribute<ReclamationItem, Integer> priority;
    public static volatile SingularAttribute<ReclamationItem, Reclamation> reclamation;
    public static volatile SingularAttribute<ReclamationItem, User> user;

}