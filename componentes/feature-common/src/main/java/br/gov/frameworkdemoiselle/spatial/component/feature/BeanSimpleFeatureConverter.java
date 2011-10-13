package br.gov.frameworkdemoiselle.spatial.component.feature;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureAttribute;
import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureAttributeExcluded;
import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureAttributeName;
import br.gov.frameworkdemoiselle.spatial.component.feature.annotation.FeatureName;
import br.gov.frameworkdemoiselle.spatial.component.feature.exception.FeatureException;
import br.gov.frameworkdemoiselle.spatial.component.feature.util.BeanHelper;


public class BeanSimpleFeatureConverter {

	
	public static <T> List<SimpleFeature> beanListToSimpleFeatureList(List<T> beanList)
	{
		List<SimpleFeature> retorno = null;
		
		if(beanList != null && !beanList.isEmpty())
			retorno = new ArrayList<SimpleFeature>();
		else
			return null;
		
		for (Object bean : beanList) {
			retorno.add(beanToSimpleFeature(bean));
		}
		
		return retorno;
	}
	
	public static SimpleFeature beanToSimpleFeature(Object bean)
	{
		SimpleFeature retorno = null;
		SimpleFeatureTypeBuilder sftb = new SimpleFeatureTypeBuilder();
		SimpleFeatureType type = null;
		SimpleFeatureBuilder sfb = null;
		String featurePropertyName = null;
		String featureName = null;
		
		if(!hasJTSGeometry(bean))
			throw new FeatureException("Bean does not contain property of type Geometry");
		
			Map<String, Object> map = parseGeoBean(bean);

			sftb.setName(bean.getClass().getSimpleName());
			sftb.add("@featureName", String.class);
			
			for (String property : map.keySet()) {
				
					if("geometry".equals(property))
					{
						sftb.setCRS(DefaultGeographicCRS.WGS84);
						sftb.setDefaultGeometry(property);
					}
						sftb.add(property, map.get(property).getClass());
			}

			type = sftb.buildFeatureType();
			sfb = new SimpleFeatureBuilder(type);
			
			for (String property : map.keySet()) 
				sfb.set(property, map.get(property));
					
			featurePropertyName = BeanHelper.getFieldNameByAnnontation(bean.getClass(), FeatureName.class);
			
			if(featurePropertyName != null)
			{
				featureName = BeanHelper.getAnnotationByFieldAndClass(featurePropertyName, bean.getClass(), FeatureName.class).compositeName();
				sfb.set("@featureName", featureName.replace("$",BeanUtil.getDeclaredProperty(bean, featurePropertyName).toString()));
			}		
			
			retorno = sfb.buildFeature(null);
		
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
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, Object> parseGeoBean(Object bean)
	{
		Map<String, Object> retorno = new HashMap<String, Object>();
		Class clazz = null;
		
		String featureAttributeName = null;
		
		boolean verifyAnnontation = BeanHelper.hasAnnontationInField(bean.getClass(), FeatureAttribute.class);
		
		try{
		Map<String, String> mapProperties = BeanUtils.describe(bean);
		
		for (String property : mapProperties.keySet()) {
			
			clazz = BeanUtil.getDeclaredPropertyType(bean, property);
			if(clazz == null)
				continue;
			
			
			{
				if(BeanHelper.hasPackageClass("com.vividsolutions.jts.geom.*", clazz))
				{
					
					if(BeanUtil.getDeclaredProperty(bean, property) == null)
						throw new FeatureException("Feature Geometry is null");
					
					//TODO Modify to obtain CRS from Geometry property
					retorno.put("geometry", BeanUtil.getDeclaredProperty(bean, property));
				}
				else{
					
					if(!hasAcceptedType(clazz))
						continue;
					
					if(BeanHelper.propertyHasAnnontation(property, bean.getClass(), FeatureAttributeExcluded.class))
						continue;
					
					FeatureAttributeName annotationFeatureAttributeName = BeanHelper.getAnnotationByFieldAndClass(property, bean.getClass(), FeatureAttributeName.class);
					if(annotationFeatureAttributeName != null)
						featureAttributeName = annotationFeatureAttributeName.name();
					else
						featureAttributeName = null;
					
					if(verifyAnnontation)
					{
						if(!BeanHelper.propertyHasAnnontation(property, bean.getClass(), FeatureAttribute.class))
							continue;
						
						if(featureAttributeName != null)
							retorno.put(featureAttributeName, BeanUtil.getDeclaredProperty(bean, property));
						else
							retorno.put(property, BeanUtil.getDeclaredProperty(bean, property));
					}
					else{
						if(featureAttributeName != null)
							retorno.put(featureAttributeName, BeanUtil.getDeclaredProperty(bean, property));
						else
							retorno.put(property, BeanUtil.getDeclaredProperty(bean, property));
					}
					
				}

				
			}				
		}
		}
		catch (Exception e) {
			new FeatureException("One problem occurred on parse GeoBean", e);
		}
		
		return retorno;
		
	}

	
}
