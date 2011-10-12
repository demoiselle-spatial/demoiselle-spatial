package br.gov.frameworkdemoiselle.component.georest.geojson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.component.georest.geojson.exception.GeoJSONBuildException;
import br.gov.frameworkdemoiselle.component.georest.model.GeoJSONFeature;
import br.gov.frameworkdemoiselle.component.georest.model.GeoJSONFeatureCollection;
import br.gov.frameworkdemoiselle.component.georest.model.GeoJSONGeometry;
import br.gov.frameworkdemoiselle.spatial.component.feature.util.BeanHelper;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class GeoJSONBuilder {
	

	
	public GeoJSONFeatureCollection decodeSimpleFeatureList(List<SimpleFeature> features)
	{
		GeoJSONFeatureCollection geoJSONFeatureCollecation = new GeoJSONFeatureCollection();
		geoJSONFeatureCollecation.setFeatures(this.createFeatures(features));
		
		return geoJSONFeatureCollecation;
	}
	
	private List<GeoJSONFeature> createFeatures(List<SimpleFeature> features)
	{
		List<GeoJSONFeature> retorno = new ArrayList<GeoJSONFeature>();
		GeoJSONFeature geoJSONFeature = null;
		
		for (SimpleFeature feature : features) {

			geoJSONFeature = new GeoJSONFeature();
			geoJSONFeature.setId(feature.getID());
			
			geoJSONFeature.setGeometry(this.createGeometry((Geometry)feature.getDefaultGeometry()));
			geoJSONFeature.setBbox(this.encodeBoundingBox(((Geometry)feature.getDefaultGeometry()).getEnvelopeInternal()));
			
			if(feature.getAttributes() != null && !feature.getAttributes().isEmpty())
				geoJSONFeature.setProperties(this.createProperties(feature.getProperties()));
			
			retorno.add(geoJSONFeature);
		}
		
		return retorno;
	}
	
	private Map<String, Object> createProperties(Collection<Property> properties)
	{
		Map<String, Object> retorno = new HashMap<String, Object>();
		for (Property property : properties) {
			
			if(property.getName() == null || property.getValue() == null)
				continue;
			try {
				if(BeanHelper.hasPackageClass("com.vividsolutions.jts.geom.*", property.getValue().getClass()))
					continue;
			} catch (Exception e) {
				throw new GeoJSONBuildException("Error on parse create geojson properties",e);
			}			
			retorno.put(property.getName().toString(), property.getValue());			
		}		
		return retorno;

	}
	
	
    private GeoJSONGeometry createGeometry(Geometry g) {

    	GeoJSONGeometry retorno = new GeoJSONGeometry();
    	List<Object> listGeometries = new ArrayList<Object>();
    	
        GeometryType geometryType = getGeometryType(g);

        if (geometryType != GeometryType.MULTIGEOMETRY) {
 
            switch (geometryType) {
            case POINT:
            	retorno.setCoordinates(encodeCoordinate(g.getCoordinate()));
            	retorno.setType("POINT");
                break;

            case LINESTRING:
            	retorno.setCoordinates(encodeCoordinates(g.getCoordinates()));
            	retorno.setType("LINESTRING");
                break;
            case MULTIPOINT:
            	retorno.setCoordinates(encodeCoordinates(g.getCoordinates()));
            	retorno.setType("MULTIPOINT");
                break;

            case POLYGON:
            	retorno.setCoordinates(encodePolygon((Polygon) g));
            	retorno.setType("POLYGON");
                break;

            case MULTILINESTRING:
                
                for (int i = 0, n = g.getNumGeometries(); i < n; i++) {
                	listGeometries.add(encodeCoordinates(g.getGeometryN(i).getCoordinates()));
                }
                retorno.setCoordinates(listGeometries);
                retorno.setType("MULTILINESTRING");
                break;

            case MULTIPOLYGON:
                for (int i = 0, n = g.getNumGeometries(); i < n; i++) {
                	listGeometries.add(encodePolygon((Polygon) g.getGeometryN(i)));
                }
                retorno.setCoordinates(listGeometries);
                retorno.setType("MULTIPOLYGON");
                break;

            default:
                //should never happen.
                throw new GeoJSONBuildException("No implementation for "+geometryType);
            }
        } else {
        	
        	//TODO Needs to encode a GeometryCollection in future
            //retorno.setCoordinates(encodeGeomCollection((GeometryCollection) g).toString());
        }

        return retorno;
    }

//    private JSONObject encodeGeomCollection(GeometryCollection collection) {
//    	
//    	JSONObject retorno = new JSONObject();
//    	JSONArray geometries = new JSONArray();
//
//        for (int i = 0, n = collection.getNumGeometries(); i < n; i++) {
//        	geometries.put(createGeometry(collection.getGeometryN(i)));
//        }
//
//       retorno.put("geometries", geometries);
//        
//       return retorno;
//    }

    /**
     * Write the coordinates of a geometry
     * @param coords The coordinates to encode
     * @throws JSONException
     */
    private List<Object> encodeCoordinates(Coordinate[] coords)
        {
        
    	List<Object> retorno = new ArrayList<Object>();

        for (int i = 0; i < coords.length; i++) {
            Coordinate coord = coords[i];
            retorno.add(encodeCoordinate(coord));
        }

        return retorno;
    }

    private List<Object> encodeCoordinate(Coordinate coord) {
        
    	List<Object> retorno = new ArrayList<Object>();
    	
    	retorno.add(new Double(coord.x));
    	retorno.add(new Double(coord.y));
      
    	return retorno;
    }

    /**
     * Turns an envelope into an array [minX,minY,maxX,maxY]
     * @param env envelope representing bounding box
     */
    private List<Double> encodeBoundingBox(Envelope env) {
    	
    	List<Double> retorno = new ArrayList<Double>();

    	retorno.add(env.getMinX());
    	retorno.add(env.getMinY());
    	retorno.add(env.getMaxX());
    	retorno.add(env.getMaxY());
 
    	
    	return retorno;
    }

    /**
     * Writes a polygon
     * @param geometry The polygon to encode
     * @throws JSONException
     */
    private List<Object> encodePolygon(Polygon geometry) {
    	
    	List<Object> retorno = new ArrayList<Object>();
    	
    	retorno.add(encodeCoordinates(geometry.getExteriorRing().getCoordinates()));

        for (int i = 0, ii = geometry.getNumInteriorRing(); i < ii; i++) {
        	retorno.add(encodeCoordinates(geometry.getInteriorRingN(i).getCoordinates()));
        }

       return retorno; //end the linear ring
    }

	
    private static enum GeometryType {
        POINT("Point"),
        LINESTRING("LineString"),
        POLYGON("Polygon"),
        MULTIPOINT("MultiPoint"),
        MULTILINESTRING("MultiLineString"),
        MULTIPOLYGON("MultiPolygon"),
        MULTIGEOMETRY("GeometryCollection");

        private final String name;

        private GeometryType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
	
//    private static String getGeometryName(Geometry geometry) {
//	        final GeometryType type = getGeometryType(geometry);
//	        return type != null ? type.getName() : null;
//	    }

	    /**
	     * Gets the internal representation for the given Geometry
	     *
	     * @param geometry a Geometry
	     *
	     * @return int representation of Geometry
	     */
    private static GeometryType getGeometryType(Geometry geometry) {
	        final Class<?> geomClass = geometry.getClass();
	        final GeometryType returnValue;

	        if (geomClass.equals(Point.class)) {
	            returnValue = GeometryType.POINT;
	        } else if (geomClass.equals(LineString.class)) {
	            returnValue = GeometryType.LINESTRING;
	        } else if (geomClass.equals(Polygon.class)) {
	            returnValue = GeometryType.POLYGON;
	        } else if (geomClass.equals(MultiPoint.class)) {
	            returnValue = GeometryType.MULTIPOINT;
	        } else if (geomClass.equals(MultiLineString.class)) {
	            returnValue = GeometryType.MULTILINESTRING;
	        } else if (geomClass.equals(MultiPolygon.class)) {
	            returnValue = GeometryType.MULTIPOLYGON;
	        } else if (geomClass.equals(GeometryCollection.class)) {
	            returnValue = GeometryType.MULTIGEOMETRY;
	        } else {
	            returnValue = null;
	            //HACK!!! throw exception.
	        }

	        return returnValue;
	    }
	
	
    public static void main(String[] args) {
		
		
	}
}
