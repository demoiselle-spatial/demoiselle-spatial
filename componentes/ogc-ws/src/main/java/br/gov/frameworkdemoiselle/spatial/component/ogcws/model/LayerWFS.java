package br.gov.frameworkdemoiselle.spatial.component.ogcws.model;

import org.opengis.feature.type.FeatureType;

public class LayerWFS extends Layer {

	private FeatureType type;

	public FeatureType getType() {
		return type;
	}

	public void setType(FeatureType type) {
		this.type = type;
	}
	
	
}
