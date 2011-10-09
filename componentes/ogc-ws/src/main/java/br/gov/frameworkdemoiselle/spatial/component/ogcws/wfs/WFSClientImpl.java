package br.gov.frameworkdemoiselle.spatial.component.ogcws.wfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

import br.gov.frameworkdemoiselle.spatial.component.ogcws.exception.WFSClientGetFeatureException;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.LayerWFS;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.Service;

public class WFSClientImpl implements WFSClient {

	@Override
	public List<SimpleFeature> getFeature(Service service, LayerWFS layerWFS) throws WFSClientGetFeatureException {
		
		System.setProperty("org.geotools.referencing.forceXY", "true");
		
		List<SimpleFeature> retorno = null;
		
		Map<String,Object> connectionParameters = new HashMap<String,Object>();
		DataStore data = null;
		FeatureSource<SimpleFeatureType, SimpleFeature> source = null;
		FeatureCollection<SimpleFeatureType, SimpleFeature> features = null;
		
		try {
			connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", service.getCapability().getWfsURL().toString());
			data = DataStoreFinder.getDataStore( connectionParameters );
			source = data.getFeatureSource(layerWFS.getName());
			features = source.getFeatures();
			
			if(features!= null && !features.isEmpty())
			{
				retorno = new ArrayList<SimpleFeature>();
				
				org.geotools.feature.FeatureIterator<SimpleFeature> iterator = features.features();
				
				try {
				     while( iterator.hasNext()  ){
				          retorno.add(iterator.next());
				     }
				 }
				 finally {
				     features.close( iterator );
				 }
			}
			
		} catch (IOException e) {
			
			throw new WFSClientGetFeatureException("Error on call getFeature", e);
		}
		
		coordinateAdapter(retorno);
		
		return retorno;
	}
	
	private void coordinateAdapter(List<SimpleFeature> features)
	{
		
		Geometry geometry = null;
		double aux;
		
		for (SimpleFeature simpleFeature : features) {
			
			geometry = (Geometry) simpleFeature.getDefaultGeometry();
			
			for (Coordinate coordinate : geometry.getCoordinates()) {
				
				aux = coordinate.x;
				coordinate.x = coordinate.y;
				coordinate.y = aux;
				
			}
			
		}
	}

}
