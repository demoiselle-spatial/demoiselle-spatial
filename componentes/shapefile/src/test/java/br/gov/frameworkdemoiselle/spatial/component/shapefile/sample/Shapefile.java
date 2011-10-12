package br.gov.frameworkdemoiselle.spatial.component.shapefile.sample;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Classe que encapsula comportamento e estado de um arquivo shapefile.
 * @author rafael.soto
 *
 */
public class Shapefile {

	private String fileName;
	private String path;
	
	private FileOutputStream shpOut;
	private FileOutputStream shxOut;
	private FileOutputStream dbfOut;
	
	private InputStream shpIN;
	private InputStream shxIN;
	private InputStream dbfIN;
	
	private File shp;
	private File shx;
	private File dbf;
	
	
	public Shapefile(InputStream shpIN,InputStream shxIN,InputStream dbfIN)
	{
		this.shpIN = shpIN;
		this.shxIN = shxIN;
		this.dbfIN = dbfIN;
	}
	
	public Shapefile(String fileName) throws IOException {

		setFileName(fileName);
		shp = File.createTempFile(fileName, ".shp"); 
		shx = new File(shp.getAbsolutePath().replaceAll(".shp", ".shx"));
		dbf = new File(shp.getAbsolutePath().replaceAll(".shp", ".dbf"));

		shpOut = new FileOutputStream(shp);
		shxOut = new FileOutputStream(shx);
		dbfOut = new FileOutputStream(dbf);

		
		path = shp.getPath();
		
		shp.deleteOnExit();
		shx.deleteOnExit();
		dbf.deleteOnExit();

	}
	
	public void initializeShapefileOut(String fileName) throws IOException
	{
		shp = File.createTempFile(fileName, ".shp"); 
		shx = new File(shp.getAbsolutePath().replaceAll(".shp", ".shx"));
		dbf = new File(shp.getAbsolutePath().replaceAll(".shp", ".dbf"));
		
		shpOut = new FileOutputStream(shp);
		shxOut = new FileOutputStream(shx);
		dbfOut = new FileOutputStream(dbf);

		
		path = shp.getPath();
		
		shp.deleteOnExit();
		shx.deleteOnExit();
		dbf.deleteOnExit();
	    
		
	}
	


	
	public FileOutputStream getShpOut() {
		return shpOut;
	}




	public void setShpOut(FileOutputStream shpOut) {
		this.shpOut = shpOut;
	}




	public FileOutputStream getShxOut() {
		return shxOut;
	}




	public void setShxOut(FileOutputStream shxOut) {
		this.shxOut = shxOut;
	}




	public FileOutputStream getDbfOut() {
		return dbfOut;
	}




	public void setDbfOut(FileOutputStream dbfOut) {
		this.dbfOut = dbfOut;
	}




	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	public File getShp() {
		return shp;
	}
	public void setShp(File shp) {
		this.shp = shp;
	}
	public File getShx() {
		return shx;
	}
	public void setShx(File shx) {
		this.shx = shx;
	}
	public File getDbf() {
		return dbf;
	}
	public void setDbf(File dbf) {
		this.dbf = dbf;
	}
	
	public File[] getFiles()
	{
		File[] fileVector = new File[3];
		fileVector[0] = shp;
		fileVector[1] = shx;
		fileVector[2] = dbf;
		
		return fileVector;
	}

	public InputStream getShpIN() {
		return shpIN;
	}

	public void setShpIN(InputStream shpIN) {
		this.shpIN = shpIN;
	}

	public InputStream getShxIN() {
		return shxIN;
	}

	public void setShxIN(InputStream shxIN) {
		this.shxIN = shxIN;
	}

	public InputStream getDbfIN() {
		return dbfIN;
	}

	public void setDbfIN(InputStream dbfIN) {
		this.dbfIN = dbfIN;
	}
	
	
	
	
	
}
