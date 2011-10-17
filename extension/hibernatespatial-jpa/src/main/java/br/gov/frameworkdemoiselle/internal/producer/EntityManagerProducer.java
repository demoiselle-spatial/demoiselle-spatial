package br.gov.frameworkdemoiselle.internal.producer;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.DemoiselleException;
import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.configuration.Configuration;
import br.gov.frameworkdemoiselle.internal.configuration.EntityManagerConfig;
import br.gov.frameworkdemoiselle.internal.proxy.EntityManagerProxy;
import br.gov.frameworkdemoiselle.util.ResourceBundle;

/**
 * <p>
 * Factory class responsible to produces instances of EntityManager. Produces instances based on informations defined in
 * persistence.xml, demoiselle.properties or @PersistenceUnit annotation.
 * </p>
 * TODO allow users to define EntityManager's scope using demoiselle.properties
 */
@RequestScoped
public class EntityManagerProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;

	@Inject
	@Name("demoiselle-jpa-bundle")
	private ResourceBundle bundle;

	private final Map<String, EntityManager> cache = Collections.synchronizedMap(new HashMap<String, EntityManager>());

	@Inject
	private EntityManagerFactoryProducer factory;

	/**
	 * <p>
	 * Default EntityManager factory. Tries two strategies to produces EntityManager instances.
	 * <li>The first one is based on informations available on demoiselle properties file
	 * ("frameworkdemoiselle.persistence.unit.name" key).</li>
	 * <li>The second one is based on persistence.xml file. If exists only one Persistence Unit defined, this one is
	 * used.</li>
	 * 
	 * @param config
	 *            Suplies informations about EntityManager defined in properties file.
	 * @return Produced EntityManager.
	 */
	@Default
	@Produces
	public EntityManager create(InjectionPoint ip, EntityManagerConfig config) {
		String persistenceUnit = getPersistenceUnit(ip, config);
		return new EntityManagerProxy(persistenceUnit);
	}

	public EntityManager getEntityManager(String persistenceUnit) {
		EntityManager entityManager = null;

		if (cache.containsKey(persistenceUnit)) {
			entityManager = cache.get(persistenceUnit);

		} else {
			entityManager = factory.create(persistenceUnit).createEntityManager();
			entityManager.setFlushMode(FlushModeType.AUTO);
			// entityManager.setFlushMode(FlushModeType.COMMIT);

			cache.put(persistenceUnit, entityManager);
			this.logger.info(bundle.getString("entity-manager-was-created", persistenceUnit));
		}

		return entityManager;
	}

	private String getPersistenceUnit(InjectionPoint ip, EntityManagerConfig config) {
		String persistenceUnitName;

		if (ip != null && ip.getAnnotated().isAnnotationPresent(Name.class)) {
			persistenceUnitName = ip.getAnnotated().getAnnotation(Name.class).value();

		} else {
			persistenceUnitName = getFromProperties(config);

			if (persistenceUnitName == null) {
				persistenceUnitName = getFromXML();
			}
		}

		return persistenceUnitName;
	}

	/**
	 * Tries to get persistence unit name from demoiselle.properties.
	 * 
	 * @param config
	 *            Configuration containing persistence unit name.
	 * @return Persistence unit name.
	 */
	private String getFromProperties(EntityManagerConfig config) {
		String persistenceUnit = config.getPersistenceUnitName();

		if (persistenceUnit != null) {
			this.logger.debug(bundle.getString("getting-persistence-unit-from-properties",
					Configuration.DEFAULT_RESOURCE));
		}

		return persistenceUnit;
	}

	/**
	 * Uses persistence.xml to get informations about which persistence unit to use. Throws DemoiselleException if
	 * more than one Persistence Unit is defined.
	 * 
	 * @return Persistence Unit Name
	 */
	private String getFromXML() {
		Set<String> persistenceUnits = factory.getCache().keySet();

		if (persistenceUnits.size() > 1) {
			throw new DemoiselleException(bundle.getString("more-than-one-persistence-unit-defined",
					Name.class.getSimpleName()));
		} else {
			return persistenceUnits.iterator().next();
		}
	}

	@PostConstruct
	public void init() {
		for (String persistenceUnit : factory.getCache().keySet()) {
			getEntityManager(persistenceUnit);
		}
	}

	@PreDestroy
	public void close() {
		for (EntityManager entityManager : cache.values()) {
			entityManager.close();
		}

		cache.clear();
	}

	public Map<String, EntityManager> getCache() {
		return cache;
	}
}
