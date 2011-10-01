package br.gov.frameworkdemoiselle.spatial.geocode;

import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.model.Language;

import com.vividsolutions.jts.geom.Envelope;



public interface Geocoding {

	
	public Geocoding setAddress(String address);
	
	public Geocoding setAddress(String street,String number,String cityDivision, String city, String state, String country);
	
	public Geocoding setPostalCode(String postalcode);
	
	public GeocodingResponse search();
	
	public GeocodingResponse search(boolean clear);
	
	public void clearSearch();
	
	public Geocoding setLanguage(Language language);
	
	public Geocoding setRegion(String region);
	
	public Geocoding setBounds(Envelope boundary);
	
	
}
