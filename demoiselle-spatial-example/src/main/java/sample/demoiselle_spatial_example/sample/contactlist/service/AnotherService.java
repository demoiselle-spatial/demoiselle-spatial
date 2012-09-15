package sample.demoiselle_spatial_example.sample.contactlist.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("AnotherService")
@Produces("application/json")
public class AnotherService {

	@Path("/entity1/list")
	@GET
	public String data()
	{
		return "DATA";
	}
}
