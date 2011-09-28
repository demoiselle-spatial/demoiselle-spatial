package br.gov.frameworkdemoiselle.spatial.geocode.test.google.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.gov.frameworkdemoiselle.spatial.geocode.Geocoding;
import br.gov.frameworkdemoiselle.spatial.geocode.google.impl.GeocodingReverseGeocodingImpl;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.model.Language;

public class GeocodingTest {

	@Test
	public void searchFullAddress() {
		
		Geocoding impl = new GeocodingReverseGeocodingImpl();
		
		GeocodingResponse response = impl.setAddress("Rua Santa Maria Goretti, Salvador, BA").search(true);
		
		assertTrue(response.getResults().get(0).getAddress().equals("R. Santa Maria Goretti - Vila Laura, Salvador - Bahia, 40270-210, Brazil"));		
		
	}
	
	@Test
	public void searchPartialAddress() {
		
		Geocoding impl = new GeocodingReverseGeocodingImpl();
		
		GeocodingResponse response = impl.setAddress("Santa Maria Gorreti","339","Vila-Laura","Salvador","BA","BRAZIL").search(true);
		
		assertTrue(response.getResults().get(0).getAddress().equals("R. Santa Maria Goretti, 339 - Vila Laura, Salvador - Bahia, 40270-210, Brazil"));		
		
	}
	
	@Test
	public void searchPostalCode() {
		
		Geocoding impl = new GeocodingReverseGeocodingImpl();
		
		GeocodingResponse response = impl.setPostalCode("40270-210").search(true);
		
		assertTrue(response.getResults().get(0).getAddress().equals("Vila Laura, Salvador - Bahia, 40270-210, Brazil"));		
		
	}
	
	@Test
	public void searchFullAddressAndLanguage() {
		
		Geocoding impl = new GeocodingReverseGeocodingImpl();
		
		GeocodingResponse response = impl.setAddress("Rua Santa Maria Goretti, Salvador, BA").setLanguage(Language.Portuguese_Brazil).search(true);
		
		assertTrue(response.getResults().get(0).getAddress().equals("R. Santa Maria Goretti - Vila Laura, Salvador - BA, 40270-210, Brasil"));		
		
	}
	
	@Test
	public void searchFullAddressAnd() {
		
		Geocoding impl = new GeocodingReverseGeocodingImpl();
		
		GeocodingResponse response = impl.setAddress("Rua Santa Maria Goretti, Salvador, BA").setRegion("BAHIA").search(true);
		
		assertTrue(response.getResults().get(0).getAddress().equals("R. Santa Maria Goretti - Vila Laura, Salvador - Bahia, 40270-210, Brazil"));		
		
	}
	
	

}
