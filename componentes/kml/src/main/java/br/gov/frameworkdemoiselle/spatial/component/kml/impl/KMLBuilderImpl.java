package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

import javax.enterprise.inject.Default;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.feature.BeanSimpleFeatureConverter;
import br.gov.frameworkdemoiselle.spatial.component.feature.util.BeanHelper;
import br.gov.frameworkdemoiselle.spatial.component.kml.KMLBuilder;
import br.gov.frameworkdemoiselle.spatial.component.kml.exception.KMLBuilderException;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Style;

@Default
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
		this.styleKML(document);
		SimpleFeature feature = null;
		String featureName = null;
		
		if(BeanHelper.implementsInterface(Collection.class, bean.getClass()))
		{
			for (Object object : ((Collection<Object>)bean)) {
				
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

	@Override
	public void buildKmlAsFileFromSimpleFeature(SimpleFeature simpleFeature, File file) {
		
		Kml kml = this.buildKmlFromSimpleFeature(simpleFeature);
		try {
			kml.marshal(file);
		} catch (FileNotFoundException e) {
			throw new KMLBuilderException("Error on marshal KML to file", e);
		}
		
	}

	@Override
	public void buildKmlAsStreamFromSimpleFeature(SimpleFeature simpleFeature,
			OutputStream stream) {
		
		Kml kml = this.buildKmlFromSimpleFeature(simpleFeature);
		try {
			kml.marshal(stream);
		} catch (FileNotFoundException e) {
			throw new KMLBuilderException("Error on marshal KML to file", e);
		}
		
	}

	@Override
	public void buildKmlAsStreamFromSimpleFeature(SimpleFeature simpleFeature, Writer writer) {
		
		Kml kml = this.buildKmlFromSimpleFeature(simpleFeature);
		
			kml.marshal(writer);
		
	}

	@Override
	public Kml buildKmlFromSimpleFeature(SimpleFeature simpleFeature) {
		
		Kml kml = KmlFactory.createKml();
		Document document = kml.createAndSetDocument();
		this.styleKML(document);

		String featureName = null;

			featureName = simpleFeature.getID() == null?null:simpleFeature.getID();
			document.addToFeature(new SimpleFeatureKMLConverter().simpleFeatureToPlaceMark(simpleFeature, featureName, null));
			//kml.setFeature();

		return kml;
	}

	@Override
	public void buildKmlAsFileFromSimpleFeature(List<SimpleFeature> simpleFeatureList, File file) {
		
		Kml kml = this.buildKmlFromSimpleFeature(simpleFeatureList);
		try {
			kml.marshal(file);
		} catch (FileNotFoundException e) {
			throw new KMLBuilderException("Error on marshal KML to file", e);
		}
		
	}

	@Override
	public void buildKmlAsStreamFromSimpleFeature(List<SimpleFeature> simpleFeatureList,
			OutputStream stream) {
		
		Kml kml = this.buildKmlFromSimpleFeature(simpleFeatureList);
		try {
			kml.marshal(stream);
		} catch (FileNotFoundException e) {
			throw new KMLBuilderException("Error on marshal KML to file", e);
		}
		
	}

	@Override
	public void buildKmlAsStreamFromSimpleFeature(List<SimpleFeature> simpleFeatureList,
			Writer writer) {
		
		Kml kml = this.buildKmlFromSimpleFeature(simpleFeatureList);
		kml.marshal(writer);

	}

	@Override
	public Kml buildKmlFromSimpleFeature(List<SimpleFeature> simpleFeatureList) {
		
		Kml kml = KmlFactory.createKml();
		Document document = kml.createAndSetDocument();
		this.styleKML(document);
		String featureName = null;
		
		if(simpleFeatureList == null)
			return null;
		
		if(simpleFeatureList.isEmpty())
			return null;
		
		for (SimpleFeature simpleFeature : simpleFeatureList) {

			featureName = simpleFeature.getID() == null?null:simpleFeature.getID();
			
			document.addToFeature(new SimpleFeatureKMLConverter().simpleFeatureToPlaceMark(simpleFeature, featureName, null));
		}
		
			
		return kml;
	}
	
	private void styleKML(Document document)
	{
		Style style = KmlFactory.createStyle();
		
		style.setId("demoiselle");
		style.createAndSetLineStyle().withColor("ff00ffff").withWidth(2.0);
		style.createAndSetPolyStyle().withColor("7fff0000");
		
		document.getStyleSelector().add(style);
		
	}

	@Override
	public InputStream buildKmlAsFile(Object bean) {

		InputStream retorno = null;
		File file = null;
		try {
			file = File.createTempFile("kml", ".kml");
			this.buildKmlAsFile(bean, file);
			retorno = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new KMLBuilderException("Error on temp file io ", e); 
		} catch (IOException e) {
			throw new KMLBuilderException("Error on temp file io ", e);
		}
		
		return retorno;
		
	}

	@Override
	public InputStream buildKmlAsFileFromSimpleFeature(
			SimpleFeature simpleFeature) {
		InputStream retorno = null;
		File file = null;
		try {
			file = File.createTempFile("kml", ".kml");
			this.buildKmlAsFileFromSimpleFeature(simpleFeature, file);
			retorno = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new KMLBuilderException("Error on temp file io ", e); 
		} catch (IOException e) {
			throw new KMLBuilderException("Error on temp file io ", e);
		}
		
		return retorno;
	}

	@Override
	public InputStream buildKmlAsFileFromSimpleFeature(
			List<SimpleFeature> simpleFeatureList) {
		InputStream retorno = null;
		File file = null;
		try {
			file = File.createTempFile("kml", ".kml");
			this.buildKmlAsFileFromSimpleFeature(simpleFeatureList, file);
			retorno = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new KMLBuilderException("Error on temp file io ", e); 
		} catch (IOException e) {
			throw new KMLBuilderException("Error on temp file io ", e);
		}
		
		return retorno;
	}

}
