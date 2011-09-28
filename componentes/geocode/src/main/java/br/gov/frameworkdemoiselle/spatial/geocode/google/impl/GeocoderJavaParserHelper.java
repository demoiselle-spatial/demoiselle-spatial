package br.gov.frameworkdemoiselle.spatial.geocode.google.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.gov.frameworkdemoiselle.spatial.geocode.model.AddressType;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingAddressParts;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResult;
import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingServiceReturnStatus;
import br.gov.frameworkdemoiselle.spatial.geocode.util.JTSUtil;

import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderResult;
import com.vividsolutions.jts.geom.Point;

public class GeocoderJavaParserHelper {

	GeocodingResponse geocoding = new GeocodingResponse();
	
	GeocodingResult geocodingResult = null;
	
	List<GeocodingResult> geocodingResultList = null;
	
	List<GeocodingAddressParts> geocodingAddressPartsList = null;
	
	GeocodingAddressParts geocodingAddressParts = null;
	
	List<AddressType> addressType = null;
	
	Point point = null;
	
	public GeocodingResponse transform(GeocodeResponse response)
	{
		
		//returnStatus
		geocoding.setReturnStatus(GeocodingServiceReturnStatus.valueOf(response.getStatus().toString()));
		
		if( response.getResults()!= null && !response.getResults().isEmpty())
			geocodingResultList = new ArrayList<GeocodingResult>();
		
		for (GeocoderResult result : response.getResults()) {
			
			geocodingResult = new GeocodingResult();
			
			//point
			point = this.createJTSPoint(result.getGeometry().getLocation().getLat(),result.getGeometry().getLocation().getLng());
			geocodingResult.setPoint(point);
			
			//address
			geocodingResult.setAddress(result.getFormattedAddress());
			
			
			
			//address parts
			if(result.getAddressComponents()!= null && !result.getAddressComponents().isEmpty())
				geocodingAddressPartsList = new ArrayList<GeocodingAddressParts>();
			
			
			for (GeocoderAddressComponent component : result.getAddressComponents()) {
				
				geocodingAddressParts = new GeocodingAddressParts();
				
				geocodingAddressParts.setLongAddressName(component.getLongName());
				geocodingAddressParts.setShortAddressName(component.getShortName());
				
				//Address Type
				if(component.getTypes()!= null && !component.getTypes().isEmpty())
					addressType = new ArrayList<AddressType>();
				
				for (String type : component.getTypes()) {
					
					addressType.add(AddressType.fromValue(type));
				}
				
				geocodingAddressParts.setTypes(addressType);
				
				geocodingAddressPartsList.add(geocodingAddressParts);
				
			}
			
			geocodingResult.setAddressParts(geocodingAddressPartsList);
			
			geocodingResultList.add(geocodingResult);
			
		}
		
		geocoding.setResults(geocodingResultList);
		
		
		return geocoding;
	}
	
	private Point createJTSPoint(BigDecimal lat, BigDecimal lng)
	{   
	    return new JTSUtil().createJTSPoint(lat.doubleValue(), lng.doubleValue());
	}
	
}
