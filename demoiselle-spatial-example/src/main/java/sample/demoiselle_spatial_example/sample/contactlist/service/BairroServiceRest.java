package sample.demoiselle_spatial_example.sample.contactlist.service;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import sample.demoiselle_spatial_example.sample.contactlist.domain.Bairro;
import sample.demoiselle_spatial_example.sample.contactlist.persistence.BairroDAO;
import br.gov.frameworkdemoiselle.component.georest.template.DelegateGeoREST;

@Path("bairro")
@Produces("application/json")
public class BairroServiceRest extends DelegateGeoREST<Bairro, Long, BairroDAO>{

	private static final long serialVersionUID = 1L;

}
