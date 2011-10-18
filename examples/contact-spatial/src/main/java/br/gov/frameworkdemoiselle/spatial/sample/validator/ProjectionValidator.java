package br.gov.frameworkdemoiselle.spatial.sample.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vividsolutions.jts.geom.Geometry;

public class ProjectionValidator implements ConstraintValidator<Projection, Geometry> {
	
	private int srid;
	
	@Override
	public void initialize(Projection constraintAnnotation) {
		
		this.srid = constraintAnnotation.srid();
		
	}

	@Override
	public boolean isValid(Geometry value, ConstraintValidatorContext context) {
		
		if(value == null)
			return true;
		
			if(value.getSRID() != this.srid)
			return false;
		
			return true;	
	}
	
	

}
