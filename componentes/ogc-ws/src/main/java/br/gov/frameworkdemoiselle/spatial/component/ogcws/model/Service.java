package br.gov.frameworkdemoiselle.spatial.component.ogcws.model;

import java.net.URL;

public class Service {

	private String name;
	
	private String title;
	
	private URL URLResource;
	
	private String _abstract;
	
	private Integer layerLimit;
	
	private Capability capability;
	
	private Responsible responsible;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public URL getURLResource() {
		return URLResource;
	}

	public void setURLResource(URL uRLResource) {
		URLResource = uRLResource;
	}

	public String get_abstract() {
		return _abstract;
	}

	public void set_abstract(String _abstract) {
		this._abstract = _abstract;
	}

	public Integer getLayerLimit() {
		return layerLimit;
	}

	public void setLayerLimit(Integer layerLimit) {
		this.layerLimit = layerLimit;
	}

	public Capability getCapability() {
		return capability;
	}

	public void setCapability(Capability capability) {
		this.capability = capability;
	}

	public Responsible getResponsable() {
		return responsible;
	}

	public void setResponsable(Responsible responsible) {
		this.responsible = responsible;
	}
	
	
}
