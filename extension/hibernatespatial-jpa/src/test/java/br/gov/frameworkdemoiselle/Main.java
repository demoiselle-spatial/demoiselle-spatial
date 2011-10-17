package br.gov.frameworkdemoiselle;

import br.gov.frameworkdemoiselle.spatial.query.SRID;
import br.gov.frameworkdemoiselle.spatial.template.SpatialEntityReflections;
import br.gov.frameworkdemoiselle.spatial.template.SpatialReflections;


public class Main {

	public static void main(String[] args) {
		
		Class clazz = Entity.class;
		
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
