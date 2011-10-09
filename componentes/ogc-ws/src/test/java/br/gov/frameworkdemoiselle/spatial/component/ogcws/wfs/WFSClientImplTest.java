package br.gov.frameworkdemoiselle.spatial.component.ogcws.wfs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.ogcws.exception.ServiceMetadataLookupException;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.exception.WFSClientGetFeatureException;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.metadata.ServiceMetadataLookup;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.metadata.ServiceMetadataLookupImpl;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.Service;

public class WFSClientImplTest {

	private URL wfs;
	
	@Before
	public void init() throws MalformedURLException
	{
		wfs = new URL("http://www2.sipam.gov.br/geoserver/wfs?service=WFS&request=GetCapabilities");

	}
	
	@Test
	public void testGetFeature() throws ServiceMetadataLookupException, WFSClientGetFeatureException
	{
		WFSClient wfsClient = new WFSClientImpl();
		ServiceMetadataLookup lookup = new ServiceMetadataLookupImpl();
		Service service = lookup.lookup(null, wfs);
		
		List<SimpleFeature> features = wfsClient.getFeature(service, service.getCapability().getWfs().getLayers().get(0));
		
		for (SimpleFeature simpleFeature : features) {
		
			System.out.println(simpleFeature);
		}
		
		
		
	}
}
