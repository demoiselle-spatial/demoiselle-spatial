package br.gov.frameworkdemoiselle.spatial.sample.latinoware;

import java.io.File;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.feature.BeanSimpleFeatureConverter;
import br.gov.frameworkdemoiselle.spatial.component.kml.impl.KMLBuilderImpl;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class KMLExample {

	
	public static void main(String[] args) throws ParseException {
		Point point = (Point) new WKTReader().read("POINT(-38.3331 -12.9133)");
		
		Aerodromo aerodromo = new Aerodromo("SBSV", "Int. Deputado Luís Eduardo Magalhães", point);
		
		SimpleFeature aerodromoSimpleFeature = BeanSimpleFeatureConverter.beanToSimpleFeature(aerodromo);
		
		new KMLBuilderImpl().buildKmlAsFileFromSimpleFeature(aerodromoSimpleFeature,new File("/home/benicio/latinoware/examples_out/kmldummy.kml"));
		
	}
	

}
