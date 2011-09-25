package br.gov.frameworkdemoiselle.spatial.sample.commom.view;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.spatial.sample.commom.business.ClientBC;
import br.gov.frameworkdemoiselle.spatial.sample.commom.domain.Client;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;


@ViewController
@PreviousView("/client_list.xhtml")
public class ClientEditMB extends AbstractEditPageBean<Client, Long> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClientBC bc;

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
