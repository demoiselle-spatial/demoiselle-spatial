package br.gov.frameworkdemoiselle.spatial.component.kml;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public interface KMLBuilder {

	public void buildKmlAsFile(Object bean, File file);
	
	public void buildKmlAsStream(Object bean, OutputStream stream);
	
	public void buildKmlAsStream(Object bean, Writer writer);
	
	public Kml buildKml(Object bean);
	
}
