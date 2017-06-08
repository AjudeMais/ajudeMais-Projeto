/**
 * <p>
 * Ajude Mais - Módulo Web Service
 * </p>
 * 
 * <p>
 * Sistema para potencializar o processo de doação.
 * </p>
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
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
 * <p>
 * {@link InstituicaoCaridadeRepositoryTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes relacionados a
 * {@link InstituicaoCaridadeRepository}
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
@DatabaseSetup("/instituicao-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/instituicao-entries.xml" })
@DirtiesContext
public class InstituicaoCaridadeRepositoryTest {

	/**
	 * 
	 */
	@Autowired
	private InstituicaoCaridadeRepository instituicaoRepository;

	/**
	 * Testa busca de instituição por Id existente
	 * 
	 */
	@Test
	public void testFindByIdOk() {
		InstituicaoCaridade instituicao = instituicaoRepository.findOne(1l);
		assertNotNull(instituicao);

	}

	/**
	 * Testa busca de instituição por documento existente
	 */
	@Test
	public void testFindInstituicaoByDocumentoOk() {
		Optional<InstituicaoCaridade> instituicaoOptional = instituicaoRepository.findOneByDocumento("19016014350");

		assertNotNull(instituicaoOptional.get());

	}

	/**
	 * Testa busca de instituição por documento não existente
	 */
	public void testFindInstituicaoByDocumentoNotfound() {
		Optional<InstituicaoCaridade> instituicaoOptional = instituicaoRepository.findOneByDocumento("190160143508473");
		assertThat(!instituicaoOptional.isPresent());

	}

	/**
	 * Testa busca de instituição por localidade e estado existente.
	 */
	@Test
	public void testFiltersInstituicaoCaridadeCloseOk() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.filtersInstituicaoCaridadeClose("Ouro Velho",
				"PB");

		assertThat(!instituicoes.isEmpty());
	}

	/**
	 * Testa busca de instituição por apenas localidade
	 */
	@Test
	public void testFiltersInstituicaoCaridadeCloseOnlyLocalidade() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.filtersInstituicaoCaridadeClose("Ouro Velho",
				null);

		assertThat(!instituicoes.isEmpty());
	}

	/**
	 * Testa busca de instituição por apenas estado
	 */
	@Test
	public void testFiltersInstituicaoCaridadeCloseOnlyUf() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.filtersInstituicaoCaridadeClose(null, "PB");

		assertThat(!instituicoes.isEmpty());
	}

	/**
	 * Testa busca de instituição por locaalização não existente
	 */
	@Test
	public void testFiltersInstituicaoCaridadeCloseNotFound() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.filtersInstituicaoCaridadeClose("Monteiro",
				"PB");

		assertThat(instituicoes.isEmpty());
	}

	/**
	 * Testa busca de instituição passando parametros nulos
	 */
	@Test
	public void testFiltersInstituicaoCaridadeCloseNullParams() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.filtersInstituicaoCaridadeClose(null, null);

		assertThat(instituicoes.isEmpty());
	}
	
	/**
	 * 
	 * <p>
	 * Teste busca insituição pelo status.
	 * </p>
	 */
	@Test
	public void findByContaAtivoTest() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.findByContaAtivo(true);

		assertThat(!instituicoes.isEmpty());
	}
	
	/**
	 * 
	 * <p>
	 * Teste tenta buscar instituição com status false.
	 * </p>
	 */
	@Test
	public void findByContaAtivoFalseTest() {
		List<InstituicaoCaridade> instituicoes = instituicaoRepository.findByContaAtivo(false);

		assertThat(instituicoes.isEmpty());
	}

}
