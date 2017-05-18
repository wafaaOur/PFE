package bean;

import bean.Marche;
import bean.Technicien;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-18T11:07:57")
@StaticMetamodel(Societe.class)
public class Societe_ { 

    public static volatile ListAttribute<Societe, Marche> marches;
    public static volatile SingularAttribute<Societe, String> libelle;
    public static volatile ListAttribute<Societe, Technicien> techniciens;
    public static volatile SingularAttribute<Societe, Long> id;

}