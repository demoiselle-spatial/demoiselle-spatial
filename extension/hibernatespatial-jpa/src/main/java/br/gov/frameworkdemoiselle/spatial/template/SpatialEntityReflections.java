package br.gov.frameworkdemoiselle.spatial.template;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import br.gov.frameworkdemoiselle.spatial.query.SRID;

public class SpatialEntityReflections {

	
	public static String seekIdColumnName(Class clazz)
	{
		String idColumnName = null;
		idColumnName = seekIdColumnNameInFields(clazz);
		if(idColumnName!= null && !idColumnName.isEmpty())
			return idColumnName;
		idColumnName = seekIdColumnNameInMethods(clazz);
		if(idColumnName!= null && !idColumnName.isEmpty())
			return idColumnName;
		
		return idColumnName;
	}
	
	@SuppressWarnings("rawtypes")
	public static String seekIdColumnNameInFields(Class clazz)
	{
		List<Field> fields = SpatialReflections.seekAllFieldAnnontation(clazz, Id.class);
		if(fields.isEmpty())
			return null;
		if(fields.get(0).getAnnotation(Column.class) != null)
		{
			Column column = fields.get(0).getAnnotation(Column.class);
			if(!column.name().isEmpty())
				return column.name();	
		}
		return fields.get(0).getName();
	}
	
	@SuppressWarnings("rawtypes")
	public static String seekIdColumnNameInMethods(Class clazz)
	{
		List<Method> method = SpatialReflections.seekAllMethodAnnontation(clazz, Id.class);
		if(method.isEmpty())
			return null;
		if(method.get(0).getAnnotation(Column.class) != null)
		{
			Column column = method.get(0).getAnnotation(Column.class);
			if(!column.name().isEmpty())
				return column.name();	
		}
		return SpatialReflections.formatGetSetMethodNameToPropertyName(method.get(0).getName());
	}
	
	public static String seekGeometryTypeColumnName(Class clazz)
	{
		String geomColumnName = null;
		geomColumnName = seekGeometryTypeColumnNameInFields(clazz);
		if(geomColumnName!= null && !geomColumnName.isEmpty())
			return geomColumnName;
		geomColumnName = seekGeometryTypeColumnNameInMethods(clazz);
		if(geomColumnName!= null && !geomColumnName.isEmpty())
			return geomColumnName;
		
		return geomColumnName;
	}
	
	public static String seekEntityTableName(Class clazz)
	{	
		 if(clazz.isAnnotationPresent(Table.class) && !((Table) clazz.getAnnotation(Table.class)).name().isEmpty())
		 	return ((Table) clazz.getAnnotation(Table.class)).name().toLowerCase();
	 	 else
	 		return clazz.getSimpleName().toLowerCase();
		
	}
	
	@SuppressWarnings("rawtypes")
	public static String seekGeometryTypeColumnNameInFields(Class clazz)
	{
		List<Field> fields = SpatialReflections.seekAllFieldAnnontation(clazz, Type.class);
		if(fields.isEmpty())
			return null;
		for (Field field : fields) {
			if(field.getAnnotation(Type.class) != null && field.getAnnotation(Type.class).type().equals("org.hibernatespatial.GeometryUserType"))
			{
				if(field.getAnnotation(Column.class)!= null && !field.getAnnotation(Column.class).name().isEmpty())
					return field.getAnnotation(Column.class).name();
				else
					return field.getName();
			}	
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static String seekGeometryTypeColumnNameInMethods(Class clazz)
	{
		List<Method> methods = SpatialReflections.seekAllMethodAnnontation(clazz, Type.class);
		if(methods.isEmpty())
			return null;
		for (Method method : methods) {
			if(method.getAnnotation(Type.class) != null && method.getAnnotation(Type.class).type().equals("org.hibernatespatial.GeometryUserType"))
			{
				if(method.getAnnotation(Column.class)!= null && !method.getAnnotation(Column.class).name().isEmpty())
					return method.getAnnotation(Column.class).name();
				else
					return SpatialReflections.formatGetSetMethodNameToPropertyName(method.getName());
			}
		}
		return null;
	}
	
	public static String seekGeometryPropertyName(Class clazz)
	{
		String geomColumnName = null;
		geomColumnName = seekGeometryPropertyNameInFields(clazz);
		if(geomColumnName!= null && !geomColumnName.isEmpty())
			return geomColumnName;
		geomColumnName = seekGeometryPropertyNameInMethods(clazz);
		if(geomColumnName!= null && !geomColumnName.isEmpty())
			return geomColumnName;
		
		return geomColumnName;
	}
	
	public static String seekGeometryPerpertyType(Class clazz)
	{
		List<Field> fields = SpatialReflections.seekAllFieldAnnontation(clazz, Type.class);
		
		if(fields != null && !fields.isEmpty() && fields.get(0).getAnnotation(Type.class).type().equals("org.hibernatespatial.GeometryUserType"))
		{			
			return fields.get(0).getType().getSimpleName();
		}
		
		List<Method> methods = SpatialReflections.seekAllMethodAnnontation(clazz, Type.class);
		
		if(methods != null && !methods.isEmpty() && methods.get(0).getAnnotation(Type.class).type().equals("org.hibernatespatial.GeometryUserType"))
		{
			return methods.get(0).getReturnType().getSimpleName();
		}
		
		return null;
		
	}
	
	@SuppressWarnings("rawtypes")
	public static String seekGeometryPropertyNameInFields(Class clazz)
	{
		List<Field> fields = SpatialReflections.seekAllFieldAnnontation(clazz, Type.class);
		if(fields.isEmpty())
			return null;
		for (Field field : fields) {
			if(field.getAnnotation(Type.class) != null && field.getAnnotation(Type.class).type().equals("org.hibernatespatial.GeometryUserType"))
					return field.getName();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static String seekGeometryPropertyNameInMethods(Class clazz)
	{
		List<Method> methods = SpatialReflections.seekAllMethodAnnontation(clazz, Type.class);
		if(methods.isEmpty())
			return null;
		for (Method method : methods) {
			if(method.getAnnotation(Type.class) != null && method.getAnnotation(Type.class).type().equals("org.hibernatespatial.GeometryUserType"))
			{
					return SpatialReflections.formatGetSetMethodNameToPropertyName(method.getName());
			}
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static Integer seekSRIDInClass(Class clazz)
	{
		
		for (Field field : SpatialReflections.describeAllFields(clazz)) {
			
			if(field.isAnnotationPresent(SRID.class))
				return new Integer(field.getAnnotation(SRID.class).value());
		}
		
		for (Method method : SpatialReflections.describeAllMethods(clazz)) {
			if(method.isAnnotationPresent(SRID.class))
				return new Integer(method.getAnnotation(SRID.class).value());
		}
		
		//SRID srid = SpatialReflections.getAnnotationInClass(clazz, SRID.class);

		return null;
	}
}
