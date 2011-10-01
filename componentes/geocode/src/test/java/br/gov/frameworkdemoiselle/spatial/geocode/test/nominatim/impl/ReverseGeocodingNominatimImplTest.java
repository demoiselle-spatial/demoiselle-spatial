package br.gov.frameworkdemoiselle.spatial.geocode.test.nominatim.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.gov.frameworkdemoiselle.spatial.geocode.ReverseGeocoding;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.nominatim.impl.ReverseGeocodingNominatimImpl;
import br.gov.frameworkdemoiselle.spatial.geocode.util.JTSUtil;

public class ReverseGeocodingNominatimImplTest {

	@Test
	public void searchLocation() {
		
		ReverseGeocoding impl = new ReverseGeocodingNominatimImpl();
		 
		GeocodingResponse response = impl.setLocation("-12.9710208", "-38.48760780000001").search(true);
		
		assertTrue(response.getResults().get(0).getAddress().equals("Brotas, Salvador, Bahia, Brasil"));		
		
	}
	
	@Test
	public void searchLocationJTS() {
		
		ReverseGeocoding impl = new ReverseGeocodingNominatimImpl();
		 
		GeocodingResponse response = impl.setLocation(new JTSUtil().createJTSPoint("-12.9710208", "-38.48760780000001")).search(true);
		
		assertTrue(response.getResults().get(0).getAddress().equals("Brotas, Salvador, Bahia, Brasil"));		
		
	}
}
