package bean;

import bean.Societe;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-17T19:17:50")
@StaticMetamodel(Marche.class)
public class Marche_ { 

    public static volatile SingularAttribute<Marche, Date> dateDebut;
    public static volatile SingularAttribute<Marche, Long> id;
    public static volatile SingularAttribute<Marche, Societe> societe;

}