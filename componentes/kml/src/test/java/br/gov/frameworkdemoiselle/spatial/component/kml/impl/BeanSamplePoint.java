package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import com.vividsolutions.jts.geom.Point;

public class BeanSamplePoint extends AnotherBean{

	private String name;
	
	private Integer classification;
	
	private Double height;
	
	private Point point;
	
	private AnotherBean anotherBean;

	
	
	public BeanSamplePoint(String name, Integer classification, Double height,
			Point point,AnotherBean anotherBean) {
		super(1l);
		this.name = name;
		this.classification = classification;
		this.height = height;
		this.point = point;
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

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
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
