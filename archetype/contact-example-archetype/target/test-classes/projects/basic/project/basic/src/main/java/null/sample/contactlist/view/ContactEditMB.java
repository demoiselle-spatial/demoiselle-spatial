package ${package}.sample.contactlist.view;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import ${package}.geocode.ReverseGeocoding;
import ${package}.geocode.model.GeocodingResponse;
import ${package}.sample.contactlist.business.ContactBC;
import ${package}.sample.contactlist.domain.Contact;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

import com.vividsolutions.jts.geom.Point;


@ViewController
@PreviousView("/contact_list.xhtml")
public class ContactEditMB extends AbstractEditPageBean<Contact, Long> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ReverseGeocoding reverseGeocoding;
	
	@Inject
	private ContactBC bc;

	public String geocoding()
	{
		if(this.getBean().getPoint() != null)
		{
			Point point = (Point)this.getBean().getPoint();
			 GeocodingResponse response = reverseGeocoding.setLocation(point).search();			 
			 if(response!= null && response.getResults()!= null && !response.getResults().isEmpty())
				 return response.getResults().get(0).getAddress();

		}
		
		return "Nenhum endereco encontrado";
	}
	
	
	@Override
	@Transactional
	public String delete() {
		this.bc.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		
		getBean().setAddress(this.geocoding());
		
		this.bc.insert(getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		
		getBean().setAddress(this.geocoding());
		
		this.bc.update(getBean());
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.bc.load(getId()));
	}


	
	
}
