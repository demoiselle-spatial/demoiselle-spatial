package br.gov.frameworkdemoiselle.spatial.component.kml;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public interface KMLBuilder {


	public void buildKmlAsFile(Object bean, File file);
	
	public void buildKmlAsStream(Object bean, OutputStream stream);
	
	public void buildKmlAsStream(Object bean, Writer writer);
	
	public Kml buildKml(Object bean);
	
	public InputStream buildKmlAsFile(Object bean);
	
	public void buildKmlAsFileFromSimpleFeature(SimpleFeature simpleFeature, File file);
	
	public void buildKmlAsStreamFromSimpleFeature(SimpleFeature simpleFeature, OutputStream stream);
	
	public void buildKmlAsStreamFromSimpleFeature(SimpleFeature simpleFeature, Writer writer);
	
	public Kml buildKmlFromSimpleFeature(SimpleFeature simpleFeature);
	
	public InputStream buildKmlAsFileFromSimpleFeature(SimpleFeature simpleFeature);
	
	public void buildKmlAsFileFromSimpleFeature(List<SimpleFeature> simpleFeatureList, File file);
	
	public void buildKmlAsStreamFromSimpleFeature(List<SimpleFeature> simpleFeatureList, OutputStream stream);
	
	public void buildKmlAsStreamFromSimpleFeature(List<SimpleFeature> simpleFeatureList, Writer writer);
	
	public Kml buildKmlFromSimpleFeature(List<SimpleFeature> simpleFeatureList);
	
	public InputStream buildKmlAsFileFromSimpleFeature(List<SimpleFeature> simpleFeatureList);
	
}
