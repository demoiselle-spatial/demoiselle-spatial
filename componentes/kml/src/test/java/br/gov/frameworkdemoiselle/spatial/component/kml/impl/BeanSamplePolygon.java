package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import com.vividsolutions.jts.geom.Polygon;

public class BeanSamplePolygon extends AnotherBean{

	private String name;
	
	private Integer classification;
	
	private Double height;
	
	private Polygon polygon;
	
	private AnotherBean anotherBean;

	
	
	public BeanSamplePolygon(String name, Integer classification, Double height,
			Polygon polygon,AnotherBean anotherBean) {
		super(1l);
		this.name = name;
		this.classification = classification;
		this.height = height;
		this.polygon = polygon;
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

	

	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
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
