package br.gov.frameworkdemoiselle.spatial.component.feature.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.apache.commons.beanutils.BeanUtils;

import br.gov.frameworkdemoiselle.spatial.component.feature.exception.FeatureException;

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
	
	@SuppressWarnings("rawtypes")
	public static boolean implementsInterface(Class _interface, Class beanClazz)
	{
		for (Class implementsInterface : beanClazz.getInterfaces()) {
			
			if(_interface.equals(implementsInterface))
				return true;
		}
		
		if(beanClazz.getSuperclass().equals(Object.class))
			return false;
		
		return implementsInterface(_interface, beanClazz.getSuperclass());
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean hasAnnontationInField(Class beanClazz, Class annontation)
	{
		for (Field field : beanClazz.getDeclaredFields()) {		
			if(field.isAnnotationPresent(annontation)){
				return true;
			}
		}
		
		if(beanClazz.getSuperclass().equals(Object.class))
			return false;
		
		return hasAnnontationInField(beanClazz.getSuperclass(), annontation);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean propertyHasAnnontation(String propertyName, Class beanClazz, Class annontation)
	{
		for (Field field : beanClazz.getDeclaredFields()) {		
			if(field.getName().equalsIgnoreCase(propertyName) && field.isAnnotationPresent(annontation)){
				return true;
			}
		}
		
		if(beanClazz.getSuperclass().equals(Object.class))
			return false;
		
		return propertyHasAnnontation(propertyName, beanClazz.getSuperclass(), annontation);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean classHasAnnontation(Class beanClazz, Class annontation)
	{
		for (Field field : beanClazz.getDeclaredFields()) {		
			if(field.isAnnotationPresent(annontation)){
				return true;
			}
		}
		
		if(beanClazz.getSuperclass().equals(Object.class))
			return false;
		
		return classHasAnnontation(beanClazz.getSuperclass(), annontation);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getFieldNameByAnnontation(Class beanClazz, Class annontation)
	{
		for (Field field : beanClazz.getDeclaredFields()) {		
			if(field.isAnnotationPresent(annontation)){
				return field.getName();
			}
		}
		
		if(beanClazz.getSuperclass().equals(Object.class))
			return null;
		
		return getFieldNameByAnnontation(beanClazz.getSuperclass(), annontation);

	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked"})	
	public static <T extends Annotation> T getAnnotationByFieldAndClass(String fieldName, Class clazz, Class<T> annontationClazz)
	{

		for (Field field : clazz.getDeclaredFields()) {		
			if(field.getName().equalsIgnoreCase(fieldName) && field.isAnnotationPresent(annontationClazz)){			
				for (Annotation fieldAnnontation : field.getAnnotations()) {				
					if(fieldAnnontation.annotationType().equals(annontationClazz))
						return (T) fieldAnnontation;
				}
			}
		}
		
		if(clazz.getSuperclass().equals(Object.class))
			return null;
		
		T findedAnnontation = getAnnotationByFieldAndClass(fieldName, clazz.getSuperclass(), annontationClazz);
		
		if(findedAnnontation == null)
			return null;
		else
			return findedAnnontation;
		
	}
	
	
	
}


