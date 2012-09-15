/**
 * 
 */
package sample.demoiselle_spatial_example.sample.contactlist.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.ol4jsf.component.api.FeatureVector;
import org.ol4jsf.util.WKTFeaturesCollection;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import sample.demoiselle_spatial_example.sample.contactlist.business.CasaBC;
import sample.demoiselle_spatial_example.sample.contactlist.domain.Casa;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.spatial.component.feature.util.EnvelopeWrapper;
import br.gov.frameworkdemoiselle.spatial.component.kml.KMLBuilder;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.ShapefileWriter;
import br.gov.frameworkdemoiselle.spatial.component.shapefile.exception.ShapefileWriterException;
import br.gov.frameworkdemoiselle.spatial.query.SpatialQueryArgument;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;

/**
 * @author fredestrela
 *
 */
@ViewController
@NextView("/casa_edit.xhtml")
@PreviousView("/casa_list.xhtml")
public class CasaListMB extends AbstractListPageBean<Casa, Long>{

	private static final long serialVersionUID = 1L;

	@Inject
	private CasaBC bc;
	
	@Inject
	private KMLBuilder builder;
	
	@Inject
	private ShapefileWriter shapefileWriter;
	
	@Override
	protected List<Casa> handleResultList() {
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
	
	/**\
	 * 
	 * TODO Este ponto pode virar um metodo generico.
	 * Reflection no datamodel obtendo apenas os campos {@link Geometry}
	 *
	 * @return
	 * @throws ParseException 
	 */
	public String getResultFeatureList() throws ParseException {		
		
		 //List<Contact> beans =  bc.findAll(new SpatialQueryArgument(900913));
		 System.out.println("Chamou o findAllByExtent");
		 List<Casa> beans =  bc.finAllByExtent(new EnvelopeWrapper("-73.125, -25.3125,133.59375, 40.078125",4326).getEnvelope()); //this.getResultList();
		 WKTFeaturesCollection wktFeatures = new WKTFeaturesCollection();
		 FeatureVector fv;
		 
		//List<Contact> beans = bc.findAll();
		
		 for (Casa client : beans) {
			 fv = new FeatureVector();
			 fv.setGeometry(client.getGeometry().toString());
			 wktFeatures.addFeature(fv);
			 System.out.println(bc.load(client.getId(), new SpatialQueryArgument(900913)));
		}
		
		return wktFeatures.toMap();
	}
	
	
	public StreamedContent getKml() throws IOException
	{		
		boolean selected =false;
		List<Casa> buildings = new ArrayList<Casa>();

		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			selected = getSelection().get(id);

			if (selected) {
				buildings.add(bc.load(id));
				iter.remove();
			}
		}
		return new DefaultStreamedContent(builder.buildKmlAsFile(buildings), "application/vnd.google-earth.kml+xml",
                "export.kml");
	}
	
	public StreamedContent getShapefile() throws IOException, ShapefileWriterException
	{		
		boolean selected =false;
		List<Casa> buildings = new ArrayList<Casa>();

		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			selected = getSelection().get(id);

			if (selected) {
				buildings.add(bc.load(id));
				iter.remove();
			}
		}
		return new DefaultStreamedContent(shapefileWriter.writeBeanShapefileToInputStream(buildings), "application/zip","shapefile.zip");

	}

}
