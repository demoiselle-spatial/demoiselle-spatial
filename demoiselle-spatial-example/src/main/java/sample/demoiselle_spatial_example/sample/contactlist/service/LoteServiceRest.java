package sample.demoiselle_spatial_example.sample.contactlist.service;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import sample.demoiselle_spatial_example.sample.contactlist.domain.Lote;
import sample.demoiselle_spatial_example.sample.contactlist.persistence.LoteDAO;
import br.gov.frameworkdemoiselle.component.georest.template.DelegateGeoREST;

@Path("lote")
@Produces("application/json")
public class LoteServiceRest extends DelegateGeoREST<Lote, Long, LoteDAO>{

	private static final long serialVersionUID = 1L;

}
