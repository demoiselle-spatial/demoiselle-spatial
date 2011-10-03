package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import java.util.ArrayList;
import java.util.List;

import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.kml.exception.KMLBuilderException;

import com.vividsolutions.jts.geom.LineString;

import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.ExtendedData;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
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
			point.getCoordinates().add(this.extractCoordinateFromFeature(feature));
			
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
	

		private Coordinate extractCoordinateFromFeature(SimpleFeature feature)
		{
			com.vividsolutions.jts.geom.Point point = (com.vividsolutions.jts.geom.Point) feature.getDefaultGeometry();
			
			return new Coordinate(point.getX(), point.getY());
			
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
			LineString lineStringJTS = null;
			com.vividsolutions.jts.geom.Polygon polygon = (com.vividsolutions.jts.geom.Polygon) feature.getDefaultGeometry();
			
			for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
				
				lineStringJTS = polygon.getInteriorRingN(i);
				innerBoundary = KmlFactory.createBoundary();
				innerBoundary.setLinearRing(this.convertJTSLineStringToKMLLinearRing(lineStringJTS));
				
				listInnerBoundary.add(innerBoundary);
			}
			
			return listInnerBoundary;		
		}
		
		private LinearRing convertJTSLineStringToKMLLinearRing(LineString lineStringJTS)
		{	
			LinearRing linearRing = new LinearRing();
			List<Coordinate> coordinates = new ArrayList<Coordinate>();
			Coordinate coordinate = null;
			
			for (com.vividsolutions.jts.geom.Coordinate coordinateJTS : lineStringJTS.getCoordinates()) {
				
				coordinate = new Coordinate(coordinateJTS.x, coordinateJTS.y);		
				coordinates.add(coordinate);
			}
			
			linearRing.setCoordinates(coordinates);
			
			return linearRing;
		}

}
