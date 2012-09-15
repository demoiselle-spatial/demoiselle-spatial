package sample.demoiselle_spatial_example.sample.contactlist.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;

import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureName;
import br.gov.frameworkdemoiselle.spatial.query.SRID;

import com.vividsolutions.jts.geom.Geometry;

/**
 * 
 * @author fredestrela
 *
 */
@Entity
@XmlRootElement(name = "lote")
public class Lote implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	@FeatureName
	@NotNull
	private String name;
	
	@Column
	@NotNull
	private String address;
	
	@ManyToOne
	@JoinColumn(name="bairro",updatable=false,nullable=false)
	@NotNull
	private Bairro bairro;
	
	@OneToMany(mappedBy="lote")
	private List<Casa> casas;
	
	@Type(type = "org.hibernatespatial.GeometryUserType")
	@SRID("4326")
	@NotNull
	private Geometry geometry;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public List<Casa> getCasas() {
		return casas;
	}

	public void setCasas(List<Casa> casas) {
		this.casas = casas;
	}

}
