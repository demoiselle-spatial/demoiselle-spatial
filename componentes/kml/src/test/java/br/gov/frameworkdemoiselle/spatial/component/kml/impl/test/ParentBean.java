package br.gov.frameworkdemoiselle.spatial.component.kml.impl.test;

import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureAttributeName;

public class ParentBean {

	private Long id;
	
	//@FeatureAttribute(name="Campo de Teste")
	//@FeatureAttributeExcluded
	@FeatureAttributeName(name="SAMPLE_FIELD")
	private String sampleField = "TESTE";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public ParentBean(Long id)
	{
		this.id = id;
	}

	public String getSampleField() {
		return sampleField;
	}

	public void setSampleField(String sampleField) {
		this.sampleField = sampleField;
	}
	
	
	
}
