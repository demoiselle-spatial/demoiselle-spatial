#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.sample.contactlist.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.geotools.geometry.jts.JTS;
import org.ol4jsf.util.WKTFeaturesCollection;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import ${package}.component.feature.util.EnvelopeWrapper;
import ${package}.component.kml.KMLBuilder;
import ${package}.component.shapefile.ShapefileReader;
import ${package}.component.shapefile.ShapefileWriter;
import ${package}.component.shapefile.exception.ShapefileWriterException;
import ${package}.geocode.Geocoding;
import ${package}.geocode.ReverseGeocoding;
import ${package}.query.SpatialQueryArgument;
import ${package}.sample.contactlist.business.ContactBC;
import ${package}.sample.contactlist.domain.Contact;
import ${package}.template.DemoiselleSpatialEnvelope;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

@ViewController
@NextView("/contact_edit.xhtml")
@PreviousView("/contact_list.xhtml")
public class ContactListMB extends AbstractListPageBean<Contact, Long> {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private ContactBC bc;
	
	@Inject
	private KMLBuilder builder;
	
	@Inject
	private ShapefileWriter shapefileWriter;
	
	@Inject
	private ShapefileReader shapefileReader;
	
	@Override
	protected List<Contact> handleResultList() {
		
		return bc.findAll(new SpatialQueryArgument(900913));
	}
	
	@Transactional
	public String deleteSelection() {
	
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);

			if (delete) {
				bc.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	
	}
	
	/**${symbol_escape}
	 * 
	 * TODO Este ponto pode virar um metodo generico.
	 * Reflection no datamodel obtendo apenas os campos {@link Geometry}
	 *
	 * @return
	 * @throws ParseException 
	 */
	public String getResultFeatureList() throws ParseException {		
		
		List<Contact> beans =  bc.findAll(new SpatialQueryArgument(900913));
		 //List<Contact> beans =  bc.finAllByExtent(new EnvelopeWrapper("-73.125, -25.3125,133.59375, 40.078125",4326).getEnvelope()); //this.getResultList();
		 WKTFeaturesCollection<Geometry> wktFeatures = new WKTFeaturesCollection<Geometry>();
		 
		//List<Contact> beans = bc.findAll();
		
		 for (Contact client : beans) {			 
			 wktFeatures.addFeature(client.getPoint());
			 System.out.println(bc.load(client.getId(), new SpatialQueryArgument(900913)));
		}
		
		return wktFeatures.toMap();
	}
	
	public StreamedContent getKml() throws IOException
	{		
		boolean selected =false;
		List<Contact> contacts = new ArrayList<Contact>();

		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			selected = getSelection().get(id);

			if (selected) {
				contacts.add(bc.load(id));
				iter.remove();
			}
		}
		return new DefaultStreamedContent(builder.buildKmlAsFile(contacts), "application/vnd.google-earth.kml+xml",
                "export.kml");
	}
	
	public StreamedContent getShapefile() throws IOException, ShapefileWriterException
	{		
		boolean selected =false;
		List<Contact> contacts = new ArrayList<Contact>();

		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			selected = getSelection().get(id);

			if (selected) {
				contacts.add(bc.load(id));
				iter.remove();
			}
		}
		return new DefaultStreamedContent(shapefileWriter.writeBeanShapefileToInputStream(contacts), "application/zip","shapefile.zip");

	}
	
	public static void main(String[] args) {
		
		Envelope envelope = new EnvelopeWrapper("-73.125, -25.3125,133.59375, 40.078125").getEnvelope();
		
		System.out.println("MAX_X" + envelope.getMaxX());
		System.out.println("MAX_Y" + envelope.getMaxY());
		System.out.println("MIN_X" + envelope.getMinX());
		System.out.println("MIN_Y" + envelope.getMinY());
		
		System.out.println(JTS.toGeometry(envelope));
		
	}

}
