package br.gov.frameworkdemoiselle.spatial.component.kml.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.apache.commons.beanutils.BeanUtils;

public class BeanHelper {

	
	@SuppressWarnings("rawtypes")
	public static boolean hasClass(Class clazz, Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		@SuppressWarnings("unchecked")
		Map<String, String> map = BeanUtils.describe(bean);
		Class internalClazz = null;
		
		for (String fieldName : map.keySet()) {
					
			internalClazz = BeanUtil.getDeclaredPropertyType(bean, fieldName);
			
			//bypass if fieldName represents the self bean class description
			if(internalClazz == null)
				continue;
			
			if(internalClazz.equals(clazz))
				return true;
			
		}
	
		return false;
	}	
	
	
	@SuppressWarnings("rawtypes")
	public static boolean hasPackageClass(String packageName, Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		@SuppressWarnings("unchecked")
		Map<String, String> map = BeanUtils.describe(bean);
		Class clazz = null;
		
		for (String fieldName : map.keySet()) {
			
			clazz = BeanUtil.getDeclaredPropertyType(bean, fieldName);
			
			//bypass if fieldName represents the self bean class description
			if(clazz == null)
				continue;
			
			if(hasPackageClass(packageName, clazz))
				return true;
		}
		
		return false;
	}


	@SuppressWarnings("rawtypes")
	public static boolean hasPackageClass(String packageName, Class clazz) {
		
		if(packageName.contains("*"))
		{
			if(clazz.getPackage().getName().contains(packageName.replace(".*", "")))
			{
				return true;
			}
			 
		}
		else if(clazz.getPackage().getName().equalsIgnoreCase(packageName))
		{
			return true;
		}
		
		return false;
	}
	
}


