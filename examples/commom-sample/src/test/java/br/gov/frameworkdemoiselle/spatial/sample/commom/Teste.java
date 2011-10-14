package br.gov.frameworkdemoiselle.spatial.sample.commom;

import org.geotools.geometry.jts.JTS;

import com.vividsolutions.jts.geom.Envelope;

import br.gov.frameworkdemoiselle.spatial.component.feature.util.EnvelopeWrapper;

public class Teste {

	public static void main(String[] args) {
		
		Envelope envelope = new EnvelopeWrapper("-74.3923002715439,-11.462958782424522,-65.97160155697127,-6.809676427950414").getEnvelope();
		
		System.out.println(JTS.toGeometry(envelope));
	}
}
