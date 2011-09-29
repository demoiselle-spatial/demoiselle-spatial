package br.gov.frameworkdemoiselle.spatial.geocode.nominatim.model;

import br.gov.frameworkdemoiselle.spatial.geocode.util.JTSUtil;

import com.vividsolutions.jts.geom.Point;

public class NominatimRequestBuilder {

	private NominatimRequest request;
	
	public NominatimRequestBuilder() {
	
		this.request = new NominatimRequest();
		
	}
	
	public NominatimRequestBuilder setLocation(Point point)
	{
		this.request.setLocation(point);
		
		return this;
	}

	public NominatimRequestBuilder setLocation(String lat, String lon)
	{
		this.request.setLocation(new JTSUtil().createJTSPoint(lat, lon));
		
		return this;
	}
	
	public NominatimRequestBuilder setLocation(double lat, double lon)
	{
		this.request.setLocation(new JTSUtil().createJTSPoint(lat, lon));
		
		return this;
	}
	
	public NominatimRequestBuilder setUserMail(String userMail)
	{
		this.request.setUserMail(userMail);
		
		return this;
		
	}
	
	public NominatimRequest getNominatimRequest()
	{	
		NominatimRequest request = this.request;
		this.request = new NominatimRequest();
		
		return request;
	}
	
}
