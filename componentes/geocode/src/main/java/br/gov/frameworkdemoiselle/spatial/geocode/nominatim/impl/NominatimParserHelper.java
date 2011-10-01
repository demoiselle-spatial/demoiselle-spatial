package br.gov.frameworkdemoiselle.spatial.geocode.nominatim.impl;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

import br.gov.frameworkdemoiselle.spatial.geocode.model.AddressType;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingAddressParts;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResult;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingServiceReturnStatus;
import br.gov.frameworkdemoiselle.spatial.geocode.nominatim.model.NominatimResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.util.JTSUtil;

public class NominatimParserHelper {

	GeocodingResponse geocoding = new GeocodingResponse();
	
	GeocodingResult geocodingResult = null;
	
	List<GeocodingResult> geocodingResultList = null;
	
	List<GeocodingAddressParts> geocodingAddressPartsList = null;
	
	GeocodingAddressParts geocodingAddressParts = null;
	
	List<AddressType> addressType = null;
	
	Point point = null;
	
	public GeocodingResponse transform(NominatimResponse geocode) {
		
		if(geocode == null){
		geocoding.setReturnStatus(GeocodingServiceReturnStatus.ZERO_RESULTS);
		return geocoding;
		}
		
		geocoding.setReturnStatus(GeocodingServiceReturnStatus.OK);
		
		geocodingResultList = new ArrayList<GeocodingResult>();
		geocodingResult = new GeocodingResult();
		geocodingResult.setAddress(geocode.getDisplay_name());
		
		geocodingResult.setPoint(new JTSUtil().createJTSPoint(geocode.getLat(), geocode.getLon()));
		geocodingResultList.add(geocodingResult);		
		geocoding.setResults(geocodingResultList);
		
		return geocoding;
	
	}

	
}
