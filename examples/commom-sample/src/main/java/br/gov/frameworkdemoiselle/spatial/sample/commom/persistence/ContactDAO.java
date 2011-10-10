package br.gov.frameworkdemoiselle.spatial.sample.commom.persistence;

import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.spatial.sample.commom.domain.Contact;
import br.gov.frameworkdemoiselle.template.JPACrud;

public class ContactDAO extends JPACrud<Contact, Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
}