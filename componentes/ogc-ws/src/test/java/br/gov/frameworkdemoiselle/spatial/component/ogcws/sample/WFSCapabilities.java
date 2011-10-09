package br.gov.frameworkdemoiselle.spatial.component.ogcws.sample;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.wfs.WFSDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeType;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;


public class WFSCapabilities {

	public static void main(String[] args) throws IOException, ParseException {
		//"http://localhost:8080/geoserver/ows?service=wfs&version=1.1.0&request=GetCapabilities"; 
		String getCapabilities = "http://www2.sipam.gov.br/geoserver/wfs?service=WFS&request=GetCapabilities";

		Map connectionParameters = new HashMap();
		connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilities );
		DataStore data = DataStoreFinder.getDataStore( connectionParameters );
//		String typeNames[] = data.getTypeNames();
//		String typeName = typeNames[1];
//		SimpleFeatureType schema = data.getSchema(typeName);
//
//		for (String type : data.getTypeNames()) {
//		
//			System.out.println(type);
//		}
//		
//		System.out.println(data.getInfo().getDescription());
//		
//		WFSDataStore wfsStore = (WFSDataStore) data;
//		System.out.println(wfsStore.getCapabilitiesURL());
		FeatureSource<SimpleFeatureType, SimpleFeature> source = data.getFeatureSource(data.getTypeNames()[1] );
//		System.out.println( "Metadata Bounds:"+ source.getBounds() );

//		// Step 5 - query
//		String geomName = schema.get
//		Envelope bbox = new Envelope( -100.0, -70, 25, 40 );
//
//		FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2( GeoTools.getDefaultHints() );
//		Object polygon = JTS.toGeometry( bbox );
//		Intersects filter = ff.intersects( ff.property( geomName ), ff.literal( polygon ) );
//
//		Query query = new DefaultQuery( typeName, filter, new String[]{ geomName } );
		
		FeatureCollection<SimpleFeatureType, SimpleFeature> features = source.getFeatures();

		ReferencedEnvelope bounds = new ReferencedEnvelope();
		Iterator<SimpleFeature> iterator = features.iterator();
		try {
		    while( iterator.hasNext() ){
		        Feature feature = (Feature) iterator.next();
		        System.out.print("LONGITUDE: " + ((Point)feature.getDefaultGeometryProperty().getValue()).getX() + " , ");
		        System.out.println("LATITUDE: " +((Point)feature.getDefaultGeometryProperty().getValue()).getY());
		        
//		    bounds.include( feature.getBounds() );
		}
//		    System.out.println( "Calculated Bounds:"+ bounds );
		}
		finally {
		    features.close( iterator );
		}
	}
}
