/**
 * 
 */
package sample.demoiselle_spatial_example.sample.contactlist.business;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import sample.demoiselle_spatial_example.sample.contactlist.domain.Casa;
import sample.demoiselle_spatial_example.sample.contactlist.message.InfoMessages;
import sample.demoiselle_spatial_example.sample.contactlist.persistence.CasaDAO;
import br.gov.frameworkdemoiselle.annotation.Startup;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.spatial.template.DelegateSpatialDAO;
import br.gov.frameworkdemoiselle.transaction.Transactional;

import com.vividsolutions.jts.geom.Envelope;

/**
 * @author fredestrela
 *
 */
public class CasaBC extends DelegateSpatialDAO<Casa, Long, CasaDAO>{

	private static final long serialVersionUID = 1L;

	@Inject
	private MessageContext messageContext;

	@Override
	public void delete(Long id) {
		super.delete(id);

		messageContext.add(InfoMessages.CONTACT_DELETE_OK, id);
	}

	@Override
	public void insert(Casa bean) {
		super.insert(bean);

		messageContext.add(InfoMessages.CONTACT_INSERT_OK, bean.getId());
	}

	@Override
	public void update(Casa bean) {
		super.update(bean);

		messageContext.add(InfoMessages.CONTACT_UPDATE_OK, bean.getId());
	}

	public List<Casa> finAllByExtent(Envelope envelope) {
		return getDelegate().findAllByExtent(envelope);
	}
	
	@Startup
	@Transactional
	public void load() throws IOException, ClassNotFoundException {
		if (findAll().isEmpty()) {
//			try {
				
				this.createGeometryColumns();
				
//				Geometry point = new WKTReader().read("POINT(-38.48760780000001 -12.9710208)");
//				point.setSRID(4326);
//				insert(new Contact("Rafael Soto", "2021-9833", "rafael.soto@serpro.gov.br", point));
//				
//				point = new WKTReader().read("POINT(133.59375 -25.3125)");
//				point.setSRID(4326);
//				insert(new Contact("Asdrubal da Silva", "4861-1313", "asdrubal@jetsync.com", point));
//				
//				point = new WKTReader().read("POINT(-73.125 40.078125)");
//				point.setSRID(4326);
//				insert(new Contact("Fulano Pereira", "3245-9874", "fulano@recopany.com", point));
//				
//				point = new WKTReader().read("POINT(-5.625 37.96875)");
//				point.setSRID(4326);				
//				insert(new Contact("Sicrano Fernandez", "3245-9874", "sicrano.fernandez@recopany.com", point));
//			} catch (ParseException e) {				
//				e.printStackTrace();
//			}
		}
	}

	
}
