package br.gov.frameworkdemoiselle.spatial.sample.commom.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.ol4jsf.util.WKTFeaturesCollection;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.spatial.sample.commom.business.ClientBC;
import br.gov.frameworkdemoiselle.spatial.sample.commom.domain.Client;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

import com.vividsolutions.jts.geom.Geometry;

@ViewController
@NextView("/client_edit.xhtml")
@PreviousView("/client_list.xhtml")
public class ClientListMB extends AbstractListPageBean<Client, Long> {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private ClientBC bc;
	
	@Override
	protected List<Client> handleResultList() {
		
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
		
		 List<Client> beans = this.getResultList();
		 WKTFeaturesCollection<Geometry> wktFeatures = new WKTFeaturesCollection<Geometry>();
		 
		 for (Client client : beans) {			 
			 wktFeatures.addFeature(client.getPoint());	 
		}
		
		return wktFeatures.toMap();
	}

}
