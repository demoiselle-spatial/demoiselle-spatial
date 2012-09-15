/**
 * 
 */
package sample.demoiselle_spatial_example.sample.commom;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sample.demoiselle_spatial_example.sample.contactlist.business.ContactBC;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import br.gov.frameworkdemoiselle.transaction.Transactional;


/**
 * @author fredestrela
 *
 */
@Transactional
@RunWith(DemoiselleRunner.class)
public class SpatialQueryTest {

	@Inject
	ContactBC bc;
	
	@Before
	public void before(){
		System.out.println("Começou o teste....");
	}
	
	@Test
	public void testFindAllByExtent(){
		bc.findAll();
		System.out.println("Teste funcionou");
	}

	@After
	public void after(){
		System.out.println("Terminou o teste..");
	}
	
}
