package br.gov.frameworkdemoiselle.spatial.component.ogcws.model;

import java.util.List;

public class WFS {

	private List<LayerWFS> layers;
	
	private String getURL;
	
	private String postURL;

	public List<LayerWFS> getLayers() {
		return layers;
	}

	public void setLayers(List<LayerWFS> layers) {
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
