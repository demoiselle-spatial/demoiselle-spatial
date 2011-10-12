package br.gov.frameworkdemoiselle.spatial.component.shapefile.sample;



import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.BufferUnderflowException;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipException;

import org.geotools.data.shapefile.ShpFiles;
import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.dbf.DbaseFileReader.Row;
import org.geotools.data.shapefile.dbf.DbaseFileWriter;
import org.geotools.data.shapefile.shp.ShapeType;
import org.geotools.data.shapefile.shp.ShapefileReader;
import org.geotools.data.shapefile.shp.ShapefileWriter;
import org.geotools.styling.Extent;
import org.opengis.feature.type.GeometryType;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * Classe responsavel por realizar processamentos em cima de arquivo no padrao ESRI SHP.
 * A classe utiliza componentes da biblioteca geotools
 * @author rafael.soto
 *
 */
public class OGShapefile {
//
//	public static final int SHP_DEFAULT_FILE_LENGTH = 10000;
//	
//	
//	private ZIP zip;
//	
//	
//	public OGShapefile() {
//		zip = new ZIP();
//	}
//	
//	
//	/**
//	 * Inicializa a estrutura de um arquivo do modelo {@link Shapefile}
//	 * @param shapefileName
//	 * @return
//	 * @throws ShapefileException
//	 */
//	public Shapefile constroiShapefile(String shapefileName) throws ShapefileException {
//		
//		Shapefile retorno = null;
//		try {	
//			retorno = new Shapefile(shapefileName);
//		} catch (Exception cause) {
//			throw new ShapefileException("Error on create a new Shapefile",cause);
//		}
//		return retorno;
//	}
//
//	
//	
//	public ShapefileWriter constroiShapefileWriter(Shapefile shapefile)
//			throws ShapefileException {
//		
//		ShapefileWriter retorno = null;
//		
//		try {
//			
//			retorno = new ShapefileWriter(shapefile.getShpOut().getChannel(),shapefile.getShxOut().getChannel());
//			
//		} catch (Exception cause) {
//			
//			throw new ShapefileException("Error on create a geotools ShapefileWritter");
//		}
//		
//		return retorno;
//		
//		
//	}
//
//
//	
//	public void definirShapefileHeader(ShapefileWriter shapefileWriter,
//			Envelope bbox, ShapeType formatoGeometria, int quantidadeFeicoes)
//			throws ShapefileException {
//		try {
//			shapefileWriter.writeHeaders(bbox, formatoGeometria,quantidadeFeicoes, SHP_DEFAULT_FILE_LENGTH);
//			
//		} catch (Exception cause) {	
//			throw new ShapefileException("Error on define shapefile headers",cause);
//		}
//		
//	}
//	
//	public void definirShapefileHeader(ShapefileWriter shapefileWriter, Envelope bbox, ShapeType formatoGeometria, int quantidadeFeicoes, int length)
//			throws IOException {
//		
//			shapefileWriter.writeHeaders(bbox, formatoGeometria,quantidadeFeicoes, length);
//		
//	}
//
//
//	
//	public DbaseFileWriter constroiDBFWriter(Shapefile shapefile,
//			List<DBFColumn> colunas, int numRecord) throws ShapefileException {
//		
//		DbaseFileHeader dbfHeader = null;
//		DbaseFileWriter retorno = null;
//		
//		try {
//			dbfHeader = new DbaseFileHeader();
//			
//			for (DBFColumn column : colunas) {
//				dbfHeader.addColumn(column.getNomeColuna(), column.getTipoDado(), column.getTamanhoDadosColuna(), column.getQuantidadeCasasDecimais());
//			}
//			dbfHeader.setNumRecords(numRecord);
//			retorno = new DbaseFileWriter(dbfHeader,shapefile.getDbfOut().getChannel()); 
//			
//		} catch (Exception cause) {
//			
//			throw new ShapefileException("Error on create DBFFileWriter",cause);
//		}
//		
//		return retorno;
//	}
//
//
//	
//	public ShapefileReader constroiShapefileReader(Shapefile shapefile)
//			throws  ShapefileCorrompidoException, ErroCriarHeaderException{
//		
//		ShapefileReader retorno = null;
//		ShpFiles files = new ShpFiles(shapefile.getShpIN().);
//		
//		try {
//			retorno =  new ShapefileReader(shapefile.getShpIN() ,true,true,new GeometryFactory());
//		} 
//		catch (EOFException cause)
//		{
//			throw new ShapefileCorrompidoException(cause,TipoMensagemErro.ERRO_NAO_ESPERADO);
//		}
//		catch (Exception cause) {
//			throw new ErroCriarHeaderException(cause,TipoMensagemErro.ERRO_NAO_ESPERADO);
//		} 
//		
//		return retorno;
//	}
//
//
//	
//	public DbaseFileReader constroiDBFReader(Shapefile shapefile)
//			throws ShapefileException, ErroCriarDBFReaderException, ShapefileCorrompidoException {
//
//		DbaseFileReader retorno = null;
//		
//		try {
//			retorno =  new DbaseFileReader(Channels.newChannel(shapefile.getDbfIN()),true);
//		}
//		catch (EOFException cause)
//		{
//			throw new ShapefileCorrompidoException(TipoMensagemErro.ERRO_NAO_ESPERADO);
//		}
//		catch (Exception cause) {
//			throw new ErroCriarDBFReaderException(TipoMensagemErro.ERRO_NAO_ESPERADO);
//		} 
//		
//		return retorno;
//	}
//
//
//	
//	public ShapefileResultSet constroiShapefileResultSet(Shapefile shapefile)
//			throws ShapefileException, ErroCriarShapefilersException, ShapefileCorrompidoException {
//		
//		ShapefileReader shpReader = null;
//		DbaseFileReader dbfReader = null;
//		ShapefileResultSet shpResultSet = null;
//		ShapefileRow shpRow = null;
//		Row row = null;
//		Geometry shape = null;
//		String key = null;
//		Object value = null;
//		Map<String, Object> hashRow = null;
//		
//		try{
//		
//			shpReader = constroiShapefileReader(shapefile);
//			dbfReader = constroiDBFReader(shapefile);
//			shpResultSet = new ShapefileResultSet();
//			
//			while (shpReader.hasNext()) {
//				
//				
//				row = dbfReader.readRow();
//				shape = (Geometry) shpReader.nextRecord().shape();
//                
//				shpRow = new ShapefileRow();
//				hashRow = new HashMap<String, Object>();
//				
//				for (int i = 0; i < dbfReader.getHeader().getNumFields(); i++) {
//					
//					key = dbfReader.getHeader().getFieldName(i);
//					value = row.read(i);
//					
//					hashRow.put(key, value);
//					
//					
//				}
//				
//				shpRow.setGeometry(shape);
//				shpRow.setData(hashRow);
//				
//				shpResultSet.addRow(shpRow);
//		}
//			
//		}
//		catch (ShapefileCorrompidoException cause)
//		{
//			throw new ShapefileCorrompidoException(TipoMensagemErro.ERRO);
//		}
//		catch (BufferUnderflowException cause)
//		{
//			throw new ShapefileCorrompidoException(TipoMensagemErro.ERRO_NAO_ESPERADO);
//		}
//		catch (Exception cause) { 
//			throw new ErroCriarShapefilersException(TipoMensagemErro.ERRO_NAO_ESPERADO);
//		} 
//		
//		
//		return shpResultSet;
//	}
//
//
//	/**
//	 * Efetua a descompactacao do conteudo do arquivo zip enviado encapsulando os dados
//	 * na abstracao de um shapefile. O metodo efetua a validação do protocolo de nomenclatura
//	 * e tipos de arquivos que devem estar contidos em um ZIP de um arquivo shapefile.
//	 * @param zipFile
//	 * @return
//	 * @throws ShapefileException
//	 * @throws ShapefileArquivoForaPadraoException 
//	 * @throws ShapefileArquivoInvalidoException 
//	 * @throws ShapefileCorrompidoException 
//	 */
//	public Shapefile descompactaShapefile(InputStream zipFile) throws ShapefileArquivoForaPadraoException, ShapefileArquivoInvalidoException, ShapefileCorrompidoException{
//		
//		
//		Map<String,InputStream> listInputStream = new HashMap<String,InputStream>();
//		Shapefile retorno = null;
//		InputStream shpIN = null;
//		InputStream shxIN = null;
//		InputStream dbfIN = null;
//		
//		
//
//		try {
//			
//			listInputStream = zip.extrairZip(zipFile);
//			
//			
//			Set<String> listFileName = listInputStream.keySet();
//			
//			String baseFileName = null;
//			
//			//Verifica o protocolo de nomenclatura shapefile.
//			for (String fileName : listFileName) {
//				
//				if(fileName.subSequence(fileName.length() - 4, fileName.length()).equals(".shp") || fileName.subSequence(fileName.length() - 4, fileName.length()).equals(".shx") || fileName.subSequence(fileName.length() - 4, fileName.length()).equals(".dbf"))
//				{
//				
//				if(baseFileName == null)
//				{
//					baseFileName = fileName.replaceAll("\\..*", "");
//				}
//				else{
//					if(baseFileName.equals(fileName.replaceAll("\\..*", "")))
//					{
//						
//						baseFileName = fileName.replaceAll("\\..*", "");
//						
//					}
//					else{
//						throw new ShapefileArquivoForaPadraoException(TipoMensagemErro.ERRO);
//					
//					}
//				}
//				}
//				else{
//					throw new ShapefileArquivoInvalidoException(TipoMensagemErro.ERRO);
//				}
//				
//				
//				if(fileName.subSequence(fileName.length() - 4, fileName.length()).equals(".shp") )
//				{
//					shpIN = listInputStream.get(fileName);
//				}
//				else if(fileName.subSequence(fileName.length() - 4, fileName.length()).equals(".shx") )
//				{
//					shxIN = listInputStream.get(fileName);
//				}
//				else if(fileName.subSequence(fileName.length() - 4, fileName.length()).equals(".dbf") )
//				{
//					dbfIN = listInputStream.get(fileName);
//				}
//					
//			}
//			
//			retorno = new Shapefile(shpIN,shxIN,dbfIN);
//		}
//		catch (ZipException cause) {
//			throw new ShapefileCorrompidoException(TipoMensagemErro.ERRO);
//		}catch (Exception cause){
//			cause.printStackTrace();
//		}
//				
//		
//		return retorno;
//	}
//		
//	
//
//
//	
//	public OutputStream compactarShapefile(Shapefile shapefile)
//			throws ErroCompactarShapefileException {
//		
//		ByteArrayOutputStream retorno = null;
//		File[] arquivos = shapefile.getFiles();
//		
//		
//		try {
//			retorno = new ByteArrayOutputStream();
//
//			zip.criarZip(retorno, arquivos);
//			
//		} catch (Exception cause) {
//			
//			throw new ErroCompactarShapefileException(cause,TipoMensagemErro.ERRO_NAO_ESPERADO);
//			
//		} 		
//		
//		
//		return retorno;
//	}
//	
//	/**
//	 * Valida o tipo de geometria
//	 * @param geometry
//	 * @param geometryType
//	 * @return
//	 */
//	public boolean validateGeometryType(Geometry geometry,GeometryType geometryType)
//	{
//		
//		if(geometry.getGeometryType().equalsIgnoreCase(geometryType.toString()))
//		{
//			return true;
//		}
//		else{
//			return false;
//		}
//		
//	}
}
