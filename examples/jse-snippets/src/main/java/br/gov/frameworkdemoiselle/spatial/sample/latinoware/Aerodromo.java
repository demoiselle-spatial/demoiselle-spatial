package br.gov.frameworkdemoiselle.spatial.sample.latinoware;

import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureAttributeName;
import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureName;

import com.vividsolutions.jts.geom.Point;

public class Aerodromo {

	//@FeatureAttributeName(name="Codigo_Aeroporto")
	private String icao;
	
	//@FeatureName(compositeName="Aeroporto $")
	private String nome;
	
	private Point localizacao;
	
	public Aerodromo(String icao, String nome, Point localizacao) {
		super();
		this.icao = icao;
		this.nome = nome;
		this.localizacao = localizacao;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Point getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Point localizacao) {
		this.localizacao = localizacao;
	}
	
	
}
