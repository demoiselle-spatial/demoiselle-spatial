package br.gov.frameworkdemoiselle.component.georest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="featureCollection")
public class GeoJSONFeatureCollection {

	private String type = "FeatureCollection";
	
	private List<GeoJSONFeature> features;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<GeoJSONFeature> getFeatures() {
		return features;
	}

	public void setFeatures(List<GeoJSONFeature> features) {
		this.features = features;
	}
	
	
}
