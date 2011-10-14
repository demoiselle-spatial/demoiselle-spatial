package br.gov.frameworkdemoiselle.spatial.component.feature.util;

import javax.xml.bind.annotation.XmlRootElement;

import com.vividsolutions.jts.geom.Envelope;

@XmlRootElement
public class EnvelopeWrapper {

	  private double minx;

	  private double maxx;

	  private double miny;

	  private double maxy;

	  
	public EnvelopeWrapper(String bbox)
	{
		String[] envelop = bbox.split(",");
		
		this.minx = new Double(envelop[0]);
		this.maxx = new Double(envelop[1]);
		this.miny = new Double(envelop[2]);
		this.maxy = new Double(envelop[3]);
	}
	  
	public EnvelopeWrapper() {
	
	}  
	  
	public EnvelopeWrapper(double minx, double maxx, double miny, double maxy) {
		super();
		this.minx = minx;
		this.maxx = maxx;
		this.miny = miny;
		this.maxy = maxy;
	}
	
	public EnvelopeWrapper(Envelope envelop) {
		super();
		this.minx = envelop.getMinX();
		this.maxx = envelop.getMaxX();
		this.miny = envelop.getMinY();
		this.maxy = envelop.getMaxY();
	}

	public double getMinx() {
		return minx;
	}

	public void setMinx(double minx) {
		this.minx = minx;
	}

	public double getMaxx() {
		return maxx;
	}

	public void setMaxx(double maxx) {
		this.maxx = maxx;
	}

	public double getMiny() {
		return miny;
	}

	public void setMiny(double miny) {
		this.miny = miny;
	}

	public double getMaxy() {
		return maxy;
	}

	public void setMaxy(double maxy) {
		this.maxy = maxy;
	}
	  
	public Envelope getEnvelope()
	{
		return new Envelope(this.minx, this.maxx, this.miny, this.maxy);
	}
}
