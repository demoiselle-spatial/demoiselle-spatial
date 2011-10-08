package br.gov.frameworkdemoiselle.spatial.component.kml.impl.test;

import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.feature.BeanSimpleFeatureConverter;

import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class BeanSimpleFeatureConverterTest {

	public static Polygon polygon1;
	
	@Before
	public void init() throws ParseException
	{
		polygon1 = (Polygon) new WKTReader().read("POLYGON((-38.5293710697436 -13.0056888516009,-38.5288351640926 -13.0065451899837,-38.5284493120239 -13.0065869625121,-38.5285779293801 -13.0057932832693,-38.5287922916405 -13.0057723969391,-38.5291995799353 -13.0056261925788,-38.5293710697436 -13.0056888516009))");
	}
	
	@Test
	public void testBeanToSimpleFeaturePolygon()
	{
		BeanSamplePolygon bean = new BeanSamplePolygon("Client 1", 1, 1.8, polygon1, new ParentBean(1l));
		
		SimpleFeature feature = BeanSimpleFeatureConverter.beanToSimpleFeature(bean);
		
		System.out.println(feature);
	}
	
}
