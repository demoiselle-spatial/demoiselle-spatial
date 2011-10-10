package br.gov.frameworkdemoiselle.spatial.sample.commom.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.ol4jsf.util.WKTFeaturesCollection;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.spatial.sample.commom.business.ContactBC;
import br.gov.frameworkdemoiselle.spatial.sample.commom.domain.Contact;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

import com.vividsolutions.jts.geom.Geometry;

@ViewController
@NextView("/contact_edit.xhtml")
@PreviousView("/contact_list.xhtml")
public class ContactListMB extends AbstractListPageBean<Contact, Long> {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private ContactBC bc;
	
	@Override
	protected List<Contact> handleResultList() {
		
		return this.bc.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
	
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);

			if (delete) {
				bc.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	
	}
	
	/**\
	 * 
	 * TODO Este ponto pode virar um metodo generico.
	 * Reflection no datamodel obtendo apenas os campos {@link Geometry}
	 *
	 * @return
	 */
	public String getResultFeatureList() {
		
		 List<Contact> beans = this.getResultList();
		 WKTFeaturesCollection<Geometry> wktFeatures = new WKTFeaturesCollection<Geometry>();
		 
		 for (Contact client : beans) {			 
			 wktFeatures.addFeature(client.getPoint());	 
		}
		
		return wktFeatures.toMap();
	}

}
