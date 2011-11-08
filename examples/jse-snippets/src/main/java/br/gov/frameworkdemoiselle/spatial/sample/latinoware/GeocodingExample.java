package br.gov.frameworkdemoiselle.spatial.sample.latinoware;

import br.gov.frameworkdemoiselle.spatial.geocode.Geocoding;
import br.gov.frameworkdemoiselle.spatial.geocode.ReverseGeocoding;
import br.gov.frameworkdemoiselle.spatial.geocode.google.impl.GeocodingGoogleMapsImpl;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.model.Language;
import br.gov.frameworkdemoiselle.spatial.geocode.nominatim.impl.ReverseGeocodingNominatimImpl;

public class GeocodingExample {

	
	private Geocoding geocode = new GeocodingGoogleMapsImpl();
	
	private GeocodingResponse response = null;
	
	public void geocodeDummy()
	{
	response =	geocode
				.setAddress("Rua Marechal Deodoro, Foz do Iguacu")
				.setPostalCode("85851-030")
				.setLanguage(Language.Arabic)
				.search();
	System.out.println("Localização: " + response.getResults().get(0).getPoint());
	System.out.println("Endereço Base Geo: " +response.getResults().get(0).getAddress());
	}
	
	public static void main(String[] args) {
		
		new GeocodingExample().geocodeDummy();
		//new GeocodingExample().reverseGeocoding();
	}
	
	
	
	private ReverseGeocoding reverse = new ReverseGeocodingNominatimImpl();
	
	//private ReverseGeocoding reverse = new ReverseGeocodingGoogleMapsImpl();
	
	public void reverseGeocoding()
	{
		response = reverse
			.setLocation("-12.9710208", "-38.4876078")
			.search();
		
		System.out.println("Ponto Base Geo: " + response.getResults().get(0).getPoint());
		System.out.println("Endereço Localizado: "+response.getResults().get(0).getAddress());
	}
}
