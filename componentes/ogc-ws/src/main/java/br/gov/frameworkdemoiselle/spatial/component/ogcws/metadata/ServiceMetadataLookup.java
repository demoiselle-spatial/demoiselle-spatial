package br.gov.frameworkdemoiselle.spatial.component.ogcws.metadata;

import java.net.URL;

import br.gov.frameworkdemoiselle.spatial.component.ogcws.exception.ServiceMetadataLookupException;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.Service;

public interface ServiceMetadataLookup {

	public Service lookup(URL wmsCapabilities, URL wfsCapabilities) throws ServiceMetadataLookupException;
	
}
