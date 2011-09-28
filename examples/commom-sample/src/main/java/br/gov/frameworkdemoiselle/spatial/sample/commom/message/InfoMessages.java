package br.gov.frameworkdemoiselle.spatial.sample.commom.message;

import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.Message;

public interface InfoMessages {

	final Message CLIENT_DELETE_OK = new DefaultMessage("{client-delete-ok}");

    final Message CLIENT_INSERT_OK = new DefaultMessage("{client-insert-ok}");

    final Message CLIENT_UPDATE_OK = new DefaultMessage("{client-update-ok}");
}
