package br.gov.frameworkdemoiselle.spatial.query;

import br.gov.frameworkdemoiselle.spatial.template.DemoiselleSpatialEnvelope;

import com.vividsolutions.jts.geom.Envelope;

public class SpatialQueryArgument {
	
	private DemoiselleSpatialEnvelope extentFilter;
	
	private Integer outputSRID;
	
	public SpatialQueryArgument(DemoiselleSpatialEnvelope extentFilter, Integer outputSRID) {
		super();
		this.extentFilter = extentFilter;
		this.outputSRID = outputSRID;
	}
	
	public SpatialQueryArgument(Envelope extentFilter, Integer outputSRID) {
		super();
		setExtentFilterAsEnvelope(extentFilter);
		this.outputSRID = outputSRID;
	}
	
	public SpatialQueryArgument(Envelope extentFilter,Integer extentFilterSRID, Integer outputSRID) {
		super();
		setExtentFilterAsEnvelope(extentFilter,extentFilterSRID);
		this.outputSRID = outputSRID;
	}
	
	public SpatialQueryArgument( Integer outputSRID) {
		this.outputSRID = outputSRID;
	}
	
	public SpatialQueryArgument() {}


	public DemoiselleSpatialEnvelope getExtentFilter() {
		return extentFilter;
	}
	
	public Envelope getExtentFilterAsEnvelope() {
		return extentFilter.getEnvelope();
	}

	public void setExtentFilter(DemoiselleSpatialEnvelope extentFilter) {
		this.extentFilter = extentFilter;
	}
	
	public void setExtentFilterAsEnvelope(Envelope extentFilter) {
		this.extentFilter = new DemoiselleSpatialEnvelope(extentFilter);
	}
	
	public void setExtentFilterAsEnvelope(Envelope extentFilter, Integer extentFilterSRID) {
		this.extentFilter = new DemoiselleSpatialEnvelope(extentFilter,extentFilterSRID);
	}

	public Integer getOutputSRID() {
		return outputSRID;
	}

	public void setOutputSRID(Integer outputSRID) {
		this.outputSRID = outputSRID;
	}
}
