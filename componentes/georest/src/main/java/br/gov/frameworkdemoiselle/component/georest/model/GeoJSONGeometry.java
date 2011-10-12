package br.gov.frameworkdemoiselle.component.georest.model;

import java.util.List;

public class GeoJSONGeometry {

	private String type;
	
	private List<Object> coordinates;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Object> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Object> coordinates) {
		this.coordinates = coordinates;
	}
	
	
}
