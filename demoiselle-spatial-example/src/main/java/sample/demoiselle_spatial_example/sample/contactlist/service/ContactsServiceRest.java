package sample.demoiselle_spatial_example.sample.contactlist.service;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.gov.frameworkdemoiselle.component.georest.template.DelegateGeoREST;
import sample.demoiselle_spatial_example.sample.contactlist.domain.Contact;
import sample.demoiselle_spatial_example.sample.contactlist.persistence.ContactDAO;

@Path("contact")
@Produces("application/json")
public class ContactsServiceRest extends DelegateGeoREST<Contact, Long, ContactDAO> {

	private static final long serialVersionUID = 1L;


}
