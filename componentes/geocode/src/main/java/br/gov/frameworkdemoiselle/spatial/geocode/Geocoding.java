package br.gov.frameworkdemoiselle.spatial.geocode;



public interface Geocoding extends GeocodingCommom{

	
	public Geocoding setAddress(String address);
	
	public Geocoding setAddress(String street,String number,String cityDivision, String city, String state, String country);
	
	public Geocoding setPostalCode(String postalcode);
	
	
}
