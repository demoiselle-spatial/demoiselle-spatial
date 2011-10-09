package br.gov.frameworkdemoiselle.spatial.component.ogcws.model;

import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class SRID {

	private String name;
	
	private CoordinateReferenceSystem crs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CoordinateReferenceSystem getCrs() {
		return crs;
	}

	public void setCrs(CoordinateReferenceSystem crs) {
		this.crs = crs;
	}
	
	
}
