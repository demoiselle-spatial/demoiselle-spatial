package br.gov.frameworkdemoiselle.spatial.sample.latinoware;

import java.net.MalformedURLException;
import java.net.URL;

import br.gov.frameworkdemoiselle.spatial.component.ogcws.exception.ServiceMetadataLookupException;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.metadata.ServiceMetadataLookup;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.metadata.ServiceMetadataLookupImpl;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.LayerWFS;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.Service;

public class OGCLookupExample {

	;
	
	
	public static void main(String[] args) throws ServiceMetadataLookupException, MalformedURLException {
		
		URL urlSIPAMWFS = new URL("http://www2.sipam.gov.br/geoserver/wfs?service=WFS&request=GetCapabilities");
		URL urlSIPAMWMS = new URL("http://www2.sipam.gov.br/geoserver/wms?service=WMS&request=GetCapabilities");
		
		ServiceMetadataLookup lookup = new ServiceMetadataLookupImpl();
		
		Service service = lookup.lookup(urlSIPAMWMS, urlSIPAMWFS);
		
		System.out.println(service.getCapability().getFormats());
		
		for (LayerWFS layer : service.getCapability().getWfs().getLayers()) {
			
			System.out.println("Nome da camada: " + layer.getName());
			System.out.println("Projecoes Suportadas: " + layer.getListSRID());
		}
	}
}
