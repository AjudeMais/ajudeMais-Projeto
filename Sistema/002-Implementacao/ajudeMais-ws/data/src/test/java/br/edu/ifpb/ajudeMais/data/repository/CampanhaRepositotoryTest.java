package br.edu.ifpb.ajudeMais.data.repository;

import static org.assertj.core.api.Assertions.assertThat;
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
	 * <p>
	 * Teste para verificação de filtro por localização de instituição.
	 * </p>
	 */
	public void filterByInstituicaoLocalTest() {
		List<Campanha> campanhas = campanhaRepository.filterByInstituicaoLocal("Ouro Velho", "PB");

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

}
