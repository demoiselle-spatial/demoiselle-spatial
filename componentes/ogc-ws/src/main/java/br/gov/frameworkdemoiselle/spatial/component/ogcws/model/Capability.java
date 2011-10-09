package br.gov.frameworkdemoiselle.spatial.component.ogcws.model;

import java.net.URL;
import java.util.List;

public class Capability {

	private URL wfsURLcapabilities;
	
	private URL wmsURLcapabilities;
	
	private Service service;
	
	private WMS wms;
	
	private WFS wfs;
	
	private List<LayerOutpuFormat> formats;

	public URL getWfsURL() {
		return wfsURLcapabilities;
	}

	public void setWfsURL(URL wfsURL) {
		this.wfsURLcapabilities = wfsURL;
	}

	public URL getWmsURL() {
		return wmsURLcapabilities;
	}

	public void setWmsURL(URL wmsURL) {
		this.wmsURLcapabilities = wmsURL;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public WMS getWms() {
		return wms;
	}

	public void setWms(WMS wms) {
		this.wms = wms;
	}

	public WFS getWfs() {
		return wfs;
	}

	public void setWfs(WFS wfs) {
		this.wfs = wfs;
	}

	public List<LayerOutpuFormat> getFormats() {
		return formats;
	}

	public void setFormats(List<LayerOutpuFormat> formats) {
		this.formats = formats;
	}
}
