package br.gov.frameworkdemoiselle.spatial.sample.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.gov.frameworkdemoiselle.spatial.template.DemoiselleSpatialEnvelope;

import com.vividsolutions.jts.geom.Geometry;

public class ExtentValidator implements ConstraintValidator<Extent, Geometry> {
	
	private String extent;
	
	@Override
	public void initialize(Extent constraintAnnotation) {
		
		this.extent = constraintAnnotation.extent();
		
	}

	@Override
	public boolean isValid(Geometry value, ConstraintValidatorContext context) {
		
		if(value == null)
			return true;
		
		Geometry extentValidatorGeometry =  new DemoiselleSpatialEnvelope(this.extent).getGeometry();
		extentValidatorGeometry.setSRID(value.getSRID());
		
		if(extentValidatorGeometry.contains(value))
			return true;
		
		return false;	
	}
	
	

}
