package br.gov.frameworkdemoiselle;

import java.lang.reflect.Field;
import java.util.List;

import jodd.bean.BeanUtil;

import com.vividsolutions.jts.io.ParseException;

import br.gov.frameworkdemoiselle.spatial.query.SRID;
import br.gov.frameworkdemoiselle.spatial.template.SpatialEntityReflections;
import br.gov.frameworkdemoiselle.spatial.template.SpatialReflections;


public class Main {

	public static void main(String[] args) throws ParseException {
		
		Entity entity = new Entity();
		
		System.out.println(BeanUtil.getDeclaredProperty(entity, "ponto").getClass());
		
		Class clazz = Entity.class;
		
		List<Field> fields = SpatialReflections.describeAllFields(clazz);
		
		for (Field field : fields) {
			System.out.println(field);
		}
		
		System.out.println(SpatialEntityReflections.seekEntityTableName(clazz)); 
		
		System.out.println(SpatialReflections.getAnnotationInClass(clazz, SRID.class));
		
//		for (Field field : SpatialReflections.seekAllFieldAnnontation(clazz, Id.class)) {
//			System.out.println(field);
//		} 
//		
//		System.out.println();
//		
//		for (Method method : SpatialReflections.describeAllMethods(clazz)) {
//			System.out.println(method);
//		}  
		
		//Annotation[] annotations = clazz.is
		
//		for (Annotation annotation2 : annotations) {
//			
//			System.out.println(annotation2);
//		}
	}
}
