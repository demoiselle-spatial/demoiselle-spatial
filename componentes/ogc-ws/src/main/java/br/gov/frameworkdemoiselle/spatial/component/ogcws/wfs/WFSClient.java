package br.gov.frameworkdemoiselle.spatial.component.ogcws.wfs;

import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.ogcws.exception.WFSClientGetFeatureException;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.LayerWFS;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.Service;

public interface WFSClient {

	
	public List<SimpleFeature> getFeature(Service service, LayerWFS layerWFS) throws WFSClientGetFeatureException;
}
