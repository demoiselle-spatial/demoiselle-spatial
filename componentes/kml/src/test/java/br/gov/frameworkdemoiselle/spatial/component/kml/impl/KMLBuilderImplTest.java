package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class KMLBuilderImplTest {

	
	KMLBuilderImpl KMLbuilder;
	Polygon polygon1,polygon2;
	Point point;
	LineString line;
	
	@Before
	public void init() throws ParseException
	{
		KMLbuilder = new KMLBuilderImpl();
		polygon1 = (Polygon) new WKTReader().read("POLYGON((-38.5293710697436 -13.0056888516009,-38.5288351640926 -13.0065451899837,-38.5284493120239 -13.0065869625121,-38.5285779293801 -13.0057932832693,-38.5287922916405 -13.0057723969391,-38.5291995799353 -13.0056261925788,-38.5293710697436 -13.0056888516009))");
		polygon2 = (Polygon) new WKTReader().read("POLYGON((-38.5284064395718 -13.004059711882,-38.5285564931541 -13.0049578286913,-38.5272703195916 -13.0041641442362,-38.5278490976947 -13.0041014848289,-38.5281277686332 -13.0040388254059,-38.5284064395718 -13.004059711882),(-38.5282563859895 -13.0042059171656,-38.5283421308937 -13.0045192139116,-38.5276561716604 -13.0042685765464,-38.5276561716604 -13.0042685765464,-38.5282563859895 -13.0042059171656),(-38.5284064395718 -13.0046027596437,-38.5282349497635 -13.0046654189243,-38.5284707482499 -13.0048116238509,-38.5284064395718 -13.0046027596437))");
		point = new JTSUtil().createJTSPoint("-12.9710208","-38.48760780000001");
		line = (LineString) new WKTReader().read("LINESTRING(-38.5301266967115 -13.0050831470574,-38.5298212304905 -13.0036472036533,-38.5291513484267 -13.0037568578063,-38.529563995778 -13.005297232453,-38.5299605659597 -13.0051458062167,-38.5299605659597 -13.0051458062167,-38.5300570289769 -13.0051040334456)");
	}
	
	@Test
	public void buildKmlTestPoint()
	{
		File file = new File("/Users/otos/development/projeto/demoiselle-spatial/tmp/HelloKmlPoint.kml");
		BeanSamplePoint bean = new BeanSamplePoint("Client 1", 1, 1.8, point , new AnotherBean(1l));
		
		KMLbuilder.buildKmlAsFile(bean, file);
			
	}
	
	@Test
	public void buildKmlTestPolygon()
	{
		File file = new File("/Users/otos/development/projeto/demoiselle-spatial/tmp/HelloKmlPolygon.kml");
		BeanSamplePolygon bean = new BeanSamplePolygon("Client 1", 1, 1.8, polygon1 , new AnotherBean(1l));
		
		KMLbuilder.buildKmlAsFile(bean, file);
			
	}
	
	@Test
	public void buildKmlTestPolygon2()
	{
		File file = new File("/Users/otos/development/projeto/demoiselle-spatial/tmp/HelloKmlPolygon2.kml");
		BeanSamplePolygon bean = new BeanSamplePolygon("Client 1", 1, 1.8, polygon2 , new AnotherBean(1l));
		
		KMLbuilder.buildKmlAsFile(bean, file);
			
	}
	
	@Test
	public void buildKmlTestLineString()
	{
		File file = new File("/Users/otos/development/projeto/demoiselle-spatial/tmp/HelloKmlLineString.kml");
		BeanSampleLineString bean = new BeanSampleLineString("Client 1", 1, 1.8, line , new AnotherBean(1l));
		
		KMLbuilder.buildKmlAsFile(bean, file);
			
	}
	
}
