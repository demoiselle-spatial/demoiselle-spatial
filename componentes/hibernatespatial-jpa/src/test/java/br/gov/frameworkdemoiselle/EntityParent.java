package br.gov.frameworkdemoiselle;

import javax.persistence.Column;
import javax.persistence.Id;

import br.gov.frameworkdemoiselle.spatial.query.SRID;

@SRID("4326")
public class EntityParent {

	private final String VAR_PARENT = "";
	
	protected final String VAR_PARENT2 = "";
	
	@Id
	@Column(name="ID_COLUMN")
	private Long id;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
