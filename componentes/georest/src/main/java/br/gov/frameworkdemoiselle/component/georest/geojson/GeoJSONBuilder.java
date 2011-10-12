package br.gov.frameworkdemoiselle.component.georest.geojson;

import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.component.georest.model.GeoJSONFeatureCollection;

public interface GeoJSONBuilder {

	public abstract GeoJSONFeatureCollection decodeSimpleFeatureList(
			List<SimpleFeature> features);
	
	public abstract GeoJSONFeatureCollection decodeSimpleFeature(
			SimpleFeature feature);

}