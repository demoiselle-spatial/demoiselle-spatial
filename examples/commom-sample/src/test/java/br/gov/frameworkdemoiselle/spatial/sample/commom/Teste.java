package br.gov.frameworkdemoiselle.spatial.sample.commom;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

public class Teste {

	public static void main(String[] args) throws IOException {
		
		URL[] urls = ClasspathUrlFinder.findResourceBases("META-INF/persistence.xml");
		AnnotationDB db = new AnnotationDB();
		db.scanArchives(urls);
		Set<String> entityClasses = db.getAnnotationIndex().get(javax.persistence.Entity.class.getName());
		
		for (String string : entityClasses) {
			System.out.println(entityClasses);
		}
	}
}
