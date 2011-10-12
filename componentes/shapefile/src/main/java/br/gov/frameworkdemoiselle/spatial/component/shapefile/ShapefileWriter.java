package br.gov.frameworkdemoiselle.spatial.component.shapefile;

import java.io.File;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.shapefile.exception.ShapefileWriterException;

public interface ShapefileWriter {

	public File writeShapefile(SimpleFeature... feature) throws ShapefileWriterException;
	
	
}
