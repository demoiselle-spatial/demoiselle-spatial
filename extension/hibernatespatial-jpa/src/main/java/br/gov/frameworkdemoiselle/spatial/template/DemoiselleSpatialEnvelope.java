package br.gov.frameworkdemoiselle.spatial.template;

import javax.xml.bind.annotation.XmlRootElement;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

@XmlRootElement
public class DemoiselleSpatialEnvelope {

	  private double minx;

	  private double maxx;

	  private double miny;

	  private double maxy;
	  
	  private Integer srid;

	  
	public DemoiselleSpatialEnvelope(String bbox)
	{
		String[] envelop = bbox.split(",");
		
		this.minx = new Double(envelop[0]);
		this.maxy = new Double(envelop[1]);
		this.miny = new Double(envelop[2]);
		this.maxx = new Double(envelop[3]);
		this.srid = -1;
	}
	
	public DemoiselleSpatialEnvelope(String bbox,Integer srid)
	{
		String[] envelop = bbox.split(",");
		
		this.minx = new Double(envelop[0]);
		this.maxy = new Double(envelop[1]);
		this.miny = new Double(envelop[2]);
		this.maxx = new Double(envelop[3]);
		this.srid = srid;
		
	}
	  
	public DemoiselleSpatialEnvelope() {
	
	}  
	  
	public DemoiselleSpatialEnvelope(double minx, double maxx, double miny, double maxy) {
		super();
		this.minx = minx;
		this.maxx = maxx;
		this.miny = miny;
		this.maxy = maxy;
		this.srid = -1;
	}
	
	public DemoiselleSpatialEnvelope(double minx, double maxx, double miny, double maxy,Integer srid) {
		super();
		this.minx = minx;
		this.maxx = maxx;
		this.miny = miny;
		this.maxy = maxy;
		this.srid = srid;
	}
	
	public DemoiselleSpatialEnvelope(Envelope envelop) {
		super();
		this.minx = envelop.getMinX();
		this.maxx = envelop.getMaxX();
		this.miny = envelop.getMinY();
		this.maxy = envelop.getMaxY();
		this.srid = -1;
	}
	
	public DemoiselleSpatialEnvelope(Envelope envelop,Integer srid) {
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
		return toGeometry(new Envelope(this.minx, this.maxx, this.miny, this.maxy),this.getSrid());
	}
	
	
    /**
     * Converts an envelope to a polygon.
     * <p>
     * The resulting polygon contains an outer ring with verticies:
     * (x1,y1),(x2,y1),(x2,y2),(x1,y2),(x1,y1)
     *
     * @param envelope The original envelope.
     * @return The envelope as a polygon.
     *
     * @since 2.4
     */
    public static Polygon toGeometry(Envelope e) {
        GeometryFactory gf = new GeometryFactory();

        return gf.createPolygon(gf.createLinearRing(
                new Coordinate[] {
                    new Coordinate(e.getMinX(), e.getMinY()),
                    new Coordinate(e.getMaxX(), e.getMinY()),
                    new Coordinate(e.getMaxX(), e.getMaxY()),
                    new Coordinate(e.getMinX(), e.getMaxY()),
                    new Coordinate(e.getMinX(), e.getMinY())
                }), null);
    }
    
    /**
     * Converts an envelope to a polygon.
     * <p>
     * The resulting polygon contains an outer ring with verticies:
     * (x1,y1),(x2,y1),(x2,y2),(x1,y2),(x1,y1)
     *
     * @param envelope The original envelope.
     * @return The envelope as a polygon.
     *
     * @since 2.4
     */
    public static Polygon toGeometry(Envelope e, Integer srid) {
        GeometryFactory gf = new GeometryFactory();

        Polygon retorno =  gf.createPolygon(gf.createLinearRing(
                new Coordinate[] {
                        new Coordinate(e.getMinX(), e.getMinY()),
                        new Coordinate(e.getMaxX(), e.getMinY()),
                        new Coordinate(e.getMaxX(), e.getMaxY()),
                        new Coordinate(e.getMinX(), e.getMaxY()),
                        new Coordinate(e.getMinX(), e.getMinY())
                    }), null);
        
        retorno.setSRID(srid.intValue());
        
        return retorno;
    }

	public Integer getSrid() {
		return srid;
	}

	public void setSrid(Integer srid) {
		this.srid = srid;
	}
    
    
    

	
}
