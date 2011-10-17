package br.gov.frameworkdemoiselle.component.georest.template;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.component.georest.model.GeoJSONFeature;
import br.gov.frameworkdemoiselle.component.georest.model.GeoJSONFeatureCollection;
import br.gov.frameworkdemoiselle.spatial.template.DemoiselleSpatialEnvelope;

public interface GeoRESTCRUD<I> extends Serializable {

	void delete(I id);

	GeoJSONFeatureCollection list(DemoiselleSpatialEnvelope bbox,Integer outputSrid);

	void insert(GeoJSONFeature feature);

	GeoJSONFeatureCollection load(I id);

	void update(GeoJSONFeature feature);

}