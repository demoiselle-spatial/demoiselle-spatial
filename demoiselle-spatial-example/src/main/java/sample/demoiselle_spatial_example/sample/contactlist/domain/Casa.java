package sample.demoiselle_spatial_example.sample.contactlist.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;

import br.gov.frameworkdemoiselle.spatial.query.SRID;

import com.vividsolutions.jts.geom.Geometry;
/**
 * 
 * @author fredestrela
 *
 */
@Entity
@XmlRootElement(name = "casa")
public class Casa implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	@NotNull
	private Integer number;

	@ManyToOne
	@JoinColumn(name="lote",updatable=false,nullable=false)
	@NotNull
	private Lote lote;
	
	@Column
	@NotNull
	private Contact owner;
	
	@NotNull
	@Type(type = "org.hibernatespatial.GeometryUserType")
	@SRID("4326")
	private Geometry geometry;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Contact getOwner() {
		return owner;
	}

	public void setOwner(Contact owner) {
		this.owner = owner;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}
	
}
