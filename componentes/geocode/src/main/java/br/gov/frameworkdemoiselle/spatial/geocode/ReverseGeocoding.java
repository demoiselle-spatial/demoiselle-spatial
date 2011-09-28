package br.gov.frameworkdemoiselle.spatial.geocode;

import com.vividsolutions.jts.geom.Point;

public interface ReverseGeocoding extends GeocodingCommom{

	public Geocoding setLocation(String lat, String lng);
	
	public Geocoding setLocation(Point point);
	
}
