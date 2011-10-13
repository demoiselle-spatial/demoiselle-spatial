package br.gov.frameworkdemoiselle.spatial.component.shapefile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.shapefile.exception.ShapefileWriterException;

public interface ShapefileWriter {

	public File writeSimpleFeatureShapefile(List<SimpleFeature> features) throws ShapefileWriterException;
	
	public InputStream writeSimpleFeatureShapefileToInputStream(List<SimpleFeature> features) throws ShapefileWriterException;
	
	public <T> File writeBeanShapefile(List<T> beans) throws ShapefileWriterException;
	
	public <T> InputStream writeBeanShapefileToInputStream(List<T> beans) throws ShapefileWriterException;
	
	
}
