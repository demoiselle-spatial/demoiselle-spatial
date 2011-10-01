package br.gov.frameworkdemoiselle.spatial.geocode.nominatim.impl;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import br.gov.frameworkdemoiselle.spatial.geocode.exception.GeocodeException;
import br.gov.frameworkdemoiselle.spatial.geocode.nominatim.model.NominatimRequest;
import br.gov.frameworkdemoiselle.spatial.geocode.nominatim.model.NominatimResponse;
import flexjson.JSONDeserializer;

public class Nominatim {

	public static final String NOMINATIM_BASE_URL = "http://nominatim.openstreetmap.org/reverse";
	
	
	
	public NominatimResponse geocode(NominatimRequest request)
	{
		
		String httpResponseBody = null;
		NominatimResponse response = null;
		
	    HttpClient client = new HttpClient();
	    NameValuePair[] parameters = null;
	    
	    if(request.getUserMail()!= null && !request.getUserMail().isEmpty())
	    	parameters = new NameValuePair[6];
	    else
	    	parameters = new NameValuePair[5];
	    
	    
	    //Montando a QUERYSTRING
	    parameters[0] = new NameValuePair("format","json");
	    parameters[1] = new NameValuePair("lat",request.getLocation().getY()+"");
	    parameters[2] = new NameValuePair("lon",request.getLocation().getX()+"");
	    parameters[3] = new NameValuePair("zoom","18");
	    parameters[4] = new NameValuePair("addressdetails","1");
	    if(request.getUserMail()!= null && !request.getUserMail().isEmpty())
	    	parameters[5] = new NameValuePair("email",request.getUserMail());
	    
	    //TODO Implements http proxy settings
	    //client.getHostConfiguration().setProxy(ip, new Integer(porta));
	    
//	    if(usuario != null && senha != null){
//	    client.getState().setProxyCredentials(
//				new AuthScope(ip, new Integer(porta), null),
//				new UsernamePasswordCredentials(usuario, senha));
//	    }
	    
	    GetMethod method = new GetMethod(NOMINATIM_BASE_URL);
	    method.setQueryString(parameters);
	    
	    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
	    new DefaultHttpMethodRetryHandler(3, false));
	    

	    try {
	    	
	      // Execute the method.
	      int statusCode = client.executeMethod(method);

	      if (statusCode != HttpStatus.SC_OK) {
	        System.err.println("Method failed: " + method.getStatusLine());
	      }

	     
	       httpResponseBody = method.getResponseBodyAsString();
	       
	       response = parseJSONNominatimReverseGeocoding(httpResponseBody);
	     
	      

	    } catch (HttpException e) {
	      
	      	throw new GeocodeException("Exception on request a geocoding external service", e);      
	      
	    } catch (IOException e) {
	      
	    	throw new GeocodeException("Exception on request a geocoding external service", e);	      
	      
	    } finally {
	  
	      method.releaseConnection();
	    } 
	    
	    
	    
	    return response;
	}
	
	
	private NominatimResponse parseJSONNominatimReverseGeocoding(String json)
	{
		return new JSONDeserializer<NominatimResponse>().use(null, NominatimResponse.class).deserialize(json);
	}
	

	
}
