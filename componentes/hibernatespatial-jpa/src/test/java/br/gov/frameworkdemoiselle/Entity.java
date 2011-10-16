package br.gov.frameworkdemoiselle;

import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

@Table(name="CONTACT")
public class Entity extends EntityParent {
	
	public static final String VAR = "";
	

	public Point ponto;


	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(name="THE_geom")
	public Point getPonto() {
		return ponto;
	}

	public void setPonto(Point ponto) {
		this.ponto = ponto;
	}
	
	
}
