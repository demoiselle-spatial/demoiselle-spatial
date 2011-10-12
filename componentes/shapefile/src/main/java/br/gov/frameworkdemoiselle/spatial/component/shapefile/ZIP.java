package br.gov.frameworkdemoiselle.spatial.component.shapefile;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZIP {
	
	
	private static final int TAMANHO_BUFFER = 2048; // 2 Kb

	public Map<String,InputStream> extrairZip(File arquivoZip) throws ZipException {
			
		FileInputStream fis;
		try {
			fis = new FileInputStream(arquivoZip);
		} catch (FileNotFoundException e) {
			throw new ZipException(e.getMessage());
		}
		
		return extrairZip(fis);
	}
	
	/**
	 * Efetua a descompactação de um arquivo zip e retorna um hash contendo os streams e nome de cada arquivo 
	 * presente no zip.
	 * @param arquivoZip
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */
	public Map<String,InputStream> extrairZip( InputStream arquivoZip) throws ZipException {
		ZipInputStream zis = new ZipInputStream(arquivoZip);
		Map<String,InputStream> retorno = null;
		

		try {
			retorno = new HashMap<String,InputStream>();
			ZipEntry entrada ;
			
			while((entrada = zis.getNextEntry()) != null) {
				ByteArrayOutputStream d = new ByteArrayOutputStream();
				for (int c = zis.read(); c != -1; c = zis.read()) {
					d.write(c);
				}
				retorno.put(entrada.getName(), new ByteArrayInputStream(d.toByteArray()));
			}
		} catch (IOException e) {
		throw new ZipException(e.getMessage());
		}
		return retorno;
	}


	@SuppressWarnings("unchecked")
	public List criarZip( OutputStream os, File... arquivos ) throws ZipException {
		if( arquivos == null || arquivos.length < 1 ) {
			throw new ZipException();
		}
		List listaEntradasZip = new ArrayList();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream( os );
			for( int i=0; i<arquivos.length; i++ ) {
				String caminhoInicial = arquivos[i].getParent();
				List novasEntradas = adicionarArquivoNoZip( zos, arquivos[i], caminhoInicial );
				if( novasEntradas != null ) {
					listaEntradasZip.addAll( novasEntradas );
				}
			}
		}
	 catch( Exception e ) 
	 {
		 throw new ZipException(e.getMessage());
	 }
	
		finally {
			if( zos != null ) {
				try {
					zos.close();
				} catch( Exception e ) {}
			}
		}
		return listaEntradasZip;
	}

	@SuppressWarnings("unchecked")
	public List criarZip( File zip, File... arquivos ) throws ZipException {
		if( arquivos == null || arquivos.length < 1 ) {
			throw new ZipException();
		}
		
		FileOutputStream os;
		try {
			os = new FileOutputStream(zip);
		} catch (FileNotFoundException e) {
			throw new ZipException(e.getMessage());
		}
		
		return this.criarZip(os, arquivos);
	}
	/**
	 * Adiciona Arquivos no ZIP
	 * @param zos
	 * @param arquivo
	 * @param caminhoInicial
	 * @return List
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private List adicionarArquivoNoZip( ZipOutputStream zos, File arquivo, String caminhoInicial ) throws ZipException {
		List listaEntradasZip = new ArrayList();
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		byte buffer[] = new byte[TAMANHO_BUFFER];
		try {
			//diretórios não são adicionados
			if( arquivo.isDirectory() ) {
				//recursivamente adiciona os arquivos dos diretórios abaixo
				File[] arquivos = arquivo.listFiles();
				for( int i=0; i<arquivos.length; i++ ) {
					List novasEntradas = adicionarArquivoNoZip( zos, arquivos[i], caminhoInicial );
					if( novasEntradas != null ) {
						listaEntradasZip.addAll( novasEntradas );
					}
				}
				return listaEntradasZip;
			}
			String caminhoEntradaZip = null;
			int idx = arquivo.getAbsolutePath().indexOf(caminhoInicial);
			if( idx >= 0 ) {
				//calcula os diretórios a partir do diretório inicial
				//isso serve para não colocar uma entrada com o caminho completo
				caminhoEntradaZip = arquivo.getAbsolutePath().substring( idx+caminhoInicial.length()+1 );
			}
			ZipEntry entrada = new ZipEntry( caminhoEntradaZip );
			zos.putNextEntry( entrada );
			zos.setMethod( ZipOutputStream.DEFLATED );
			fis = new FileInputStream( arquivo );
			bis = new BufferedInputStream( fis, TAMANHO_BUFFER );
			int bytesLidos = 0;
			while((bytesLidos = bis.read(buffer, 0, TAMANHO_BUFFER)) != -1) {
				zos.write( buffer, 0, bytesLidos );
			}
			listaEntradasZip.add( entrada );
		}
		catch (Exception e) {
			throw new ZipException(e.getMessage());
		}
		finally {
			if( bis != null ) {
				try {
					bis.close();
				} catch( Exception e ) {}
			}
			if( fis != null ) {
				try {
					fis.close();
				} catch( Exception e ) {}
			}
		}
		return listaEntradasZip;
	}
}