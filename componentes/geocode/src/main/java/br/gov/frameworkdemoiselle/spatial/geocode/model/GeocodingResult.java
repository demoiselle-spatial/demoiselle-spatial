package br.gov.frameworkdemoiselle.spatial.geocode.model;

import java.util.List;

import com.vividsolutions.jts.geom.Point;

public class GeocodingResult {

	private Point point;
	
	private String address;
	
	private List<GeocodingAddressParts> addressParts;

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}



	public List<GeocodingAddressParts> getAddressParts() {
		return addressParts;
	}

	public void setAddressParts(List<GeocodingAddressParts> addressParts) {
		this.addressParts = addressParts;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	
	
}
