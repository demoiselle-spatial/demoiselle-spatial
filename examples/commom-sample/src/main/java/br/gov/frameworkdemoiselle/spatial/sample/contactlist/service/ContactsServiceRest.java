package br.gov.frameworkdemoiselle.spatial.sample.contactlist.service;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.gov.frameworkdemoiselle.component.georest.template.DelegateGeoRest;
import br.gov.frameworkdemoiselle.spatial.sample.contactlist.domain.Contact;
import br.gov.frameworkdemoiselle.spatial.sample.contactlist.persistence.ContactDAO;

@Path("contact")
@Produces("application/json")
public class ContactsServiceRest extends DelegateGeoRest<Contact, Long, ContactDAO> {

	private static final long serialVersionUID = 1L;


}
