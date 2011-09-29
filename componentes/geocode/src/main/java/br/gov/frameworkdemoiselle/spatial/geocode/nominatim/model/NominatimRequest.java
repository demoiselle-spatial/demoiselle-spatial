package br.gov.frameworkdemoiselle.spatial.geocode.nominatim.model;

import com.vividsolutions.jts.geom.Point;

public class NominatimRequest {

	
	private Point location;
	
	//TODO Need implements in configurable mode
	private String userMail;

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
	
	
}
