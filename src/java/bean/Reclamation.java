/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author WAFA
 */
@Entity
public class Reclamation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "reclamation")
    private List<ReclamationItem> reclamationItems;
    @ManyToOne
    private User declarator;
    @ManyToOne
    private User traitor;
    @ManyToOne
    private Technicien technicien;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateR;

    
    public Date getDateR() {
        return dateR;
    }

    public void setDateR(Date dateR) {
        this.dateR = dateR;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
       
    public List<ReclamationItem> getReclamationItems() {
        return reclamationItems;
    }

    public void setReclamationItems(List<ReclamationItem> reclamationItems) {
        this.reclamationItems = reclamationItems;
    }

    public User getDeclarator() {
        return declarator;
    }

    public void setDeclarator(User declarator) {
        this.declarator = declarator;
    }

    public User getTraitor() {
        return traitor;
    }

    public void setTraitor(User traitor) {
        this.traitor = traitor;
    }

    public Technicien getTechnicien() {
        return technicien;
    }

    public void setTechnicien(Technicien technicien) {
        this.technicien = technicien;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reclamation)) {
            return false;
        }
        Reclamation other = (Reclamation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Reclamation[ id=" + id + " ]";
    }
    
}
