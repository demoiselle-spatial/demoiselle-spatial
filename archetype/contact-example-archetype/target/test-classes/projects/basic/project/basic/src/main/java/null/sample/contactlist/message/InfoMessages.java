package ${package}.sample.contactlist.message;

import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.Message;

public interface InfoMessages {

	final Message CONTACT_DELETE_OK = new DefaultMessage("{contact-delete-ok}");

    final Message CONTACT_INSERT_OK = new DefaultMessage("{contact-insert-ok}");

    final Message CONTACT_UPDATE_OK = new DefaultMessage("{contact-update-ok}");
}
