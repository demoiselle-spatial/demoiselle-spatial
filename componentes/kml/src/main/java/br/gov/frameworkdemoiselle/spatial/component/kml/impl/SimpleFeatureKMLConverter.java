package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import java.util.ArrayList;
import java.util.List;

import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.kml.exception.KMLBuilderException;

import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;

import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.ExtendedData;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.Polygon;

public class SimpleFeatureKMLConverter {

	
	@SuppressWarnings("rawtypes")
	public Placemark simpleFeatureToPlaceMark(SimpleFeature feature, String name, String description)
	{	
		Placemark placemark = null;
		ExtendedData extendedData = null;
		
		
		placemark = KmlFactory.createPlacemark();
		if(name != null)
			placemark.setName(name);
		placemark.setVisibility(true);
		placemark.setOpen(true);
		if(description != null)
			placemark.setDescription(description);
		
		Class geometry = feature.getDefaultGeometry().getClass();
		
		try{
		
		if(com.vividsolutions.jts.geom.Point.class.equals(geometry))
		{	
			Point point = KmlFactory.createPoint();
			point.setExtrude(false);
			point.setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);			
			point.getCoordinates().add(this.extractCoordinateFromFeaturePoint(feature));
			
			placemark.setGeometry(point);
			
			
		}
		else if(com.vividsolutions.jts.geom.Polygon.class.equals(geometry))
		{
			Polygon polygon = KmlFactory.createPolygon();
			polygon.setExtrude(false);
			polygon.setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);
			polygon.setOuterBoundaryIs(this.extractOuterBoundaryFromFeature(feature));
			
			if(((com.vividsolutions.jts.geom.Polygon)feature.getDefaultGeometry()).getNumInteriorRing() > 0)
				polygon.setInnerBoundaryIs(this.extractInnerBoundaryFromFeature((feature)));
			
			placemark.setGeometry(polygon);
		}
		else if(com.vividsolutions.jts.geom.LineString.class.equals(geometry))
		{
			LineString lineString = KmlFactory.createLineString();
			lineString.setExtrude(false);
			lineString.setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);
			lineString.getCoordinates().addAll(this.extractCoordinatesFromFeatureLineString(feature));
			
			placemark.setGeometry(lineString);
		}
		else if(MultiPolygon.class.equals(geometry) || MultiPoint.class.equals(geometry) || MultiLineString.class.equals(geometry))
		{
			
//			MultiGeometry multi = KmlFactory.createMultiGeometry();
//			multi.setGeometry(this.ex)

		}
		
		if(feature.getAttributes()!= null && !feature.getAttributes().isEmpty())
		{
			StringBuffer sb = new StringBuffer();
			if(description!= null)
				sb.append(description);
			sb.append("<![CDATA[ ");
			sb.append("</br>");
			sb.append("<table border='1' padding='3' width='300'>");
			sb.append("<tr>");
			sb.append("<td>Propriedade</td>");
			sb.append("<td>Valor</td>");
			sb.append("</tr>");
			for (Property property : feature.getProperties()) {
				sb.append("<tr>");
				sb.append("<td>$</td>".replace("$", property.getName().toString()));
				sb.append("<td>$</td>".replace("$", property.getValue().toString()));
				sb.append("</tr>");
			}
			sb.append("</table>");
			
			placemark.setDescription(sb.toString());
		}
		
		}catch (Exception e) {
			throw new KMLBuilderException("Error on convert SimpleFeature to Placemark",e);
		}	
		
		return placemark;
	}
	

		private Coordinate extractCoordinateFromFeaturePoint(SimpleFeature feature)
		{
			com.vividsolutions.jts.geom.Point point = (com.vividsolutions.jts.geom.Point) feature.getDefaultGeometry();
			
			return new Coordinate(point.getX(), point.getY());
			
		}
		
		private List<Coordinate> extractCoordinatesFromFeatureLineString(SimpleFeature feature)
		{
			com.vividsolutions.jts.geom.LineString lineString = (com.vividsolutions.jts.geom.LineString) feature.getDefaultGeometry();
			
			return convertJTSCoordinatesToKMLCoordinates(lineString.getCoordinates());
			
		}
		
		private Boundary extractOuterBoundaryFromFeature(SimpleFeature feature)
		{
			Boundary outerBoundary = KmlFactory.createBoundary();
			com.vividsolutions.jts.geom.Polygon polygon = (com.vividsolutions.jts.geom.Polygon) feature.getDefaultGeometry();
			
			outerBoundary.setLinearRing(this.convertJTSLineStringToKMLLinearRing(polygon.getExteriorRing()));
			
			return outerBoundary;
			
		}
		
		private List<Boundary> extractInnerBoundaryFromFeature(SimpleFeature feature)
		{
			List<Boundary> listInnerBoundary = new ArrayList<Boundary>();
			Boundary innerBoundary = null;
			com.vividsolutions.jts.geom.LineString lineStringJTS = null;
			com.vividsolutions.jts.geom.Polygon polygon = (com.vividsolutions.jts.geom.Polygon) feature.getDefaultGeometry();
			
			for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
				
				lineStringJTS = polygon.getInteriorRingN(i);
				innerBoundary = KmlFactory.createBoundary();
				innerBoundary.setLinearRing(this.convertJTSLineStringToKMLLinearRing(lineStringJTS));
				
				listInnerBoundary.add(innerBoundary);
			}
			
			return listInnerBoundary;		
		}
		
		private LinearRing convertJTSLineStringToKMLLinearRing(com.vividsolutions.jts.geom.LineString lineStringJTS)
		{	
			LinearRing linearRing = new LinearRing();
			
			linearRing.setCoordinates(convertJTSCoordinatesToKMLCoordinates(lineStringJTS.getCoordinates()));
			
			return linearRing;
		}
		
		private List<Coordinate> convertJTSCoordinatesToKMLCoordinates(com.vividsolutions.jts.geom.Coordinate[] coordinatesJTS)
		{
			Coordinate coordinate = null;
			List<Coordinate> coordinates = new ArrayList<Coordinate>();
			
			for (com.vividsolutions.jts.geom.Coordinate coordinateJTS : coordinatesJTS) {
				
				coordinate = new Coordinate(coordinateJTS.x, coordinateJTS.y);		
				coordinates.add(coordinate);
			}
			
			return coordinates;
		}

}
