package br.gov.frameworkdemoiselle.spatial.sample.contactlist.business;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.Startup;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.spatial.sample.contactlist.domain.Contact;
import br.gov.frameworkdemoiselle.spatial.sample.contactlist.message.InfoMessages;
import br.gov.frameworkdemoiselle.spatial.sample.contactlist.persistence.ContactDAO;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;

import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class ContactBC extends DelegateCrud<Contact, Long, ContactDAO> {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
    private MessageContext messageContext;
	

	@Override
	public void delete(Long id) {
		super.delete(id);
		
		 messageContext.add(InfoMessages.CONTACT_DELETE_OK,id);
	}


	@Override
	public void insert(Contact bean) {
		
//		GeocodingResponse response = reverseGeocoding.setLocation((Point)bean.getPoint()).search();
		
		super.insert(bean);
		
		 messageContext.add(InfoMessages.CONTACT_INSERT_OK, bean.getName());
	}


	@Override
	public void update(Contact bean) {
		super.update(bean);
		
		 messageContext.add(InfoMessages.CONTACT_UPDATE_OK,bean.getName());
	}
	
	@Startup
	@Transactional
	public void load() {
		if (findAll().isEmpty()) {
			try {
				insert(new Contact("Rafael Soto", "2021-9833", "rafael.soto@serpro.gov.br", new WKTReader().read("POINT(-38.48760780000001 -12.9710208)")));
				insert(new Contact("Asdrubal da Silva", "4861-1313", "asdrubal@jetsync.com", new WKTReader().read("POINT(133.59375 -25.3125)")));
				insert(new Contact("Fulano Pereira", "3245-9874", "fulano@recopany.com", new WKTReader().read("POINT(-73.125 40.078125)")));
				insert(new Contact("Sicrano Fernandez", "3245-9874", "sicrano.fernandez@recopany.com", new WKTReader().read("POINT(-5.625 37.96875)")));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
	}

}