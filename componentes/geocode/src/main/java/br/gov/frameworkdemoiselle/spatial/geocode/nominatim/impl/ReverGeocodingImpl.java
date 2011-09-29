package br.gov.frameworkdemoiselle.spatial.geocode.nominatim.impl;

import br.gov.frameworkdemoiselle.spatial.geocode.Geocoding;
import br.gov.frameworkdemoiselle.spatial.geocode.ReverseGeocoding;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.model.Language;
import br.gov.frameworkdemoiselle.spatial.geocode.nominatim.model.NominatimRequestBuilder;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Point;


public class ReverGeocodingImpl implements ReverseGeocoding{

	private NominatimRequestBuilder builder;
	
	public ReverGeocodingImpl() {
		this.builder = new NominatimRequestBuilder();
	}
	
	@Override
	public GeocodingResponse search() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeocodingResponse search(boolean clear) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearSearch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Geocoding setLanguage(Language language) {
		
		return null;
	}

	@Override
	public Geocoding setRegion(String region) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Geocoding setBounds(Envelope boundary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Geocoding setLocation(String lat, String lng) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Geocoding setLocation(Point point) {
		
		return null;
	}

}
