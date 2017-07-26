package br.edu.ifpb.ajudeMais.data.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

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

import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;

/**
 * 
 * <p>
 * {@link CampanhaRepository}
 * </p>
 * 
 * <p>
 * Classe utilizada para execução de testes referentes a
 * {@link CampanhaRepository}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@RunWith(SpringRunner.class)
@DataJpaTest
@DatabaseSetup("/campanha-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/campanha-entries.xml" })
@DirtiesContext
public class CampanhaRepositotoryTest {

	@Autowired
	private CampanhaRepository campanhaRepository;

	/**
	 * 
	 * <p>
	 * Teste para verificação de repsoitory de campanha.
	 * </p>
	 */
	@Test
	public void saveTest() {

		Campanha campanha = campanhaRepository.findOne(1l);
		assertNotNull(campanha);

	}

	/**
	 * 
	 */
	@Test
	public void findByInstituicaoTest() {
		InstituicaoCaridade instituicaoCaridade = new InstituicaoCaridade();
		instituicaoCaridade.setId(1l);
		List<Campanha> campanhas = campanhaRepository.findByInstituicaoCaridadeOrderByDataCriacaoDesc(instituicaoCaridade);

		assertThat(!campanhas.isEmpty());
	}

	/**
	 * 
	 * <p>
	 * Teste para verificação de filtro por localização de instituição.
	 * </p>
	 */
	@Test
	public void filterByInstituicaoLocalTest() {
		List<Campanha> campanhas = campanhaRepository.filterByInstituicaoLocalOrderByDataCriacaoDesc("Ouro Velho", "PB");

		assertThat(!campanhas.isEmpty());
	}

	/**
	 * 
	 * <p>
	 * Teste para bsuca de campanhas por status. Para stattus = true;
	 * </p>
	 */
	@Test
	public void findByStatusTrueTest() {
		List<Campanha> campanhas = campanhaRepository.findByStatus(true);
		assertThat(!campanhas.isEmpty());
	}

	/**
	 * 
	 * <p>
	 * Teste para bsuca de campanhas por status. Para stattus = false;
	 * </p>
	 */
	@Test
	public void findByStatusFalseTest() {
		List<Campanha> campanhas = campanhaRepository.findByStatus(false);
		assertThat(campanhas.isEmpty());
	}

	
	/**
	 * 
	 * <p>
	 * Teste se a quantidade de campanhas da instituição com uma meta com a categoria com id passado é igual a 1;
	 * </p>
	 */
	@Test
	public void filterCountCampanhasMetaCategoriaIdTest() {
		Long campanhas = campanhaRepository.filterCountCampanhasMetaCategoriaId(1l, 1l);
		assertEquals(campanhas.longValue(), 1);
	}
	
	/**
	 * 
	 * <p>
	 * Teste se a quantidade de campanhas da instituição com uma meta com a categoria com id passado é igual não é igual a 0;
	 * </p>
	 */
	@Test
	public void filterCountCampanhasMetaCategoriaIdInvalidValueTest() {
		Long campanhas = campanhaRepository.filterCountCampanhasMetaCategoriaId(2l, 1l);
		assertNotEquals(campanhas.longValue(), 0);
	}
	
	/**
	 * 
	 * <p>
	 * Teste se a quantidade de campanhas da instituição com id inválido é igual a 0;
	 * </p>
	 */
	@Test
	public void filterCountCampanhasMetaCategoriaIdInvalidIdInvalidTest() {
		Long campanhas = campanhaRepository.filterCountCampanhasMetaCategoriaId(1l, 4l);
		assertEquals(campanhas.longValue(), 0);
	}
	
	@Test
	public void countByInstituicaoCaridadeIdAndStatusTest() {
		Long quantidade = campanhaRepository.countByInstituicaoCaridadeIdAndStatus(1l, false);
		assertEquals(quantidade.longValue(), 0);
	}

	@Test
	public void findByInstituicaoCaridadeIdAndStatusTest() {
		List<Campanha> campanhas = campanhaRepository.findByInstituicaoCaridadeIdAndStatusOrderByDataFimAsc(1l, false);
		assertThat(campanhas.isEmpty());
	}
}
