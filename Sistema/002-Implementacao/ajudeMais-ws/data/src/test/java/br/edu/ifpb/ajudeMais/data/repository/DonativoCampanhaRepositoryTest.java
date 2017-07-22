
/**
 * 
 * <p>
 * <b> DonativoCampanhaRepositoryTest.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.data.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

import br.edu.ifpb.ajudeMais.domain.entity.DonativoCampanha;

/**
 * 
 * <p>
 * <b>{@link DonativoCampanhaRepositoryTest} </b>
 * </p>
 *
 * <p>
 * Execita testes para donativos relacionados a uma campanha
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@RunWith(SpringRunner.class)
@DataJpaTest
@DatabaseSetup("/donativo-campanha-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/donativo-campanha-entries.xml" })
@DirtiesContext
public class DonativoCampanhaRepositoryTest {

	
	/**
	 * 
	 */
	@Autowired
	private DonativoCampanhaRepository donativoRepository;
	
	/**
	 * Testa se com base no id da campanha é retorna a lista de donativos.
	 */
	@Test
	public void findDonativoByCampanhaIdTest() {
		List<DonativoCampanha> donativos = donativoRepository.findByCampanhaId(2l);
		assertThat(!donativos.isEmpty());
	}
	

	/**
	 * Testa se retornará null caso o id da campanha não existe.
	 */
	@Test
	public void findDonativoByCampanhaIdWithInvalidIdTest() {
		List<DonativoCampanha> donativos = donativoRepository.findByCampanhaId(4l);
		assertThat(donativos.isEmpty());
	}
	
	
	
	/**
	 * Testa se com base no id da campanha é retorna a lista de donativos com estado ativo depois de aceito.
	 */
	@Test
	public void filterDonativoByEstadoAfterAceitoTest() {
		List<DonativoCampanha> donativos = donativoRepository.filterDonativoByEstadoAfterAceito(2l);
		assertThat(donativos.isEmpty());
	}
	
	/**
	 * Testa que a quantidade de donativos, doados para camapanha e da categoria com os ids passados e com estado após aceito é mesmo informado 
	 */
	@Test
	public void filterCountByEstadoAndCategoriaAfterAceitoTest() {
		Long qtdDonativos = donativoRepository.filterCountByEstadoAndCategoriaAfterAceito(2l, 2l);
		assertEquals(new Long(1), qtdDonativos);
	}
	
	/**
	 * Testa que a consulta retorna um objeto do tipo Campanha donativo.
	 */
	@Test
	public void findOneByDonativoIdTest() {
		DonativoCampanha donativoCampanha = donativoRepository.findOneByDonativoId(5l);
		assertThat(donativoCampanha != null);
	}
	
	/**
	 * Testa que a consulta retorna um objeto do tipo Campanha donativo com donativo com nome igual ao verificado.
	 */
	@Test
	public void findOneByDonativoIdTestEqualNameDonativo() {
		DonativoCampanha donativoCampanha = donativoRepository.findOneByDonativoId(5l);
		assertEquals(donativoCampanha.getDonativo().getNome(),"Camisa the flash");
	}
	
	
	/**
	 * Testa que a consulta retorna um objeto do tipo Campanha donativo com valor null.
	 */
	@Test
	public void findOneByDonativoIdTestwithInvalidId() {
		DonativoCampanha donativoCampanha = donativoRepository.findOneByDonativoId(10l);
		assertEquals(donativoCampanha,null);
	}
	
	
}
