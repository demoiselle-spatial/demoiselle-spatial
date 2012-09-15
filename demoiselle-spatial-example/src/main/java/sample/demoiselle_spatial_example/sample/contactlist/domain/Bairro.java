package sample.demoiselle_spatial_example.sample.contactlist.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureName;
import br.gov.frameworkdemoiselle.spatial.query.SRID;

import com.vividsolutions.jts.geom.Geometry;

/**
 * 
 * @author fredestrela
 *
 */
@Entity
@XmlRootElement(name="bairro")
public class Bairro implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	@Column
	@FeatureName
	private String name;
	
	@OneToMany(mappedBy="bairro")
	private List<Lote> lotes;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}
}
