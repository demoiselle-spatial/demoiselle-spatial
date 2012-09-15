/**
 * 
 */
package sample.demoiselle_spatial_example.sample.contactlist.view;

import javax.inject.Inject;

import sample.demoiselle_spatial_example.sample.contactlist.business.CasaBC;
import sample.demoiselle_spatial_example.sample.contactlist.domain.Casa;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

/**
 * @author fredestrela
 *
 */

@ViewController
@PreviousView("/casa_list.xhtml")
public class CasaEditMB extends AbstractEditPageBean<Casa, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private CasaBC bc;
	
	@Override
	@Transactional
	public String delete() {
		this.bc.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		this.bc.insert(getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		this.bc.update(getBean());
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		setBean(this.bc.load(getId()));
	}
	
}
