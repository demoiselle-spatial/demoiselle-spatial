package sample.demoiselle_spatial_example.sample.contactlist.persistence;

import java.util.List;

import javax.inject.Inject;

import org.geotools.geometry.jts.JTS;
import org.slf4j.Logger;

import sample.demoiselle_spatial_example.sample.contactlist.domain.Casa;
import br.gov.frameworkdemoiselle.spatial.template.JPASpatialDAO;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

/***
 * 
 * @author fredestrela
 *
 */
public class CasaDAO extends JPASpatialDAO<Casa, Long>{

	private static final long serialVersionUID = 1L;
	
	@Inject
	@SuppressWarnings("unused")
	private Logger logger;
	
	@SuppressWarnings("unchecked")
	public List<Casa> findAllByExtent(Envelope envelope)
	{
		Geometry geom = JTS.toGeometry(envelope);
		geom.setSRID(4326);
		
		return getEntityManager().createQuery("from Casa c where within(c.point, :filter) = true").setParameter("filter",geom ).getResultList(); 
	}

}
