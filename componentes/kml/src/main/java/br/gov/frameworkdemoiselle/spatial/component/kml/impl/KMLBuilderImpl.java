package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.Writer;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.kml.KMLBuilder;
import br.gov.frameworkdemoiselle.spatial.component.kml.exception.KMLBuilderException;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;

public class KMLBuilderImpl implements KMLBuilder {

	@Override
	public void buildKmlAsStream(Object bean, OutputStream stream) {
		
		Kml kml = this.buildKml(bean);
		try {
			kml.marshal(stream);
		} catch (FileNotFoundException e) {
			throw new KMLBuilderException("Error on marshal KML to file", e);
		}
	}

	@Override
	public void buildKmlAsFile(Object bean, File file) {
		
		Kml kml = this.buildKml(bean);
		try {
			kml.marshal(file);
		} catch (FileNotFoundException e) {
			throw new KMLBuilderException("Error on marshal KML to file", e);
		}
//		placemark = kml.createAndSetPlacemark();
//		
//		kml.createAndSetPlacemark().withDescription(bean.getName())
//		   .withName("My KML Sample").withOpen(Boolean.TRUE)
//		   .createAndSetPoint().addToCoordinates(point.getX(), point.getY());
//		kml.marshal(new File("/Users/otos/development/projeto/demoiselle-spatial/tmp/HelloKml.kml"));
	}

	@Override
	public Kml buildKml(Object bean) {
		
		Kml kml = KmlFactory.createKml();
		SimpleFeature feature = BeanSimpleFeatureConverter.beanToSimpleFeature(bean);
		kml.setFeature(new SimpleFeatureKMLConverter().simpleFeatureToPlaceMark(feature, null, null));
		
		return kml;

	}

	@Override
	public void buildKmlAsStream(Object bean, Writer writer) {
		
		Kml kml = this.buildKml(bean);
		kml.marshal(writer);

	}
	
	


}
