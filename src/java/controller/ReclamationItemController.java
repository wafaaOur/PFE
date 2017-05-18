package controller;

import bean.Categorie;
import bean.ReclamationItem;
import bean.User;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.ReclamationItemFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import service.UserFacade;

@Named("reclamationItemController")
@SessionScoped
public class ReclamationItemController implements Serializable {

    @EJB
    private service.ReclamationItemFacade ejbFacade;
    @EJB
    private service.UserFacade userFacade;

    private List<ReclamationItem> items = null;
    private ReclamationItem selected;
    private User  selectedUser;

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
   
    private Categorie selectedCategorie;

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
   
    public ReclamationItemController() {
    }

    public ReclamationItem getSelected() {
        if (selected == null) {
            selected = new ReclamationItem();
        }
        return selected;
    }

    public ReclamationItemFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(ReclamationItemFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Categorie getSelectedCategorie() {
        if (selectedCategorie == null) {
            selectedCategorie = new Categorie();
        }
        return selectedCategorie;
    }

    public void setSelectedCategorie(Categorie selectedCategorie) {
        this.selectedCategorie = selectedCategorie;
    }

    public void setSelected(ReclamationItem selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ReclamationItemFacade getFacade() {
        return ejbFacade;
    }

    public ReclamationItem prepareCreate() {
        selected = new ReclamationItem();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ReclamationItemCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ReclamationItemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ReclamationItemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ReclamationItem> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ReclamationItem getReclamationItem(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ReclamationItem> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ReclamationItem> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ReclamationItem.class)
    public static class ReclamationItemControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReclamationItemController controller = (ReclamationItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "reclamationItemController");
            return controller.getReclamationItem(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ReclamationItem) {
                ReclamationItem o = (ReclamationItem) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ReclamationItem.class.getName()});
                return null;
            }
        }

    }

    public void findConnected() {
        userFacade.findConnected(selectedUser);
        
    }

}
