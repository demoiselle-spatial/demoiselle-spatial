	package br.gov.frameworkdemoiselle.spatial.component.shapefile.sample;

import java.io.File;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.feature.BeanSimpleFeatureConverter;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.ShapefileWriterImpl;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.dados.BeanSamplePolygon;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.dados.ParentBean;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.exception.ShapefileWriterException;

import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class TesteSHP {

	public static void main(String[] args) throws ShapefileWriterException, ParseException {
			
		 	Polygon polygon1;

			polygon1 = (Polygon) new WKTReader().read("POLYGON((-38.5293710697436 -13.0056888516009,-38.5288351640926 -13.0065451899837,-38.5284493120239 -13.0065869625121,-38.5285779293801 -13.0057932832693,-38.5287922916405 -13.0057723969391,-38.5291995799353 -13.0056261925788,-38.5293710697436 -13.0056888516009))");

			BeanSamplePolygon bean = new BeanSamplePolygon("Client 1", 1, 1.8, polygon1, new ParentBean(1l));
			
			SimpleFeature feature = BeanSimpleFeatureConverter.beanToSimpleFeature(bean);
			
			File zip = new ShapefileWriterImpl().writeShapefile(feature);
			
			System.out.println(zip.getAbsolutePath());
			
	}
	
}
