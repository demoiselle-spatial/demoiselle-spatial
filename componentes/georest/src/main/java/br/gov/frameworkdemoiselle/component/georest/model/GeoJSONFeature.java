package br.gov.frameworkdemoiselle.component.georest.model;

import java.util.List;
import java.util.Map;

public class GeoJSONFeature {

	public String type = "Feature";
	
	private String id;
	
	private Map<String, Object> properties;
	
	private GeoJSONGeometry geometry;
	
	private List<Double> bbox;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public GeoJSONGeometry getGeometry() {
		return geometry;
	}

	public void setGeometry(GeoJSONGeometry geometry) {
		this.geometry = geometry;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Double> getBbox() {
		return bbox;
	}

	public void setBbox(List<Double> bbox) {
		this.bbox = bbox;
	}

	
	
}
