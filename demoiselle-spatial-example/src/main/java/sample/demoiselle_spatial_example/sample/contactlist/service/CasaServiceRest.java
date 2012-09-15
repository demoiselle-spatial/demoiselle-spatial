package sample.demoiselle_spatial_example.sample.contactlist.service;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import sample.demoiselle_spatial_example.sample.contactlist.domain.Casa;
import sample.demoiselle_spatial_example.sample.contactlist.persistence.CasaDAO;
import br.gov.frameworkdemoiselle.component.georest.template.DelegateGeoREST;

@Path("casa")
@Produces("application/json")
public class CasaServiceRest extends DelegateGeoREST<Casa, Long, CasaDAO>{

	private static final long serialVersionUID = 1L;

}
