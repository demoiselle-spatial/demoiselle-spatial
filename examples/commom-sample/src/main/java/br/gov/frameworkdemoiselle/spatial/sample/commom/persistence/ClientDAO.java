package br.gov.frameworkdemoiselle.spatial.sample.commom.persistence;

import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.spatial.sample.commom.domain.Client;
import br.gov.frameworkdemoiselle.template.JPACrud;

public class ClientDAO extends JPACrud<Client, Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
}