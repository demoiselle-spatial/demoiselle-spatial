package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import com.vividsolutions.jts.geom.LineString;

public class BeanSampleLineString extends AnotherBean{

	private String name;
	
	private Integer classification;
	
	private Double height;
	
	private LineString line;
	
	private AnotherBean anotherBean;

	
	
	public BeanSampleLineString(String name, Integer classification, Double height,
			LineString line,AnotherBean anotherBean) {
		super(1l);
		this.name = name;
		this.classification = classification;
		this.height = height;
		this.line = line;
		this.anotherBean = anotherBean;
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

	public AnotherBean getAnotherBean() {
		return anotherBean;
	}

	public void setAnotherBean(AnotherBean anotherBean) {
		this.anotherBean = anotherBean;
	}
	
	public void methodDummy()
	{
		
	}
}
