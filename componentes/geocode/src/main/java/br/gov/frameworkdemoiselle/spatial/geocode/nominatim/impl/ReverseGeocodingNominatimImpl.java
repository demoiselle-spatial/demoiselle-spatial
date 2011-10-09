package br.gov.frameworkdemoiselle.spatial.geocode.nominatim.impl;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.spatial.geocode.ReverseGeocoding;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingServiceReturnStatus;
import br.gov.frameworkdemoiselle.spatial.geocode.model.Language;
import br.gov.frameworkdemoiselle.spatial.geocode.nominatim.model.NominatimRequest;
import br.gov.frameworkdemoiselle.spatial.geocode.util.JTSUtil;

import com.vividsolutions.jts.geom.Point;


public class ReverseGeocodingNominatimImpl implements ReverseGeocoding, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private NominatimRequest request = new NominatimRequest();
	
	@Override
	public ReverseGeocoding setLocation(String lat, String lng) {
		
		this.request.setLocation(new JTSUtil().createJTSPoint(lat, lng));
		
		return this;
	}

	@Override
	public ReverseGeocoding setLocation(Point point) {

		this.request.setLocation(point);
		
		return this;
	}

	@Override
	public GeocodingResponse search() {

		if(this.request.getLocation() != null)
		{
			return new NominatimParserHelper().transform(new Nominatim().geocode(request));
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
		
		
		this.request = new NominatimRequest();
		
	}

	@Override
	public ReverseGeocoding setLanguage(Language language) {
		
		return this;
	}

	@Override
	public ReverseGeocoding setRegion(String region) {
		
		return this;
	}

	
	
}
