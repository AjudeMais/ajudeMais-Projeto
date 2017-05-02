package br.edu.ifpb.ajudeMais.data.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

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

import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;


/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@RunWith(SpringRunner.class)
@DataJpaTest
@DatabaseSetup("/instituicao-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/instituicao-entries.xml" })
@DirtiesContext
public class InstituicaoCaridadeRepositoryTest {
	
	@Autowired
	private InstituicaoCaridadeRepository instituicaoRepository;
	
	/**
	 * 
	 */
	@Test
	public void testFindByIdOk() {
		InstituicaoCaridade instituicao = instituicaoRepository.findOne(1l);
		assertNotNull(instituicao);
		
	}
	
	/**
	 * 
	 */
	@Test
	public void testFindInstituicaoByDocumentoOk() {
		Optional<InstituicaoCaridade> instituicaoOptional = instituicaoRepository.findOneByDocumento("19016014350");
		
		assertNotNull(instituicaoOptional.get());
		
	}
	
	/**
	 * 
	 */
	public void testFindInstituicaoByDocumentoNotfound() {
		Optional<InstituicaoCaridade> instituicaoOptional = instituicaoRepository.findOneByDocumento("190160143508473");
		assertThat(!instituicaoOptional.isPresent());

	}
	
	/**
	 * 
	 */
	@Test
	public void testFiltersInstituicaoCaridadeCloseOk() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.filtersInstituicaoCaridadeClose("Ouro Velho", "PB");
		
		assertThat(!instituicoes.isEmpty());
	}
	
	/**
	 * 
	 */
	@Test
	public void testFiltersInstituicaoCaridadeCloseOnlyLocalidade() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.filtersInstituicaoCaridadeClose("Ouro Velho", null);
		
		assertThat(!instituicoes.isEmpty());
	}
	
	/**
	 * 
	 */
	@Test
	public void testFiltersInstituicaoCaridadeCloseOnlyUf() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.filtersInstituicaoCaridadeClose(null, "PB");
		
		assertThat(!instituicoes.isEmpty());
	}
	
	/**
	 * 
	 */
	@Test
	public void testFiltersInstituicaoCaridadeCloseNotFound() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.filtersInstituicaoCaridadeClose("Monteiro", "PB");
		
		assertThat(instituicoes.isEmpty());
	}
	
	/**
	 * 
	 */
	@Test
	public void testFiltersInstituicaoCaridadeCloseNullParams() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.filtersInstituicaoCaridadeClose(null, null);
		
		assertThat(instituicoes.isEmpty());
	}

}

