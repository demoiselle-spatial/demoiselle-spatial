package br.gov.frameworkdemoiselle.spatial.template;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SpatialReflections {

	@SuppressWarnings({ "rawtypes" })
	public static List<Field> seekAllFieldAnnontation(Class clazz,Class<? extends Annotation > annotationClazz)
	{
		List<Field> fields = new ArrayList<Field>();
		
		for (Field field : describeAllFields(clazz)) {
			
			for (Annotation annotation : field.getAnnotations()) {
				if(annotation.annotationType().equals(annotationClazz))
					fields.add(field);
			}
		}
		
		return fields;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static List<Method> seekAllMethodAnnontation(Class clazz,Class<? extends Annotation > annotationClazz)
	{
		List<Method> methods = new ArrayList<Method>();
		
		for (Method method : describeAllMethods(clazz)) {
			
			for (Annotation annotation : method.getAnnotations()) {
				if(annotation.annotationType().equals(annotationClazz))
					methods.add(method);
			}
		}
		
		return methods;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static List<Method> describeAllMethods(Class clazz)
	{
		List<Method> methods = new ArrayList<Method>();
		for (Method method : clazz.getDeclaredMethods())
			methods.add(method);
		if(hasSuperclass(clazz))
			methods.addAll(describeAllMethods(clazz.getSuperclass()));
		return methods;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static List<Field> describeAllFields(Class clazz)
	{
		List<Field> fields = new ArrayList<Field>();
		for (Field field : clazz.getDeclaredFields())
			fields.add(field);
		if(hasSuperclass(clazz))
			fields.addAll(describeAllFields(clazz.getSuperclass()));
		return fields;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})	
	public static <T extends Annotation> T getAnnotationInClass(Class clazz, Class<T> annontationClazz)
	{

				for (Annotation fieldAnnontation : clazz.getAnnotations()) {				
					if(fieldAnnontation.annotationType().equals(annontationClazz))
						return (T) fieldAnnontation;
				}

		
		if(clazz.getSuperclass() == null)
			return null;
		if(clazz.getSuperclass().equals(Object.class))
			return null;
		
		T findedAnnontation = getAnnotationInClass(clazz.getSuperclass(), annontationClazz);
		
		if(findedAnnontation == null)
			return null;
		else
			return findedAnnontation;
		
	}
	
	@SuppressWarnings({ "rawtypes" })
	private static boolean hasSuperclass(Class clazz)
	{
		if(clazz.getSuperclass() == null)
			return false;
		if(clazz.getSuperclass().equals(Object.class))
			return false;
		return true;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static String formatGetSetMethodNameToPropertyName(String methodName)
	{
		String name = methodName;
		if(name.startsWith("get"))
			return methodName.replaceFirst("get", "").replace(name.charAt(3)+"", ((name.charAt(3)+"").toLowerCase()));
		if(name.startsWith("set"))
			return methodName.replaceFirst("get", "").replace(name.charAt(3)+"", ((name.charAt(3)+"").toLowerCase()));
		return methodName;
	}
	
	
}
