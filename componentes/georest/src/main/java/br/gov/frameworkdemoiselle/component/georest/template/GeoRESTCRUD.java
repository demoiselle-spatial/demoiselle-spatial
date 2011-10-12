package br.gov.frameworkdemoiselle.component.georest.template;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.component.georest.model.GeoJSONFeature;
import br.gov.frameworkdemoiselle.component.georest.model.GeoJSONFeatureCollection;

public interface GeoRESTCRUD<I> extends Serializable {

	void delete(I id);

	GeoJSONFeatureCollection list();

	void insert(GeoJSONFeature feature);

	GeoJSONFeatureCollection load(I id);

	void update(GeoJSONFeature feature);

}