package br.gov.frameworkdemoiselle.spatial.component.shapefile.sample;

/**
 * Classe modelo para colunas de um arquivo DBF
 * @author rafael.soto
 *
 */
public class DBFColumn {

	public static final char CHARACTER = 'C';
	
	public static final char DATE = 'D';
	
	public static final char FLOAT = 'F';
	
	public static final char NUMBER = 'N';
	
	public static final int CHARACTER_LENGHT = 254;
	
	public static final int DATE_LENGHT = 8;
	
	public static final int FLOAT_LENGHT = 20;
	
	public static final int NUMBER_LENGHT = 18;	
	
	private String nomeColuna;
	private char tipoDado;
	private int tamanhoDadosColuna;
	private int quantidadeCasasDecimais;
	public String getNomeColuna() {
		return nomeColuna;
	}
	public void setNomeColuna(String nomeColuna) {
		this.nomeColuna = nomeColuna;
	}
	public char getTipoDado() {
		return tipoDado;
	}
	public void setTipoDado(char tipoDado) {
		this.tipoDado = tipoDado;
	}
	public int getTamanhoDadosColuna() {
		return tamanhoDadosColuna;
	}
	public void setTamanhoDadosColuna(int tamanhoDadosColuna) {
		this.tamanhoDadosColuna = tamanhoDadosColuna;
	}
	public int getQuantidadeCasasDecimais() {
		return quantidadeCasasDecimais;
	}
	public void setQuantidadeCasasDecimais(int quantidadeCasasDecimais) {
		this.quantidadeCasasDecimais = quantidadeCasasDecimais;
	}
	
	
	
	
}
