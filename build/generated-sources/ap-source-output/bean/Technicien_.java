package bean;

import bean.Societe;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-18T16:33:03")
@StaticMetamodel(Technicien.class)
public class Technicien_ { 

    public static volatile SingularAttribute<Technicien, Long> id;
    public static volatile SingularAttribute<Technicien, String> prenom;
    public static volatile SingularAttribute<Technicien, String> nom;
    public static volatile SingularAttribute<Technicien, Societe> societe;

}