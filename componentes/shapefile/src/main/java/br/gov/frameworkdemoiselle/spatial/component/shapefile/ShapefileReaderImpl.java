package br.gov.frameworkdemoiselle.spatial.component.shapefile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.enterprise.inject.Default;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import br.gov.frameworkdemoiselle.spatial.component.shapefile.exception.ShapefileReaderException;

@Default
public class ShapefileReaderImpl implements ShapefileReader {

	@Override
	public List<SimpleFeature> readShapefile(File shapefile) throws ShapefileReaderException {
		
		List<SimpleFeature> retorno = null;
		FeatureCollection<SimpleFeatureType, SimpleFeature> features = null;
		
		Map<String, File> files = null;
		ShapefileDataStore newDataStore = null;
		ShapefileDataStoreFactory dataStoreFactory = null;
		Map<String, Serializable> params = null;
		SimpleFeatureSource sfs = null;
		
		try {
			files = this.deflateShapefileArchive(shapefile);
			
			
			dataStoreFactory = new ShapefileDataStoreFactory();
			params = new HashMap<String, Serializable>();
			    params.put("url", files.get("shp").toURI().toURL());
			newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
			
			sfs = newDataStore.getFeatureSource();
			features = sfs.getFeatures();
			
			if(features!= null && !features.isEmpty())
			{
				retorno = new ArrayList<SimpleFeature>();
				
				org.geotools.feature.FeatureIterator<SimpleFeature> iterator = features.features();
				
				try {
				     while( iterator.hasNext()  ){
				          retorno.add(iterator.next());
				     }
				 }
				 finally {
				     features.close(iterator);
				 }
			}
		} catch (MalformedURLException e) {
			throw new ShapefileReaderException("Error on read a shapefile", e);
		} catch (NoSuchElementException e) {
			throw new ShapefileReaderException("Error on read a shapefile", e);
		} catch (IOException e) {
			throw new ShapefileReaderException("Error on read a shapefile", e);
		}
	    
		return retorno;
	}
	
	public Map<String, File> deflateShapefileArchive(File zipShapefile) throws IOException, ShapefileReaderException
	{
		Map<String, File> retorno = new HashMap<String, File>();
		File file = null;
		String tempFilename = null;
		
		Map<String, InputStream> files = new ZipFileUtil().deflateZip(zipShapefile);
		
		// Find shp inputstream and create a tempfile
		for (String filename : files.keySet()) {
			
			if(filename.contains(".shp"))
			{
				file = File.createTempFile("demoiselle-spatial", ".shp");
				file.deleteOnExit();
				this.copyBytes(file, files.get(filename));
				retorno.put("shp", file);
				tempFilename = filename;
			}
		}
		
		if(tempFilename == null)
			throw new ShapefileReaderException("ShapefileReader does not found .shp file in zip archive");
		
		// Remove shp input from collection
		files.remove(tempFilename);
		
		// Store tempFilename to create another files with same generated name
		tempFilename = file.getAbsolutePath();
		
		// Create another file set from inputstream
		for (String filename : files.keySet()) {
			
			// drop files in not accepted extension
			if(this.describeFileExtenson(filename) == null)
				continue;
			
			file = new File(tempFilename.replace("shp", this.describeFileExtenson(filename)));
			file.deleteOnExit();
			this.copyBytes(file, files.get(filename));
			
			retorno.put(this.describeFileExtenson(filename), file);
		}
		
		return retorno;
	}
	
	private void copyBytes(File file, InputStream input) throws IOException
	{
		
		  OutputStream out=new FileOutputStream(file);
		  byte buf[]=new byte[1024];
		  int len;
		  while((len=input.read(buf))>0)
		  out.write(buf,0,len);

	}
	
	private String describeFileExtenson(String filename)
	{
		if(filename.contains(".shp"))
			return "shp";
		if(filename.contains(".shx"))
			return "shx";
		if(filename.contains(".dbf"))
			return "dbf";
		if(filename.contains(".prj"))
			return "prj";
		
		return null;
		
	}

}
