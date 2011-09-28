package br.gov.frameworkdemoiselle.spatial.geocode.model;


public enum GeocodingServiceReturnStatus {

	  	ERROR,
	    INVALID_REQUEST,
	    OK,
	    OVER_QUERY_LIMIT,
	    REQUEST_DENIED,
	    UNKNOWN_ERROR,
	    ZERO_RESULTS,
	    CONFIG_ERROR;

	    public String value() {
	        return name();
	    }

	    public static GeocodingServiceReturnStatus fromValue(String v) {
	        return valueOf(v);
	    }	
}
