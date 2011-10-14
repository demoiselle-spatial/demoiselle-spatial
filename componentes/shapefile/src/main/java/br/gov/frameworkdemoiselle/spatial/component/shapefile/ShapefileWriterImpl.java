package br.gov.frameworkdemoiselle.spatial.component.shapefile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Default;

import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollections;
import org.opengis.feature.simple.SimpleFeature;

import br.gov.frameworkdemoiselle.spatial.component.feature.BeanSimpleFeatureConverter;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.exception.ShapefileWriterException;

@Default
public class ShapefileWriterImpl implements ShapefileWriter {

	public static final String FILE_PREFIX = "demoiselle-spatial";
	
	@Override
	public File writeSimpleFeatureShapefile(List<SimpleFeature> features) throws ShapefileWriterException {
		
			File shp = null;
			
			if(features == null)
				throw new ShapefileWriterException("Feature parameter is null");
			
			if(features.isEmpty())
				throw new ShapefileWriterException("Feature parameter is empty");
			
			try {
				//TODO Implements parameters by demoiselle properties
				//shp = File.createTempFile("demoiselle-spatial", ".shp",new File("/Users/otos/development/projeto/demoiselle-spatial/tmp"));

				shp = File.createTempFile("demoiselle-spatial", ".shp");
				ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
				Map<String, Serializable> params = new HashMap<String, Serializable>();
			        params.put("url", shp.toURI().toURL());
			        params.put("create spatial index", Boolean.TRUE);
			    ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
			    newDataStore.createSchema(features.get(0).getFeatureType());
			    
			    Transaction transaction = new DefaultTransaction("create");
			    String typeName = newDataStore.getTypeNames()[0];
			    SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
			    
			    if (featureSource instanceof SimpleFeatureStore) {
		        	SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;

		            featureStore.setTransaction(transaction);
		            SimpleFeatureCollection collection = FeatureCollections.newCollection();
		            
		            for (SimpleFeature simpleFeature : features) {
		            	collection.add(simpleFeature);
					}
		            //Transaction
		            try {
		                featureStore.addFeatures(collection);
		                transaction.commit();

		            } catch (Exception e) {
		                transaction.rollback();
		                throw new ShapefileWriterException("Error on write a shapefile",e);

		            } finally {
		                transaction.close();
		            }
		        } else {
		        	throw new ShapefileWriterException("Error on write a shapefile: [[" + featureSource + "]] does not supported");
		        }

			} catch (IOException e) {
				throw new ShapefileWriterException("Error on write a shapefile from SimpleFeature", e);
			}
		
		
			return this.inflateShapefileArchives(shp);
	}
	
	private File inflateShapefileArchives(File shp) throws ShapefileWriterException
	{
		File zip = null;
		
        try {
			File dbf = new File(shp.getAbsolutePath().replaceAll("shp", "dbf"));
			File prj = new File(shp.getAbsolutePath().replaceAll("shp", "prj"));
			File shx = new File(shp.getAbsolutePath().replaceAll("shp", "shx"));
			File fix = new File(shp.getAbsolutePath().replaceAll("shp", "fix"));
			File qix = new File(shp.getAbsolutePath().replaceAll("shp", "qix"));
			
			//TODO Implements parameters by demoiselle properties
			//zip = File.createTempFile("demoiselle-spatial", ".zip",new File(demoiselleproperty.temporarySHPFilesFolder));
			zip = File.createTempFile("demoiselle-spatial", ".zip");
			
			new ZipFileUtil().inflateZip(zip,shp,dbf,prj,shx,fix,qix);
			
			dbf.delete();
			prj.delete();
			shx.delete();
			fix.delete();
			qix.delete();
			shp.delete();
			zip.deleteOnExit();
			
		} catch (Exception e) {
			throw new ShapefileWriterException("Error on inflate Shapefile Archives to zip file", e);
		}
        
        return zip;

	}

	@Override
	public InputStream writeSimpleFeatureShapefileToInputStream(List<SimpleFeature> feature)
			throws ShapefileWriterException {
		
		InputStream retorno = null;
		File out = this.writeSimpleFeatureShapefile(feature);
		
		try {
			retorno = new BufferedInputStream(new FileInputStream(out));
		} catch (FileNotFoundException e) {
			throw new ShapefileWriterException("Error on create InputStream out", e);
		}
		
		return retorno;
	}

	@Override
	public <T> File writeBeanShapefile(List<T> beans) throws ShapefileWriterException {
		
		List<SimpleFeature> listFeatures = BeanSimpleFeatureConverter.beanListToSimpleFeatureList(beans);
		
		return this.writeSimpleFeatureShapefile(listFeatures);
	}

	@Override
	public <T> InputStream writeBeanShapefileToInputStream(List<T> beans)
			throws ShapefileWriterException {
		
		List<SimpleFeature> listFeatures = BeanSimpleFeatureConverter.beanListToSimpleFeatureList(beans);
		
		return this.writeSimpleFeatureShapefileToInputStream(listFeatures);
	}

}
