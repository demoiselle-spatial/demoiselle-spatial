package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;

import jodd.bean.BeanUtil;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.feature.BeanSimpleFeatureConverter;
import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureName;
import br.gov.frameworkdemoiselle.spatial.component.feature.util.BeanHelper;
import br.gov.frameworkdemoiselle.spatial.component.kml.KMLBuilder;
import br.gov.frameworkdemoiselle.spatial.component.kml.exception.KMLBuilderException;
import de.micromata.opengis.kml.v_2_2_0.Document;
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
	}

	@SuppressWarnings("unchecked")
	@Override
	public Kml buildKml(Object bean) {
		
		Kml kml = KmlFactory.createKml();
		Document document = kml.createAndSetDocument();
		SimpleFeature feature = null;
		String featureName = null;
		
		if(BeanHelper.implementsInterface(List.class, bean))
		{
			for (Object object : ((List<Object>)bean)) {
				
				feature = BeanSimpleFeatureConverter.beanToSimpleFeature(object);
				featureName = feature.getProperty("@featureName").getValue() == null?null:feature.getProperty("@featureName").getValue().toString();
				
				document.addToFeature(new SimpleFeatureKMLConverter().simpleFeatureToPlaceMark(feature, featureName, null));
			}
		}
		else
		{
			feature = BeanSimpleFeatureConverter.beanToSimpleFeature(bean);
			featureName = feature.getProperty("@featureName").getValue() == null?null:feature.getProperty("@featureName").getValue().toString();
			kml.setFeature(new SimpleFeatureKMLConverter().simpleFeatureToPlaceMark(feature, featureName, null));
		}
		return kml;
	}

	@Override
	public void buildKmlAsStream(Object bean, Writer writer) {
		
		Kml kml = this.buildKml(bean);
		kml.marshal(writer);
	}

}
