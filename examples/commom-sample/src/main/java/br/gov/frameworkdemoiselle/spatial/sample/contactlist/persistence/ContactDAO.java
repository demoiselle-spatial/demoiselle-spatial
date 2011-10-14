package br.gov.frameworkdemoiselle.spatial.sample.contactlist.persistence;

import java.util.List;

import javax.inject.Inject;

import org.geotools.geometry.jts.JTS;
import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.spatial.sample.contactlist.domain.Contact;
import br.gov.frameworkdemoiselle.template.JPACrud;

import com.vividsolutions.jts.geom.Envelope;

public class ContactDAO extends JPACrud<Contact, Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
	@SuppressWarnings("unchecked")
	public List<Contact> findAllByExtent(Envelope envelope)
	{
		return getEntityManager().createQuery("from Contact c where within(c.point, :filter) = true").setParameter("filter", JTS.toGeometry(envelope)).getResultList(); 
	}
	
}