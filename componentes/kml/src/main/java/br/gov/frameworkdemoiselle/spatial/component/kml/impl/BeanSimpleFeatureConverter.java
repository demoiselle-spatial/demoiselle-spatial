package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;


public class BeanSimpleFeatureConverter {

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SimpleFeature beanToSimpleFeature(Object bean)
	{
		SimpleFeature retorno = null;
		SimpleFeatureTypeBuilder sftb = new SimpleFeatureTypeBuilder();
		SimpleFeatureType type = null;
		SimpleFeatureBuilder sfb = null;
		
		if(!hasJTSGeometry(bean))
			throw new FeatureException("Bean does not contain property of type Geometry");
		
		try {
			Map<String, String> map = BeanUtils.describe(bean);
			Class clazz = null;
			sftb.setName(bean.getClass().getSimpleName());
			
			for (String property : map.keySet()) {
				
				clazz = BeanUtil.getDeclaredPropertyType(bean, property);
				if(clazz == null)
					continue;
				
				if(hasAcceptedType(clazz))
				{
					if(BeanHelper.hasPackageClass("com.vividsolutions.jts.geom.*", clazz))
					{
						//TODO Modify to obtain CRS from Geometry property
						sftb.setCRS(DefaultGeographicCRS.WGS84);	
					}
					sftb.add(property, clazz);
				}				
			}
			type = sftb.buildFeatureType();
			sfb = new SimpleFeatureBuilder(type);
			
			for (String property : map.keySet()) {
				
				clazz = BeanUtil.getDeclaredPropertyType(bean, property);
				
				if(clazz == null)
					continue;
				
				if(hasAcceptedType(clazz))
				{
					if(BeanHelper.hasPackageClass("com.vividsolutions.jts.geom.*", clazz))
					{
						sftb.setCRS(DefaultGeographicCRS.WGS84);
						if(BeanUtil.getDeclaredProperty(bean, property) == null)
							throw new FeatureException("Feature Geometry is null");
						
					}
					sfb.set(property, BeanUtil.getDeclaredProperty(bean, property));
				}				
			}	
			
			//TODO Modify to generate FID from bean ID
			retorno = sfb.buildFeature("fid.1");
			
			
		} catch (IllegalAccessException e) {
			throw new FeatureException("Error on bean reflection",e);
		} catch (InvocationTargetException e) {
			throw new FeatureException("Error on bean reflection",e);
		} catch (NoSuchMethodException e) {
			throw new FeatureException("Error on bean reflection",e);
		}
		
		return retorno;
			
	}
	
	private static boolean hasJTSGeometry(Object bean)
	{
		try {
			if(BeanHelper.hasPackageClass("com.vividsolutions.jts.geom.*", bean))
				return true;
			else
				return false;
		} catch (Exception e) {
			
			throw new FeatureException("Error when verify JTS Geometry", e);
		}
					
	}
	
	@SuppressWarnings("rawtypes")
	private static boolean hasAcceptedType(Class clazz)
	{
		if(BeanHelper.hasPackageClass("java.lang", clazz))
		{
			return true;
		}
		else if(clazz.equals(Date.class) || clazz.equals(Calendar.class) || clazz.equals(GregorianCalendar.class))
		{
			return true;
		}
		else if(BeanHelper.hasPackageClass("com.vividsolutions.jts.geom.*", clazz))
		{
			return true;
		}
		
		return false;
	}
	
}
