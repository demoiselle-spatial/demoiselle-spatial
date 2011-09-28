package br.gov.frameworkdemoiselle.spatial.geocode.model;

import java.util.List;

public class GeocodingResponse {

	private GeocodingServiceReturnStatus returnStatus;
	private List<GeocodingResult> results;
    
    
	public GeocodingServiceReturnStatus getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(GeocodingServiceReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}
	public List<GeocodingResult> getResults() {
		return results;
	}
	public void setResults(List<GeocodingResult> results) {
		this.results = results;
	}
    
    
}
