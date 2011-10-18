#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.sample.contactlist.service;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.gov.frameworkdemoiselle.component.georest.template.DelegateGeoREST;
import ${package}.sample.contactlist.domain.Contact;
import ${package}.sample.contactlist.persistence.ContactDAO;

@Path("contact")
@Produces("application/json")
public class ContactsServiceRest extends DelegateGeoREST<Contact, Long, ContactDAO> {

	private static final long serialVersionUID = 1L;


}
