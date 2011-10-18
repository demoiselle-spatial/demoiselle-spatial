package br.gov.frameworkdemoiselle.spatial.sample.contactlist.view;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ol4jsf.util.WKTFeaturesCollection;

/**
 *
 * @author ranophoenix
 */
@Model
public class QueryViewManager {

    @Inject
    EntityManager em;
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    private String wkts;

    public String getWkts() {
        return wkts;
    }

    public void setWkts(String wkts) {
        this.wkts = wkts;
    }
    private String qryLanguage = "POSTGIS";

    public String getQryLanguage() {
        return qryLanguage;
    }

    public void setQryLanguage(String qryLanguage) {
        this.qryLanguage = qryLanguage;
    }

    @SuppressWarnings("unchecked")
    public void executeQuery() {
        try {
            WKTFeaturesCollection<String> wktFeatures = new WKTFeaturesCollection<String>();
            Query q;
            if ("POSTGIS".equals(qryLanguage)) {
                q = em.createNativeQuery(query); 
            } else {
                q = em.createQuery(query);
            }
            List<String> result = (List<String>) q.getResultList();
            wktFeatures.addAllFeatures(result);

            setWkts(wktFeatures.toMap());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
    }
}
