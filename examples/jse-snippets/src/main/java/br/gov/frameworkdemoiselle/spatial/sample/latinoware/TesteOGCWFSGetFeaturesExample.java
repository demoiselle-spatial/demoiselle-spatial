package br.gov.frameworkdemoiselle.spatial.sample.latinoware;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.kml.KMLBuilder;
import br.gov.frameworkdemoiselle.spatial.component.kml.impl.KMLBuilderImpl;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.exception.ServiceMetadataLookupException;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.exception.WFSClientGetFeatureException;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.metadata.ServiceMetadataLookup;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.metadata.ServiceMetadataLookupImpl;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.Service;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.wfs.WFSClient;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.wfs.WFSClientImpl;

public class TesteOGCWFSGetFeaturesExample {

	public static void main(String[] args) throws MalformedURLException, ServiceMetadataLookupException, WFSClientGetFeatureException {
		
		URL wfs = new URL("http://www2.sipam.gov.br/geoserver/wfs?service=WFS&request=GetCapabilities");
		WFSClient wfsClient = new WFSClientImpl();
		ServiceMetadataLookup lookup = new ServiceMetadataLookupImpl();
		KMLBuilder kmlBuilder = new KMLBuilderImpl();
		
		Service service = lookup.lookup(null, wfs);
		
		List<SimpleFeature> features = wfsClient.getFeature(service, service.getCapability().getWfs().getLayers().get(1));
		
		kmlBuilder.buildKmlAsFileFromSimpleFeature(features, new File("/home/benicio/latinoware/examples_out/WFS_SIPAM.kml"));
		
		List<SimpleFeature> features2 = wfsClient.getFeature(service, service.getCapability().getWfs().getLayers().get(2));
		
		kmlBuilder.buildKmlAsFileFromSimpleFeature(features2, new File("/home/benicio/latinoware/examples_out/WFS_SIPAM_2.kml"));

	}
}
