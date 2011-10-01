package br.gov.frameworkdemoiselle.spatial.geocode.google.impl;

import java.math.BigDecimal;

import br.gov.frameworkdemoiselle.spatial.geocode.ReverseGeocoding;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingServiceReturnStatus;
import br.gov.frameworkdemoiselle.spatial.geocode.model.Language;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.LatLng;
import com.vividsolutions.jts.geom.Point;

public class ReverseGeocodingGoogleMapsImpl implements ReverseGeocoding{

	
	private LatLng location;
	
	private GeocoderRequestBuilder requestBuilder;
	
	private final Geocoder geocoder = new Geocoder();
	
	
	public ReverseGeocodingGoogleMapsImpl() {
		this.clearSearch();
	}
	
	@Override
	public ReverseGeocoding setLocation(String lat, String lng) {
		
		this.location = new LatLng(lat, lng);
		
		return this;
	}

	@Override
	public ReverseGeocoding setLocation(Point point) {
		
		this.location = new LatLng(BigDecimal.valueOf(point.getY()), BigDecimal.valueOf(point.getX()));
		
		return this;
	}

	@Override
	public GeocodingResponse search() {
		
		if(this.location != null)
		{
			return new GeocoderJavaParserHelper().transform(this.geocoder.geocode(this.requestBuilder.setLocation(location).getGeocoderRequest()));
		}

		GeocodingResponse response = new GeocodingResponse();
		response.setReturnStatus(GeocodingServiceReturnStatus.CONFIG_ERROR);
		
		return response;
		
	}

	@Override
	public GeocodingResponse search(boolean clear) {

		GeocodingResponse response = this.search();
		
		if(clear)
		this.clearSearch();
		
		return response;
	}

	@Override
	public void clearSearch() {
		
		this.requestBuilder = new GeocoderRequestBuilder();
		
	}

	@Override
	public ReverseGeocoding setLanguage(Language language) {
		
		this.requestBuilder.setLanguage(language.value());
		
		return this;
		
	}

	@Override
	public ReverseGeocoding setRegion(String region) {

		this.requestBuilder.setRegion(region);
		
		return this;
	}


	
	

}
