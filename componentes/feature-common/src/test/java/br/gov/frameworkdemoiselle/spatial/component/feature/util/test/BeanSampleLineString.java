package br.gov.frameworkdemoiselle.spatial.component.feature.util.test;

import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureName;

import com.vividsolutions.jts.geom.LineString;

public class BeanSampleLineString extends ParentBean{

	@FeatureName
	private String name;
	
	private Integer classification;
	
	private Double height;
	
	private LineString line;
	
	private ParentBean anotherBean;

	
	
	public BeanSampleLineString(String name, Integer classification, Double height,
			LineString line,ParentBean anotherBean) {
		super(1l);
		this.name = name;
		this.classification = classification;
		this.height = height;
		this.line = line;
		this.anotherBean = anotherBean;
	}
	
	public BeanSampleLineString() {
		super(1l);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getClassification() {
		return classification;
	}

	public void setClassification(Integer classification) {
		this.classification = classification;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public LineString getLine() {
		return line;
	}

	public void setLine(LineString line) {
		this.line = line;
	}

	public ParentBean getAnotherBean() {
		return anotherBean;
	}

	public void setAnotherBean(ParentBean anotherBean) {
		this.anotherBean = anotherBean;
	}
	
	public void methodDummy()
	{
		
	}
}
