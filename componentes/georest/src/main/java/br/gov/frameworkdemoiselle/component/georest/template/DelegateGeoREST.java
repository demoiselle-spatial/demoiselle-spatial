package br.gov.frameworkdemoiselle.component.georest.template;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.gov.frameworkdemoiselle.component.georest.GeoRESTCRUDException;
import br.gov.frameworkdemoiselle.component.georest.geojson.GeoJSONBuilder;
import br.gov.frameworkdemoiselle.component.georest.geojson.GeoJSONBuilderImpl;
import br.gov.frameworkdemoiselle.component.georest.model.GeoJSONFeature;
import br.gov.frameworkdemoiselle.component.georest.model.GeoJSONFeatureCollection;
import br.gov.frameworkdemoiselle.spatial.component.feature.BeanSimpleFeatureConverter;
import br.gov.frameworkdemoiselle.spatial.template.JPASpatialDAO;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Reflections;

@Produces("application/json")
public class DelegateGeoREST<T, I, C extends JPASpatialDAO<T, I>> implements GeoRESTCRUD<I> {

	private static final long serialVersionUID = 1L;

	private Class<C> delegateClass;

	private C delegate;
	
	//TODO Needs to resolve a injection point
	private GeoJSONBuilder geoJSONBuilder = new GeoJSONBuilderImpl();

	@Override
	public void delete(final I id) {
		throw new GeoRESTCRUDException("Method not implemented");
	}

	protected C getDelegate() {
		if (this.delegate == null) {
			this.delegate = Beans.getReference(getDelegateClass());
		}
		return this.delegate;
	}

	protected Class<C> getDelegateClass() {
		if (this.delegateClass == null) {
			this.delegateClass = Reflections.getGenericTypeArgument(this.getClass(), 2);
		}
		return this.delegateClass;
	}

	@Override
	@Path("/list")
	@GET
	//@QueryParam("bbox")EnvelopeWrapper bbox
	public GeoJSONFeatureCollection list() {
		 
		//Query query = null;
		List<T> listT = null;
		
//		if(bbox != null)
//		{
//			query = entityManager.createQuery("from " + this.delegateClass.getSimpleName() + " c where within(c, :filter) = true");
//			
//			new HibernateSpatialQuery().setSpatialParameter(query, "filter", bbox.getEnvelope());
//			
//			listT = query.getResultList();
//		}
//		else
			listT = getDelegate().findAll();
		
	   	if(listT == null)
	   		return null;
	   	if(listT.isEmpty())
	   		return null;
	   	
		return geoJSONBuilder.decodeSimpleFeatureList(BeanSimpleFeatureConverter.beanListToSimpleFeatureList(listT));
	}
	

	@Override
	@Path("/{id}")
	@GET
	public GeoJSONFeatureCollection load(@PathParam("id") I id) {
		
		T t = getDelegate().load(id);
		
		return geoJSONBuilder.decodeSimpleFeature(BeanSimpleFeatureConverter.beanToSimpleFeature(t));
	}

	@Override
	public void insert(GeoJSONFeature feature) {
		throw new GeoRESTCRUDException("Method not implemented");
	}

	@Override
	public void update(GeoJSONFeature feature) {
		throw new GeoRESTCRUDException("Method not implemented");
	}

	
	
}