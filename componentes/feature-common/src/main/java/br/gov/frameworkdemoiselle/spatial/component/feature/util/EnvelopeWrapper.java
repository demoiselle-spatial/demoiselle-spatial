package br.gov.frameworkdemoiselle.spatial.component.feature.util;

import javax.xml.bind.annotation.XmlRootElement;

import org.geotools.geometry.jts.JTS;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

@XmlRootElement
public class EnvelopeWrapper {

	  private double minx;

	  private double maxx;

	  private double miny;

	  private double maxy;
	  
	  private Integer srid;

	  
	public EnvelopeWrapper(String bbox)
	{
		String[] envelop = bbox.split(",");
		
		this.minx = new Double(envelop[0]);
		this.maxy = new Double(envelop[1]);
		this.miny = new Double(envelop[2]);
		this.maxx = new Double(envelop[3]);
		
	}
	
	public EnvelopeWrapper(String bbox,Integer srid)
	{
		String[] envelop = bbox.split(",");
		
		this.minx = new Double(envelop[0]);
		this.maxy = new Double(envelop[1]);
		this.miny = new Double(envelop[2]);
		this.maxx = new Double(envelop[3]);
		this.srid = srid;
		
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
	
	public EnvelopeWrapper(double minx, double maxx, double miny, double maxy,Integer srid) {
		super();
		this.minx = minx;
		this.maxx = maxx;
		this.miny = miny;
		this.maxy = maxy;
		this.srid = srid;
	}
	
	public EnvelopeWrapper(Envelope envelop) {
		super();
		this.minx = envelop.getMinX();
		this.maxx = envelop.getMaxX();
		this.miny = envelop.getMinY();
		this.maxy = envelop.getMaxY();
	}
	
	public EnvelopeWrapper(Envelope envelop,Integer srid) {
		super();
		this.minx = envelop.getMinX();
		this.maxx = envelop.getMaxX();
		this.miny = envelop.getMinY();
		this.maxy = envelop.getMaxY();
		this.srid = srid;
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
	
	public Geometry getGeometry()
	{
		Geometry geom = JTS.toGeometry(new Envelope(this.minx, this.maxx, this.miny, this.maxy));
		
		if(srid!= null)
			geom.setSRID(this.srid);
		else
			geom.setSRID(-1);
		
		return geom; 
	}
}
