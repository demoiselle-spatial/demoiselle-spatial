package br.gov.frameworkdemoiselle.spatial.sample.latinoware;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.feature.BeanSimpleFeatureConverter;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.ShapefileWriterImpl;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.exception.ShapefileWriterException;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class ShapefileExample {

	public static void main(String[] args) throws ParseException, ShapefileWriterException, FileNotFoundException, IOException {
		List<SimpleFeature> features = new ArrayList<SimpleFeature>();
		File file = new File("/home/benicio/latinoware/examples_out/shapedummy.zip");
		
		Point point = (Point) new WKTReader().read("POINT(-38.3331 -12.9133)");
		
		Aerodromo aerodromo = new Aerodromo("SBSV", "Int. Deputado Luís Eduardo Magalhães", point);
		
		SimpleFeature aerodromoSimpleFeature = BeanSimpleFeatureConverter.beanToSimpleFeature(aerodromo);
		features.add(aerodromoSimpleFeature);

		File shapeDummyZIP = new ShapefileWriterImpl().writeSimpleFeatureShapefile(features);
		
		copyBytes(file, new FileInputStream(shapeDummyZIP));
	}
	
	
	private static void copyBytes(File file, InputStream input) throws IOException
	{
		
		  OutputStream out=new FileOutputStream(file);
		  byte buf[]=new byte[1024];
		  int len;
		  while((len=input.read(buf))>0)
		  out.write(buf,0,len);

	}
}
