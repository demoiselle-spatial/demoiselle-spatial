package br.gov.frameworkdemoiselle.spatial.component.shapefile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.feature.BeanSimpleFeatureConverter;
import br.gov.frameworkdemoiselle.spatial.component.feature.util.JTSUtil;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.dados.BeanSampleLineString;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.dados.BeanSampleMultiPolygon;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.dados.BeanSamplePoint;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.dados.BeanSamplePolygon;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.dados.ParentBean;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.exception.ShapefileWriterException;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class ShapefileWriterTest {

	
	Polygon polygon1,polygon2;
	MultiPolygon multipolygon;
	Point point;
	LineString line;
	ShapefileWriter shapeWriter;
	
	@Before
	public void init() throws ParseException, IOException
	{
		polygon1 = (Polygon) new WKTReader().read("POLYGON((-38.5293710697436 -13.0056888516009,-38.5288351640926 -13.0065451899837,-38.5284493120239 -13.0065869625121,-38.5285779293801 -13.0057932832693,-38.5287922916405 -13.0057723969391,-38.5291995799353 -13.0056261925788,-38.5293710697436 -13.0056888516009))");
		polygon2 = (Polygon) new WKTReader().read("POLYGON((-38.5284064395718 -13.004059711882,-38.5285564931541 -13.0049578286913,-38.5272703195916 -13.0041641442362,-38.5278490976947 -13.0041014848289,-38.5281277686332 -13.0040388254059,-38.5284064395718 -13.004059711882),(-38.5282563859895 -13.0042059171656,-38.5283421308937 -13.0045192139116,-38.5276561716604 -13.0042685765464,-38.5276561716604 -13.0042685765464,-38.5282563859895 -13.0042059171656),(-38.5284064395718 -13.0046027596437,-38.5282349497635 -13.0046654189243,-38.5284707482499 -13.0048116238509,-38.5284064395718 -13.0046027596437))");
		point = new JTSUtil().createJTSPoint("-12.9710208","-38.48760780000001");
		line = (LineString) new WKTReader().read("LINESTRING(-38.5301266967115 -13.0050831470574,-38.5298212304905 -13.0036472036533,-38.5291513484267 -13.0037568578063,-38.529563995778 -13.005297232453,-38.5299605659597 -13.0051458062167,-38.5299605659597 -13.0051458062167,-38.5300570289769 -13.0051040334456)");
		multipolygon = (MultiPolygon) new WKTReader().read("MULTIPOLYGON(((-38.5330132185224 -13.0101408651456,-38.5330453728615 -13.0101486973816,-38.5331632721047 -13.0101252006727,-38.5332275807828 -13.0101800263234,-38.5332007855002 -13.0102975098198,-38.5330078594659 -13.0104097717754,-38.5329783846551 -13.0104411006842,-38.5329274736182 -13.0105246444216,-38.532914075977 -13.0105037584899,-38.5328176129598 -13.0104280469727,-38.5328256515445 -13.0104202147455,-38.5326863160753 -13.0103653891479,-38.5326755979623 -13.0103601676617,-38.5326755979623 -13.0103523354324,-38.5326863160753 -13.010347113946,-38.53269435466 -13.010347113946,-38.5327399066404 -13.0103105635382,-38.5327693814512 -13.0102740131249,-38.5328122539033 -13.0102244089841,-38.5328363696576 -13.0101617511079,-38.5328631649401 -13.0101121469446,-38.5329301531465 -13.0101121469446,-38.5329515893725 -13.0101017039616,-38.5329891027681 -13.0100990932157,-38.5329891027681 -13.0101304221638,-38.5330400138049 -13.010143475891,-38.5330132185224 -13.0101408651456)),((-38.5328370395396 -13.0100697223234,-38.5328470877706 -13.0100755965021,-38.5328263214266 -13.0100951770969,-38.5328082346109 -13.0100821233672,-38.5328310106011 -13.0100612373982,-38.5328370395396 -13.0100697223234)))");
		
		shapeWriter = new ShapefileWriterImpl();
		
			
	}
	
	@Test
	public void testPolygon1() throws ParseException, ShapefileWriterException
	{

		BeanSamplePolygon bean = new BeanSamplePolygon("Client 1", 1, 1.8, polygon1, new ParentBean(1l));	
		SimpleFeature feature = BeanSimpleFeatureConverter.beanToSimpleFeature(bean);
		File zip = shapeWriter.writeSimpleFeatureShapefile(addToList(feature));
		
		//System.out.println(zip.getAbsolutePath());
	}
	
	@Test
	public void testPolygon2() throws ParseException, ShapefileWriterException
	{

		BeanSamplePolygon bean = new BeanSamplePolygon("Client 1", 1, 1.8, polygon2, new ParentBean(1l));	
		SimpleFeature feature = BeanSimpleFeatureConverter.beanToSimpleFeature(bean);
		File zip = shapeWriter.writeSimpleFeatureShapefile(addToList(feature));
		
		//System.out.println(zip.getAbsolutePath());
	}
	
	@Test
	public void testPoint() throws ParseException, ShapefileWriterException
	{

		BeanSamplePoint bean = new BeanSamplePoint("Client 1", 1, 1.8, point, new ParentBean(1l));	
		SimpleFeature feature = BeanSimpleFeatureConverter.beanToSimpleFeature(bean);
		File zip = shapeWriter.writeSimpleFeatureShapefile(addToList(feature));
		
		//System.out.println(zip.getAbsolutePath());
	}
	
	@Test
	public void testLineString() throws ParseException, ShapefileWriterException
	{

		BeanSampleLineString bean = new BeanSampleLineString("Client 1", 1, 1.8, line, new ParentBean(1l));	
		SimpleFeature feature = BeanSimpleFeatureConverter.beanToSimpleFeature(bean);
		File zip = shapeWriter.writeSimpleFeatureShapefile(addToList(feature));
		
		//System.out.println(zip.getAbsolutePath());
	}
	
	@Test
	public void testMultiPolygon() throws ParseException, ShapefileWriterException
	{

		BeanSampleMultiPolygon bean = new BeanSampleMultiPolygon("Client 1", 1, 1.8, multipolygon, new ParentBean(1l));	
		SimpleFeature feature = BeanSimpleFeatureConverter.beanToSimpleFeature(bean);
		File zip = shapeWriter.writeSimpleFeatureShapefile(addToList(feature));
		
		//System.out.println(zip.getAbsolutePath());
	}
	
	@Test
	public void testListMultiPolygon() throws ParseException, ShapefileWriterException
	{

		BeanSamplePolygon bean = new BeanSamplePolygon("Client 1", 1, 1.8, polygon1, new ParentBean(1l));	
		SimpleFeature feature = BeanSimpleFeatureConverter.beanToSimpleFeature(bean);
		
		BeanSamplePolygon bean2 = new BeanSamplePolygon("Client 2", 2, 2.8, polygon2, new ParentBean(2l));	
		SimpleFeature feature2 = BeanSimpleFeatureConverter.beanToSimpleFeature(bean2);
		
		File zip = shapeWriter.writeSimpleFeatureShapefile(addToList(feature,feature2));
		
		//System.out.println(zip.getAbsolutePath());
	}
	
	
	public List<SimpleFeature> addToList(SimpleFeature... feature)
	{
		List<SimpleFeature> retorno = new ArrayList<SimpleFeature>();
		
		for (int i = 0; i < feature.length; i++) {
			retorno.add(feature[i]);
			
		}
		
		
		return retorno;
	}
}
