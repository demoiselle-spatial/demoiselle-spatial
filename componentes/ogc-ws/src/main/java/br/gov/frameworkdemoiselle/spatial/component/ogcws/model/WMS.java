package br.gov.frameworkdemoiselle.spatial.component.ogcws.model;

import java.util.List;

public class WMS {

	private Integer maxWidht;
	
	private Integer maxHeight;
	
	private String getURL;
	
	private String postURL;
	
	private List<Layer> layers;
	
	public Integer getMaxWidht() {
		return maxWidht;
	}

	public void setMaxWidht(Integer maxWidht) {
		this.maxWidht = maxWidht;
	}

	public Integer getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	public String getGetURL() {
		return getURL;
	}

	public void setGetURL(String getURL) {
		this.getURL = getURL;
	}

	public String getPostURL() {
		return postURL;
	}

	public void setPostURL(String postURL) {
		this.postURL = postURL;
	}
}
