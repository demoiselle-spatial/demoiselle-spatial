package br.gov.frameworkdemoiselle;

import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

@Table(name="CONTACT")
public class Entity extends EntityParent {
	
	public static final String VAR = "";
	

	public Geometry ponto;

	public Entity() throws ParseException {
	
		this.ponto = new WKTReader().read("POLYGON((-38.5293710697436 -13.0056888516009,-38.5288351640926 -13.0065451899837,-38.5284493120239 -13.0065869625121,-38.5285779293801 -13.0057932832693,-38.5287922916405 -13.0057723969391,-38.5291995799353 -13.0056261925788,-38.5293710697436 -13.0056888516009))");
		
		
	}
	
	
	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(name="THE_geom")
	public Geometry getPonto() {
		return ponto;
	}

	public void setPonto(Geometry ponto) {
		this.ponto = ponto;
	}
	
	
}
