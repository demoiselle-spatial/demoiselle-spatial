package br.gov.frameworkdemoiselle.spatial.component.feature.util;

import org.junit.Before;
import org.junit.Test;

import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureAttribute;
import br.gov.frameworkdemoiselle.spatial.component.feature.util.test.BeanSampleLineString;

public class BeanHelperTest {

	BeanSampleLineString bean = null;
	
	@Before
	public void init()
	{
		bean = new BeanSampleLineString();
	}
	
	@Test
	public void testGetFieldAnnontation()
	{	
		FeatureAttribute annontation = BeanHelper.getAnnotationByFieldAndClass("sampleField", bean.getClass(), FeatureAttribute.class);
		
		System.out.println(annontation);
	}
	
	@Test
	public void testPropertyHasAnnontation()
	{
		System.out.println(BeanHelper.propertyHasAnnontation("sampleField", bean.getClass(), FeatureAttribute.class));
	}
	
	@Test
	public void testHasAnnontationInField()
	{
		System.out.println(BeanHelper.hasAnnontationInField(bean.getClass(), FeatureAttribute.class));
	}
	
	
	
}
