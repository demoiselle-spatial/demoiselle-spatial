package br.gov.frameworkdemoiselle.spatial.sample.latinoware;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.feature.BeanSimpleFeatureConverter;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class BeanSimpleFeatureExample {

	public static void main(String[] args) throws ParseException {
		
		Point point = (Point) new WKTReader().read("POINT(-38.3331 -12.9133)");
		
		Aerodromo aerodromo = new Aerodromo("SBSV", "Int. Deputado Luís Eduardo Magalhães", point);
		
		SimpleFeature aerodromoSimpleFeature = BeanSimpleFeatureConverter.beanToSimpleFeature(aerodromo);

		System.out.println(aerodromoSimpleFeature);
		
	}
}
