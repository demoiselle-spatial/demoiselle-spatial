package br.gov.frameworkdemoiselle.spatial.component.shapefile.sample;



import java.util.Map;

import com.vividsolutions.jts.geom.Geometry;

/**
 * Classe modelo de um registro shapefile
 * @author rafael.soto
 *
 */
public class ShapefileRow {

	private Geometry geometry;
	
	private Map<String, Object> data;

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public String getString(String key)
	{
		return (String) data.get(key);
	}
	
	public Double getDouble(String key)
	{
		return (Double) data.get(key);
	}
	
	public Float getFloat(String key)
	{
		return (Float) data.get(key);
	}
	
	public Long getLong(String key)
	{
		return (Long) data.get(key);
	}
	
	public Integer getInteger(String key)
	{
		return (Integer) data.get(key);
	}
	
	public Number getNumber(String key)
	{
		return (Number) data.get(key);
	}
		
}
