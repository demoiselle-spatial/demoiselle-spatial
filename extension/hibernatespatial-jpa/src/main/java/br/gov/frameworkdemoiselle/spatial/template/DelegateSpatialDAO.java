package br.gov.frameworkdemoiselle.spatial.template;

import java.io.IOException;
import java.util.List;

import br.gov.frameworkdemoiselle.spatial.query.SpatialQueryArgument;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

public class DelegateSpatialDAO<T, I, C extends JPASpatialDAO<T, I>> extends DelegateCrud<T, I, C> implements SpatialDAO<T, I> {

	private static final long serialVersionUID = 1L;

	@Override
	public T load(final I id, final SpatialQueryArgument spatialArgument) {
		return getDelegate().load(id, spatialArgument);
	}

	@Override
	public List<T> findAll(SpatialQueryArgument spatialArgument) {
		return getDelegate().findAll(spatialArgument);
	}

	@Override
	public List<T> intersects(Geometry geometry) {
		return getDelegate().intersects(geometry);
	}

	@Override
	public List<T> intersects(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().intersects(geometry,spatialArgument);
	}

	@Override
	public boolean verifyIntersects(Geometry geometry) {
		return getDelegate().verifyIntersects(geometry);
	}

	@Override
	public boolean verifyIntersects(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().verifyIntersects(geometry,spatialArgument);
	}

	@Override
	public List<T> equals(Geometry geometry) {
		return getDelegate().equals(geometry);
	}

	@Override
	public List<T> equals(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().equals(geometry,spatialArgument);
	}

	@Override
	public boolean verifyEquals(Geometry geometry) {
		return getDelegate().verifyEquals(geometry);
	}

	@Override
	public boolean verifyEquals(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().verifyEquals(geometry,spatialArgument);
	}

	@Override
	public List<T> disjoint(Geometry geometry) {
		return getDelegate().disjoint(geometry);
	}

	@Override
	public List<T> disjoint(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().disjoint(geometry,spatialArgument);
	}

	@Override
	public boolean verifyDisjoint(Geometry geometry) {
		return getDelegate().verifyDisjoint(geometry);
	}

	@Override
	public boolean verifyDisjoint(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().verifyDisjoint(geometry,spatialArgument);
	}

	@Override
	public List<T> touches(Geometry geometry) {
		return getDelegate().touches(geometry);
	}

	@Override
	public List<T> touches(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().touches(geometry,spatialArgument);
	}

	@Override
	public boolean verifyTouches(Geometry geometry) {
		return getDelegate().verifyTouches(geometry);
	}

	@Override
	public boolean verifyTouches(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().verifyTouches(geometry,spatialArgument);
	}

	@Override
	public List<T> crosses(Geometry geometry) {
		return getDelegate().crosses(geometry);
	}

	@Override
	public List<T> crosses(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().crosses(geometry,spatialArgument);
	}

	@Override
	public boolean verifyCrosses(Geometry geometry) {
		return getDelegate().verifyCrosses(geometry);
	}

	@Override
	public boolean verifyCrosses(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().verifyCrosses(geometry,spatialArgument);
	}

	@Override
	public List<T> within(Geometry geometry) {
		return getDelegate().within(geometry);
	}

	@Override
	public List<T> within(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().within(geometry,spatialArgument);
	}

	@Override
	public boolean verifyWithin(Geometry geometry) {
		return getDelegate().verifyWithin(geometry);
	}

	@Override
	public boolean verifyWithin(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().verifyWithin(geometry,spatialArgument);
	}

	@Override
	public List<T> contains(Geometry geometry) {
		return getDelegate().contains(geometry);
	}

	@Override
	public List<T> contains(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().contains(geometry,spatialArgument);
	}

	@Override
	public boolean verifyContains(Geometry geometry) {
		return getDelegate().verifyContains(geometry);
	}

	@Override
	public boolean verifyContains(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().verifyContains(geometry,spatialArgument);
	}

	@Override
	public List<T> overlaps(Geometry geometry) {
		return getDelegate().overlaps(geometry);
	}

	@Override
	public List<T> overlaps(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().overlaps(geometry,spatialArgument);
	}

	@Override
	public boolean verifyOverlaps(Geometry geometry) {
		return getDelegate().verifyOverlaps(geometry);
	}

	@Override
	public boolean verifyOverlaps(Geometry geometry,
			SpatialQueryArgument spatialArgument) {
		return getDelegate().verifyOverlaps(geometry,spatialArgument);
	}

	@Override
	public List<T> relate(Geometry geometry, String relation) {
		return getDelegate().relate(geometry,relation);
	}

	@Override
	public List<T> relate(Geometry geometry,
			SpatialQueryArgument spatialArgument, String relation) {
		return getDelegate().relate(geometry,spatialArgument,relation);
	}

	@Override
	public boolean verifyRelate(Geometry geometry, String relation) {
		return getDelegate().verifyRelate(geometry,relation);
	}

	@Override
	public boolean verifyRelate(Geometry geometry,
			SpatialQueryArgument spatialArgument, String relation) {
		return getDelegate().verifyRelate(geometry,spatialArgument,relation);
	}

	@Override
	public Envelope calculateExtent() {
		return getDelegate().calculateExtent();
	}

	@Override
	public Envelope calculateExtent(SpatialQueryArgument spatialArgument) {
		return getDelegate().calculateExtent(spatialArgument);
	}

	@Override
	public void createGeometryColumns() throws IOException,
			ClassNotFoundException {
		
		getDelegate().createGeometryColumns();
		
	}

}
