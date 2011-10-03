package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class JTSUtil {

	
	public Point createJTSPoint(double lat, double lng)
	{
		GeometryFactory geometryFactory = new GeometryFactory();
	    
	    Coordinate coord = new Coordinate(lng, lat);
	    Point point = geometryFactory.createPoint(coord);
	    
	    //TODO Needs modify to configurable SRID by config file
	    point.setSRID(4326);
	    
	    return point;
	}
	
	public Point createJTSPoint(String lat, String lng)
	{
		return this.createJTSPoint(Double.valueOf(lat), Double.valueOf(lng));	
	}
	
	
}
