package br.gov.frameworkdemoiselle.spatial.sample.contactlist.util;


import com.vividsolutions.jts.geom.MultiPolygon;

public class BeanSampleMultiPolygon extends ParentBean{

	private String name;
	
	private Integer classification;
	
	private Double height;
	
	private MultiPolygon multiPolygon;
	
	private ParentBean anotherBean;

	
	
	public BeanSampleMultiPolygon(String name, Integer classification, Double height,
			MultiPolygon multiPolygon,ParentBean anotherBean) {
		super(1l);
		this.name = name;
		this.classification = classification;
		this.height = height;
		this.multiPolygon = multiPolygon;
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

	public MultiPolygon getMultiPolygon() {
		return multiPolygon;
	}

	public void setMultiPolygon(MultiPolygon multiPolygon) {
		this.multiPolygon = multiPolygon;
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
