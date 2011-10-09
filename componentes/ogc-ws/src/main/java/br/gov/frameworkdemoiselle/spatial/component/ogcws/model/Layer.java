package br.gov.frameworkdemoiselle.spatial.component.ogcws.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.geotools.data.ows.CRSEnvelope;

public class Layer {

	private String name;
	
	private String title;
	
	private String _abstract;
	
	private List<String> keyWords;
	
	private Map<String, CRSEnvelope> boundingBoxes;
	
	private Set<String> listSRID;
	
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

	public String get_abstract() {
		return _abstract;
	}

	public void set_abstract(String _abstract) {
		this._abstract = _abstract;
	}

	public List<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}
	
	public Map<String, CRSEnvelope> getBoundingBoxes() {
		return boundingBoxes;
	}

	public void setBoundingBoxes(Map<String, CRSEnvelope> boundingBoxes) {
		this.boundingBoxes = boundingBoxes;
	}

	public Set<String> getListSRID() {
		return listSRID;
	}

	public void setListSRID(Set<String> listSRID) {
		this.listSRID = listSRID;
	}
	
	
}
