package br.gov.frameworkdemoiselle.spatial.geocode;

import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.model.Language;

import com.vividsolutions.jts.geom.Point;

public interface ReverseGeocoding{

	public ReverseGeocoding setLocation(String lat, String lng);
	
	public ReverseGeocoding setLocation(Point point);
			
	public GeocodingResponse search();
	
	public GeocodingResponse search(boolean clear);
	
	public void clearSearch();
	
	public ReverseGeocoding setLanguage(Language language);
	
	public ReverseGeocoding setRegion(String region);
	
}
