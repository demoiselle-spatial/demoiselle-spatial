package br.gov.frameworkdemoiselle.spatial.sample.commom.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

@Entity
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String telefone;
	
	@Column
	private String email;
	
	@Embedded
	private Address address;
	
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Geometry point;
	
	public Client() {
		super();
	}
	
	public Client(String name, String telefone, String email) {
		super();
		this.name = name;
		this.telefone = telefone;
		this.email = email;
		
	}
	
	public Client(String name, String telefone, String email, Geometry point) {
		super();
		this.name = name;
		this.telefone = telefone;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
