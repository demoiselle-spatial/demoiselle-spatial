package br.gov.frameworkdemoiselle.spatial.template;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import jodd.bean.BeanUtil;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import br.gov.frameworkdemoiselle.DemoiselleSpatialException;
import br.gov.frameworkdemoiselle.annotation.Startup;
import br.gov.frameworkdemoiselle.spatial.query.SpatialQueryArgument;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;

public class JPASpatialDAO<T, I> extends JPACrud<T, I> implements SpatialDAO<T, I>  {

	private static final long serialVersionUID = 1L;

	private String geoColumnName;
	
	private String geoPropertyName;
	
	private Integer geoBeanSRID;
	
	private String idColumnName;
	
	public JPASpatialDAO() {	
		this.geoBeanSRID = SpatialEntityReflections.seekSRIDInClass(this.getBeanClass());	
		this.idColumnName = SpatialEntityReflections.seekIdColumnName(this.getBeanClass());
		this.geoColumnName = SpatialEntityReflections.seekGeometryTypeColumnName(this.getBeanClass());
		this.geoPropertyName = SpatialEntityReflections.seekGeometryPropertyName(this.getBeanClass());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T load(I id, SpatialQueryArgument spatialArgument) {
		
		verifyGeometryProperty();
		
		adjustSRID(spatialArgument);
		
		if(spatialArgument == null)
			return load(id);
		
		if(spatialArgument.getOutputSRID() == null)
			return load(id);
		
		if(geoBeanSRID!= null && spatialArgument.getOutputSRID() == this.geoBeanSRID)
			return load(id);
		
		String jpql = "select this,transform(this.@0,@1) from @2 this where this.@3 = :id";
		jpql = this.setStringQueryJPQLParameters(jpql, 
										  this.geoColumnName,
										  spatialArgument.getOutputSRID(),
										  this.getBeanClass().getName(),
										  this.idColumnName
										  );
		Query query = this.createQuery(jpql);
		query.setParameter("id", id);
		
		Object objResult = query.getSingleResult();
		
		if(objResult == null)
			throw new DemoiselleSpatialException("Object not found " + this.getBeanClass().getName() + " ID: " + id);
		
		Object[] queryResult = (Object[]) objResult;
		
		T beanInstance = (T) queryResult[0];
		BeanUtil.setProperty(beanInstance, this.geoPropertyName, queryResult[1]);
		
		return beanInstance;
	}
	
	@Override
	public List<T> findAll(SpatialQueryArgument spatialArgument) {
		
		this.verifyGeometryProperty();
		
		if(spatialArgument == null)
			return findAll();	
		
		this.adjustSRID(spatialArgument);
		
		StringBuffer jpql = new StringBuffer();
		String finalQuery = null;
		
		//Necessita transformar a saida e Somente cria a query de transform se for diferente
		if(spatialArgument.getOutputSRID() != null && this.geoBeanSRID.intValue() != spatialArgument.getOutputSRID().intValue())
				jpql.append(" select this,transform(this.@geocolumn,@outputsrid) from @beanclass this ");
		else
			    jpql.append(" select this from @beanclass this ");
		
		if(spatialArgument.getExtentFilter() != null)
		{
			//verifica se precisa e consegue transformar o filtro extent de projecao
			if(this.geoBeanSRID != null && this.geoBeanSRID.intValue() != spatialArgument.getExtentFilter().getSrid())
				jpql.append(" where within(this.@geocolumn, transform(:filter,@beansrid)) = true ");
			else
				jpql.append(" where within(this.@geocolumn, :filter) = true ");
		}
		finalQuery = jpql.toString().replace("@geocolumn",  this.geoColumnName);
		if(spatialArgument.getOutputSRID() != null && this.geoBeanSRID.intValue() != spatialArgument.getOutputSRID().intValue())
			finalQuery = finalQuery.replace("@outputsrid", spatialArgument.getOutputSRID().toString());
		
		finalQuery = finalQuery.replace("@beanclass", this.getBeanClass().getName());
		if(this.geoBeanSRID != null)
			finalQuery = finalQuery.replace("@beansrid", this.geoBeanSRID.toString());

		Query query = this.createQuery(finalQuery);
		
		if(spatialArgument.getExtentFilter() != null)
			query.setParameter("filter",spatialArgument.getExtentFilter().getGeometry());
		
		if(spatialArgument.getOutputSRID() != null && this.geoBeanSRID.intValue() != spatialArgument.getOutputSRID().intValue())
		{

			List<T> retorno = new ArrayList<T>();
			List<Object> objResultList = query.getResultList();
			
			if(objResultList != null && !objResultList.isEmpty())
			{
				Object[] queryResult = null;
				T beanInstance = null;
				for (Object objResult : objResultList) {
					queryResult = (Object[]) objResult;
					beanInstance = (T) queryResult[0];
					BeanUtil.setProperty(beanInstance, this.geoPropertyName, queryResult[1]);
					retorno.add(beanInstance);
				}
			}
			
			return retorno;
		}
		else
		{
			return query.getResultList();
		}

	}

	@Override
	public List<T> intersects(Geometry geometry) {
		
		return genericTopologyQuery(geometry,"intersects");
	}



	@Override
	public List<T> intersects(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		
		if(spatialArgument == null)
			return this.intersects(geometry);
		
		return this.genericTopologyQueryWithArgument(spatialArgument, geometry,"intersects");
		

	}

	@Override
	public boolean verifyIntersects(Geometry geometry) {
		
		return this.genericTopologyVerify(geometry,"intersects").booleanValue();
	}

	@Override
	public boolean verifyIntersects(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		
		if(spatialArgument == null)
			return this.verifyIntersects(geometry);
		
		return this.genericTopologyVerifyWithArgument(spatialArgument, geometry,"intersects");
	}

	@Override
	public List<T> equals(Geometry geometry) {
		
		return genericTopologyQuery(geometry,"equals");
	}

	@Override
	public List<T> equals(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		if(spatialArgument == null)
			return this.equals(geometry);
		
		return this.genericTopologyQueryWithArgument(spatialArgument, geometry,"equals");
	}

	@Override
	public boolean verifyEquals(Geometry geometry) {
		return this.genericTopologyVerify(geometry,"equals").booleanValue();
	}

	@Override
	public boolean verifyEquals(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		
		if(spatialArgument == null)
			return this.verifyIntersects(geometry);
		
		return this.genericTopologyVerifyWithArgument(spatialArgument, geometry,"equals");
	}

	@Override
	public List<T> disjoint(Geometry geometry) {
		return genericTopologyQuery(geometry,"disjoint");
	}

	@Override
	public List<T> disjoint(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		if(spatialArgument == null)
			return this.disjoint(geometry);
		
		return this.genericTopologyQueryWithArgument(spatialArgument, geometry,"disjoint");
	}

	@Override
	public boolean verifyDisjoint(Geometry geometry) {
		return this.genericTopologyVerify(geometry,"disjoint").booleanValue();
	}

	@Override
	public boolean verifyDisjoint(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		
		if(spatialArgument == null)
			return this.verifyIntersects(geometry);
		
		return this.genericTopologyVerifyWithArgument(spatialArgument, geometry,"disjoint");
	}

	@Override
	public List<T> touches(Geometry geometry) {
		return genericTopologyQuery(geometry,"touches");
	}

	@Override
	public List<T> touches(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		if(spatialArgument == null)
			return this.touches(geometry);
		
		return this.genericTopologyQueryWithArgument(spatialArgument, geometry,"touches");
	}

	@Override
	public boolean verifyTouches(Geometry geometry) {
		return this.genericTopologyVerify(geometry,"touches").booleanValue();
	}

	@Override
	public boolean verifyTouches(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		
		if(spatialArgument == null)
			return this.verifyIntersects(geometry);
		
		return this.genericTopologyVerifyWithArgument(spatialArgument, geometry,"touches");
	}

	@Override
	public List<T> crosses(Geometry geometry) {
		return genericTopologyQuery(geometry,"crosses");
	}

	@Override
	public List<T> crosses(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		if(spatialArgument == null)
			return this.crosses(geometry);
		
		return this.genericTopologyQueryWithArgument(spatialArgument, geometry,"crosses");
	}

	@Override
	public boolean verifyCrosses(Geometry geometry) {
		return this.genericTopologyVerify(geometry,"crosses").booleanValue();
	}

	@Override
	public boolean verifyCrosses(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		
		if(spatialArgument == null)
			return this.verifyIntersects(geometry);
		
		return this.genericTopologyVerifyWithArgument(spatialArgument, geometry,"crosses");
	}

	@Override
	public List<T> within(Geometry geometry) {
		return genericTopologyQuery(geometry,"within");
	}

	@Override
	public List<T> within(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		if(spatialArgument == null)
			return this.within(geometry);
		
		return this.genericTopologyQueryWithArgument(spatialArgument, geometry,"within");
	}

	@Override
	public boolean verifyWithin(Geometry geometry) {
		return this.genericTopologyVerify(geometry,"within").booleanValue();
	}

	@Override
	public boolean verifyWithin(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		
		if(spatialArgument == null)
			return this.verifyIntersects(geometry);
		
		return this.genericTopologyVerifyWithArgument(spatialArgument, geometry,"within");
	}

	@Override
	public List<T> contains(Geometry geometry) {
		return genericTopologyQuery(geometry,"contains");
	}

	@Override
	public List<T> contains(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		if(spatialArgument == null)
			return this.contains(geometry);
		
		return this.genericTopologyQueryWithArgument(spatialArgument, geometry,"contains");
	}

	@Override
	public boolean verifyContains(Geometry geometry) {
		return this.genericTopologyVerify(geometry,"contains").booleanValue();
	}

	@Override
	public boolean verifyContains(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		
		if(spatialArgument == null)
			return this.verifyIntersects(geometry);
		
		return this.genericTopologyVerifyWithArgument(spatialArgument, geometry,"contains");
	}

	@Override
	public List<T> overlaps(Geometry geometry) {
		return genericTopologyQuery(geometry,"overlaps");
	}

	@Override
	public List<T> overlaps(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		if(spatialArgument == null)
			return this.overlaps(geometry);
		
		return this.genericTopologyQueryWithArgument(spatialArgument, geometry,"overlaps");
	}

	@Override
	public boolean verifyOverlaps(Geometry geometry) {
		return this.genericTopologyVerify(geometry,"overlaps").booleanValue();
	}

	@Override
	public boolean verifyOverlaps(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		
		if(spatialArgument == null)
			return this.verifyIntersects(geometry);
		
		return this.genericTopologyVerifyWithArgument(spatialArgument, geometry,"overlaps");
	}

	@Override
	public List<T> relate(Geometry geometry, String relation) {
		throw new DemoiselleSpatialException("Not yet implemented!");
	}

	@Override
	public List<T> relate(Geometry geometry,
			SpatialQueryArgument spatialArgument, String relation) {
		throw new DemoiselleSpatialException("Not yet implemented!");
	}

	@Override
	public boolean verifyRelate(Geometry geometry, String relation) {
		throw new DemoiselleSpatialException("Not yet implemented!");
	}

	@Override
	public boolean verifyRelate(Geometry geometry,
			SpatialQueryArgument spatialArgument, String relation) {
		throw new DemoiselleSpatialException("Not yet implemented!");
	}

	@Override
	public Envelope calculateExtent() {
		
		verifyGeometryProperty();
		
		String jpql = "select astext(extent(this.@0)) from @1 this";
		jpql = this.setStringQueryJPQLParameters(jpql, 
										  this.geoColumnName,
										  this.getBeanClass().getName()
										  );		
		String objResult = (String) this.createQuery(jpql).getSingleResult();
		
		if(objResult == null)
			throw new DemoiselleSpatialException("Extent for " + this.getBeanClass().getName() + " is empty");
		Geometry geometry = null;
		
		try{
		geometry=new WKTReader().read(objResult);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		geometry.setSRID(this.geoBeanSRID);
		return geometry.getEnvelopeInternal();
	}

	@Override
	public Envelope calculateExtent(SpatialQueryArgument spatialArgument) {
		
		verifyGeometryProperty();
		
		if(spatialArgument.getOutputSRID() == null)
			return this.calculateExtent();
		
		String jpql = "select astext(transform(ST_SetSRID(extent(this.@0),@1),@2)) from @3 this";
		jpql = this.setStringQueryJPQLParameters(jpql, 
										  this.geoColumnName,
										  this.geoBeanSRID.toString(),
										  spatialArgument.getOutputSRID().toString(),
										  this.getBeanClass().getName()
										  );		
		String objResult = (String) this.createQuery(jpql).getSingleResult();
		
		if(objResult == null)
			throw new DemoiselleSpatialException("Extent for " + this.getBeanClass().getName() + " is empty");

		Geometry geometry = null;
		
		try{
		geometry=new WKTReader().read(objResult);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		geometry.setSRID(spatialArgument.getOutputSRID());
		return geometry.getEnvelopeInternal();
		
	}
	
	private List<T> genericTopologyQueryWithArgument(SpatialQueryArgument spatialArgument, Geometry filter,String spatialfunction)
	{
		this.verifyGeometryProperty();

		if(filter == null)
			throw new DemoiselleSpatialException("Geometry filter is null");

		this.adjustSRID(spatialArgument);
		this.adjustSRID(filter);
		
		StringBuffer jpql = new StringBuffer();
		String finalQuery = null;
		
		//Necessita transformar a saida e Somente cria a query de transform se for diferente
		if(spatialArgument.getOutputSRID() != null && this.geoBeanSRID.intValue() != spatialArgument.getOutputSRID().intValue())
				jpql.append(" select this,transform(this.@geocolumn,@outputsrid) from @beanclass this where ");
		else
			    jpql.append(" select this from @beanclass this where ");
		
		if(spatialArgument.getExtentFilter() != null)
		{
			//verifica se precisa e consegue transformar o filtro extent de projecao
			if(this.geoBeanSRID != null && this.geoBeanSRID.intValue() != spatialArgument.getExtentFilter().getSrid())
				jpql.append(" within(this.@geocolumn, transform(:filterExtent,@beansrid)) = true and ");
			else
				jpql.append(" within(this.@geocolumn, :filterExtent) = true and ");
		}

				//verifica se precisa e consegue transformar o filtro de projecao
				if(this.geoBeanSRID != null && this.geoBeanSRID.intValue() != filter.getSRID())
					jpql.append(" @spatialfunction(this.@geocolumn, transform(:filter,@beansrid)) = true ");
				else
					jpql.append(" @spatialfunction(this.@geocolumn, :filter) = true ");
		
		finalQuery = jpql.toString().replace("@geocolumn",  this.geoColumnName);
		if(spatialArgument.getOutputSRID() != null && this.geoBeanSRID.intValue() != spatialArgument.getOutputSRID().intValue())
			finalQuery = finalQuery.replace("@outputsrid", spatialArgument.getOutputSRID().toString());
		
		finalQuery = finalQuery.replace("@spatialfunction",  spatialfunction);
		finalQuery = finalQuery.replace("@beanclass", this.getBeanClass().getName());
		if(this.geoBeanSRID != null)
			finalQuery = finalQuery.replace("@beansrid", this.geoBeanSRID.toString());

		Query query = this.createQuery(finalQuery);
		
		if(spatialArgument.getExtentFilter() != null)
			query.setParameter("filterExtent",spatialArgument.getExtentFilter().getGeometry());
		
		query.setParameter("filter",filter);
		
		if(spatialArgument.getOutputSRID() != null && this.geoBeanSRID.intValue() != spatialArgument.getOutputSRID().intValue())
		{

			List<T> retorno = new ArrayList<T>();
			List<Object> objResultList = query.getResultList();
			
			if(objResultList != null && !objResultList.isEmpty())
			{
				Object[] queryResult = null;
				T beanInstance = null;
				for (Object objResult : objResultList) {
					queryResult = (Object[]) objResult;
					beanInstance = (T) queryResult[0];
					BeanUtil.setProperty(beanInstance, this.geoPropertyName, queryResult[1]);
					retorno.add(beanInstance);
				}
			}
			
			return retorno;
		}
		else
		{
			return query.getResultList();
		}
	}
	
	
	private List<T> genericTopologyQuery(Geometry filter, String spatialfunction) {
		verifyGeometryProperty();
		
		if(filter == null)
			throw new DemoiselleSpatialException("Geometry filter is null");
		
		this.adjustSRID(filter);
		
		String jpql = null;
		
		//verifica se precisa e consegue transformar o filtro extent de projecao
		if(this.geoBeanSRID != null && this.geoBeanSRID.intValue() != filter.getSRID())
			jpql = " select this from @beanclass this where @spatialfunction(this.@geocolumn, transform(:filter,@beansrid)) = true ";
		else
			jpql = " select this from @beanclass this where @spatialfunction(this.@geocolumn, :filter) = true ";
		
		jpql = jpql.replace("@spatialfunction",  spatialfunction);
		jpql = jpql.replace("@geocolumn",  this.geoColumnName);
		jpql = jpql.replace("@beanclass", this.getBeanClass().getName());
		if(this.geoBeanSRID != null && this.geoBeanSRID.intValue() != filter.getSRID())
			jpql = jpql.replace("@beansrid", this.geoBeanSRID.toString());

		Query query = this.createQuery(jpql);
	
		query.setParameter("filter",filter);
		
		return query.getResultList();
	}
	
	private Boolean genericTopologyVerifyWithArgument(SpatialQueryArgument spatialArgument, Geometry filter,String spatialfunction)
	{
		this.verifyGeometryProperty();

		if(filter == null)
			throw new DemoiselleSpatialException("Geometry filter is null");

		this.adjustSRID(filter);
		this.adjustSRID(spatialArgument);
		
		StringBuffer jpql = new StringBuffer();
		String finalQuery = null;
		
		jpql.append(" select count(*) from @beanclass this where ");
		
		if(spatialArgument.getExtentFilter() != null)
		{
			//verifica se precisa e consegue transformar o filtro extent de projecao
			if(this.geoBeanSRID != null && this.geoBeanSRID.intValue() != spatialArgument.getExtentFilter().getSrid())
				jpql.append(" within(this.@geocolumn, transform(:filterExtent,@beansrid)) = true and ");
			else
				jpql.append(" within(this.@geocolumn, :filterExtent) = true and ");
		}

				//verifica se precisa e consegue transformar o filtro de projecao
				if(this.geoBeanSRID != null && this.geoBeanSRID.intValue() != filter.getSRID())
					jpql.append(" @spatialfunction(this.@geocolumn, transform(:filter,@beansrid)) = true ");
				else
					jpql.append(" @spatialfunction(this.@geocolumn, :filter) = true ");
		
		finalQuery = jpql.toString().replace("@geocolumn",  this.geoColumnName);		
		finalQuery = finalQuery.replace("@spatialfunction",  spatialfunction);
		finalQuery = finalQuery.replace("@beanclass", this.getBeanClass().getName());
		if(this.geoBeanSRID != null)
			finalQuery = finalQuery.replace("@beansrid", this.geoBeanSRID.toString());

		Query query = this.createQuery(finalQuery);
		
		if(spatialArgument.getExtentFilter() != null)
			query.setParameter("filterExtent",spatialArgument.getExtentFilter().getGeometry());
		
		query.setParameter("filter",filter);
		
		Number count = (Number) query.getSingleResult();
		
		if(count.longValue() > 0)
			return true;
		else
			return false;
	}
	
	
	private Boolean genericTopologyVerify(Geometry filter, String spatialfunction) {
		verifyGeometryProperty();
		
		if(filter == null)
			throw new DemoiselleSpatialException("Geometry filter is null");
		
		this.adjustSRID(filter);
		
		String jpql = null;
		
		//verifica se precisa e consegue transformar o filtro extent de projecao
		if(this.geoBeanSRID != null && this.geoBeanSRID.intValue() != filter.getSRID())
			jpql = " select count(*) from @beanclass this where @spatialfunction(this.@geocolumn, transform(:filter,@beansrid)) = true ";
		else
			jpql = " select count(*) from @beanclass this where @spatialfunction(this.@geocolumn, :filter) = true ";
		
		jpql = jpql.replace("@spatialfunction",  spatialfunction);
		jpql = jpql.replace("@geocolumn",  this.geoColumnName);
		jpql = jpql.replace("@beanclass", this.getBeanClass().getName());
		if(this.geoBeanSRID != null && this.geoBeanSRID.intValue() != filter.getSRID())
			jpql = jpql.replace("@beansrid", this.geoBeanSRID.toString());

		Query query = this.createQuery(jpql);
	
		query.setParameter("filter",filter);
		
		Number count = (Number) query.getSingleResult();
		
		if(count.longValue() > 0)
			return true;
		else
			return false;
	}
	
	
	private String setStringQueryJPQLParameters(String jpql,Object...objects)
	{
		for (int i = 0; i < objects.length; i++) {
			Object object = objects[i];
			jpql = jpql.replace("@"+i, object.toString());
		}
		
		return jpql;
	}
	
	private void verifyGeometryProperty()
	{
		if(this.geoColumnName == null)
			throw new DemoiselleSpatialException("Geometry property in " + this.getBeanClass().getName() + " not found");
	}
	
	private void adjustSRID(SpatialQueryArgument spatialArgument)
	{
		if(spatialArgument.getExtentFilter() != null && spatialArgument.getExtentFilter().getSrid().intValue() == -1 && this.geoBeanSRID != null  && spatialArgument.getExtentFilter().getSrid() != this.geoBeanSRID)
			spatialArgument.getExtentFilter().setSrid(geoBeanSRID);
	}
	
	private void adjustSRID(Geometry filter)
	{
		if(filter.getSRID() == new Integer(0) && this.geoBeanSRID != null)
			filter.setSRID(geoBeanSRID);
	}
	

	public void createGeometryColumns() throws IOException, ClassNotFoundException {
		
		final Map<String, String> map = this.getAddGeometryColumnInfo();
		
		this.getEntityManager().unwrap(Session.class).doWork(new Work() {
			   public void execute(Connection conn) {
				   try {					   
					
						conn.createStatement().execute("ALTER TABLE "+ map.get("table_name") +" DROP COLUMN " + map.get("column_name"));
						conn.createStatement().execute("select AddGeometryColumn('"+map.get("table_name")+"', '"+map.get("column_name")+"', "+map.get("srid")+", '"+ map.get("type") +"', 2)");
					   

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
				   }
				});
				
	}
	
	public Map<String, String> getAddGeometryColumnInfo()
	{
		
		Map<String, String> map = new HashMap<String, String>();
		Class<T> clazz = getBeanClass();
		String geometryType = null;
		
			 map = new HashMap<String, String>();
			 
			 if(SpatialEntityReflections.seekSRIDInClass(clazz) != null)
				 map.put("srid",SpatialEntityReflections.seekSRIDInClass(clazz).toString());
			 else
				 return null;
			 
			 geometryType = SpatialEntityReflections.seekGeometryPerpertyType(clazz);
			 if(geometryType != null)
				 map.put("type", geometryType.toUpperCase());
			 else
				 return null;
			 
			 map.put("column_name", SpatialEntityReflections.seekGeometryTypeColumnName(clazz));
			 map.put("table_name", SpatialEntityReflections.seekEntityTableName(clazz));
		
		return map;
	}
	
}
