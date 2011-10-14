package br.gov.frameworkdemoiselle.spatial.component.shapefile;

import java.io.File;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.shapefile.exception.ShapefileReaderException;

public interface ShapefileReader {

	public List<SimpleFeature> readShapefile(File shapefile) throws ShapefileReaderException;
}
