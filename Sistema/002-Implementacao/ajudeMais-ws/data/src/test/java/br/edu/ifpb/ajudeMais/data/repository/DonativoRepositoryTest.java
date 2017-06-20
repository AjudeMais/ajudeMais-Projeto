package br.edu.ifpb.ajudeMais.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

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

import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;

/**
 * 
 * <p>
 * DonativoRepositoryTest
 * </p>
 * 
 * <p>
 * Classe utilizada para realização de testes de unidade referentes a
 * {@link DonativoRepository}
 * </p>
 *
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@RunWith(SpringRunner.class)
@DataJpaTest
@DatabaseSetup("/donativo-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/donativo-entries.xml" })
@DirtiesContext
public class DonativoRepositoryTest {

	/**
	 * 
	 */
	@Autowired
	private DonativoRepository donativoRepository;
	
	/**
	 * 
	 */
	@Test
	public void findByNomeTest() {
		List<Donativo> donativos = donativoRepository.findByNome("Briquedos Iron man");
		assertThat(!donativos.isEmpty());
	}
	
	/**
	 * 
	 */
	@Test
	public void findByDoadorIdTest() {
		List<Donativo> donativos = donativoRepository.findByDoadorIdOrderByDataDesc(1l);
		assertThat(!donativos.isEmpty());
	}
	
	/**
	 * 
	 */
	@Test
	public void findByDoadorNomeTest() {
		List<Donativo> donativos = donativoRepository.findByDoadorNome("Ze");
		assertThat(!donativos.isEmpty());
	}
	
	/**
	 * 
	 */
	@Test
	public void findByCategoriaInstituicaoCaridadeTest() {
		InstituicaoCaridade instituicaoCaridade = new InstituicaoCaridade();
		instituicaoCaridade.setId(1l);
		List<Donativo> donativos = donativoRepository.findByCategoriaInstituicaoCaridadeOrderByDataDesc(instituicaoCaridade);
		assertThat(!donativos.isEmpty());
	}
	
	/**
	 * 
	 */
	@Test
	public void findByCategoriaAndCategoriaInstituicaoCaridadeIdTest() {
		Categoria categoria = new Categoria();
		categoria.setId(1l);
		Long count = donativoRepository.countByCategoriaAndCategoriaInstituicaoCaridadeId(categoria, 1l);
		assertThat(count>0);
	}
	
	/**
	 * 
	 */
	@Test
	public void findAllByOrderByDataAscTest() {
		List<Donativo> donativos = donativoRepository.findAllByOrderByDataDesc();
		assertThat(donativos.get(0).getId() == 2l);
	}

}
