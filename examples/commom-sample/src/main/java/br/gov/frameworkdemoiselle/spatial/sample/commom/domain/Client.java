package br.gov.frameworkdemoiselle.spatial.sample.commom.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.vividsolutions.jts.geom.Geometry;

@Entity
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	@NotEmpty(message="{client.name.notempty}")
	private String name;
	
	@Column
	@NotEmpty(message="{client.telephone.notempty}")
	private String telephone;
	
	@Column
	@NotEmpty(message="{client.email.notempty}")
    @Email(message="{client.email.email}")
	private String email;
	
	@Embedded
	private Address address;
	
	@NotNull(message="{client.point.notnull}")
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Geometry point;
	
	public Client() {
		super();
	}
	
	public Client(String name, String telefone, String email) {
		super();
		this.name = name;
		this.telephone = telefone;
		this.email = email;
		
	}
	
	public Client(String name, String telefone, String email, Geometry point) {
		super();
		this.name = name;
		this.telephone = telefone;
		this.email = email;
		this.point = point;
	}



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

	

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Geometry getPoint() {
		return point;
	}

	public void setPoint(Geometry point) {
		this.point = point;
	}
	
	
	
	

}
