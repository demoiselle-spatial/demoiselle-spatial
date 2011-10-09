package br.gov.frameworkdemoiselle.spatial.component.ogcws.metadata;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import br.gov.frameworkdemoiselle.spatial.component.ogcws.exception.ServiceMetadataLookupException;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.Service;

public class ServiceMetadataLookupImplTest {

	URL url1,url2,url3,url4,url5,urlWFS1,urlWFS2;
	
	@Before
	public void init() throws MalformedURLException
	{
		url1 = new URL("http://www2.dmsolutions.ca/cgi-bin/mswms_gmap?VERSION=1.1.0&REQUEST=GetCapabilities");			
		url2 = new URL("http://sigel.aneel.gov.br/arcgis/services/SIGEL/Geracao/MapServer/WMSServer?request=getcapabilities");
		url3 = new URL("http://www2.sipam.gov.br/geoserver/wms?service=WMS&request=GetCapabilities");
		url4 = new URL("http://labs.metacarta.com/wms/vmap0");
		url5 = new URL("http://t1.hypercube.telascience.org/cgi-bin/landsat7");
		urlWFS1 = new URL("http://www2.sipam.gov.br/geoserver/wfs?service=WFS&request=GetCapabilities");
		urlWFS2 = new URL("http://www.geoservicos.ibge.gov.br/geoserver/ows?service=WFS&request=GetCapabilities");
	}
	
	@Test
	public void testLookup1() throws ServiceMetadataLookupException
	{
		ServiceMetadataLookup lookup = new ServiceMetadataLookupImpl();
		
		Service service = lookup.lookup(url1, urlWFS1);
		
		System.out.println(service.getCapability().getFormats());
		System.out.println(service.getCapability().getWms().getGetURL());
		
		System.out.println(service.getCapability().getWfs().getLayers().get(0).getListSRID());
	}
	
	@Test
	public void testLookup2() throws ServiceMetadataLookupException
	{
		ServiceMetadataLookup lookup = new ServiceMetadataLookupImpl();
		
		Service service = lookup.lookup(url2, null);
		
		System.out.println(service.getCapability().getFormats());
		System.out.println(service.getCapability().getWms().getGetURL());
		
		System.out.println(service.getCapability().getWfs().getLayers().get(0).getListSRID());
	}
	

	public void testLookup3() throws ServiceMetadataLookupException
	{
		ServiceMetadataLookup lookup = new ServiceMetadataLookupImpl();
		
		Service service = lookup.lookup(url3, null);
		
		System.out.println(service.getCapability().getFormats());
		System.out.println(service.getCapability().getWms().getGetURL());
	}
	

	public void testLookup4() throws ServiceMetadataLookupException
	{
		ServiceMetadataLookup lookup = new ServiceMetadataLookupImpl();
		
		Service service = lookup.lookup(url4, null);
		
		System.out.println(service.getCapability().getFormats());
		System.out.println(service.getCapability().getWms().getGetURL());
	}
	

	public void testLookup5() throws ServiceMetadataLookupException
	{
		ServiceMetadataLookup lookup = new ServiceMetadataLookupImpl();
		
		Service service = lookup.lookup(url5, null);
		
		System.out.println(service.getCapability().getFormats());
		System.out.println(service.getCapability().getWms().getGetURL());
	}
}
