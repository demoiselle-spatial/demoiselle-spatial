package br.gov.frameworkdemoiselle.spatial.query;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE,ElementType.METHOD,ElementType.FIELD })
@Retention(RUNTIME)
public @interface SRID {

	String value();
}
