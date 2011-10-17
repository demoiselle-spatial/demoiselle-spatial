package br.gov.frameworkdemoiselle.spatial.template;

import java.io.IOException;
import java.util.List;

import br.gov.frameworkdemoiselle.spatial.query.SpatialQueryArgument;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

public interface SpatialDAO<T,I> {

	T load(I id, SpatialQueryArgument spatialArgument);
	
	List<T> findAll(SpatialQueryArgument spatialArgument);
	
	List<T> intersects(Geometry geometry);
	
	List<T> intersects(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	boolean verifyIntersects(Geometry geometry);
	
	boolean verifyIntersects(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	List<T> equals(Geometry geometry);
	
	List<T> equals(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	boolean verifyEquals(Geometry geometry);
	
	boolean verifyEquals(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	List<T> disjoint(Geometry geometry);
	
	List<T> disjoint(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	boolean verifyDisjoint(Geometry geometry);
	
	boolean verifyDisjoint(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	List<T> touches(Geometry geometry);
	
	List<T> touches(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	boolean verifyTouches(Geometry geometry);
	
	boolean verifyTouches(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	List<T> crosses(Geometry geometry);
	
	List<T> crosses(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	boolean verifyCrosses(Geometry geometry);
	
	boolean verifyCrosses(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	List<T> within(Geometry geometry);
	
	List<T> within(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	boolean verifyWithin(Geometry geometry);
	
	boolean verifyWithin(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	List<T> contains(Geometry geometry);
	
	List<T> contains(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	boolean verifyContains(Geometry geometry);
	
	boolean verifyContains(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	List<T> overlaps(Geometry geometry);
	
	List<T> overlaps(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	boolean verifyOverlaps(Geometry geometry);
	
	boolean verifyOverlaps(Geometry geometry,SpatialQueryArgument spatialArgument);
	
	List<T> relate(Geometry geometry,String relation);
	
	List<T> relate(Geometry geometry,SpatialQueryArgument spatialArgument,String relation);
	
	boolean verifyRelate(Geometry geometry,String relation);
	
	boolean verifyRelate(Geometry geometry,SpatialQueryArgument spatialArgument,String relation);
	
	Envelope calculateExtent();
	
	Envelope calculateExtent(SpatialQueryArgument spatialArgument);
	
	public void createGeometryColumns() throws IOException, ClassNotFoundException;
	
	
}
