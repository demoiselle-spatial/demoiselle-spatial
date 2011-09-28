package br.gov.frameworkdemoiselle.spatial.geocode;

import br.gov.frameworkdemoiselle.spatial.geocode.model.GeocodingResponse;
import br.gov.frameworkdemoiselle.spatial.geocode.model.Language;

import com.vividsolutions.jts.geom.Envelope;

public interface GeocodingCommom {

	public GeocodingResponse search();
	
	public GeocodingResponse search(boolean clear);
	
	public void clearSearch();
	
	public Geocoding setLanguage(Language language);
	
	public Geocoding setRegion(String region);
	
	public Geocoding setBounds(Envelope boundary);
}
