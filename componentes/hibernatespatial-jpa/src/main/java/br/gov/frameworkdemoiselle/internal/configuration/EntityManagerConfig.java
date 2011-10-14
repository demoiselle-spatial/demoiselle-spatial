package br.gov.frameworkdemoiselle.internal.configuration;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.configuration.Configuration;

@Configuration(prefix = "frameworkdemoiselle.persistence")
public class EntityManagerConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@Name("unit.name")
	private String persistenceUnitName;

	/**
	 * Getter for persistence unit name.
	 */
	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}
}
