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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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

import br.edu.ifpb.ajudeMais.domain.entity.Doador;

/**
 * 
 * <p>
 * {@link DoadorRepositoryTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes relacionados a {@link DoadorRepository}
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
@DatabaseSetup("/doador-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/doador-entries.xml" })
@DirtiesContext
public class DoadorRepositoryTest {

	/**
	 * 
	 */
	@Autowired
	private DoadorRepository doadorRepository;

	/**
	 * 
	 * <p>
	 * testa busca de doador por id existente
	 * </p>
	 */
	@Test
	public void findByIdTest() {

		Doador doador = doadorRepository.findOne(1l);
		assertNotNull(doador);

	}

	/**
	 * 
	 * <p>
	 * testa busca de doador por nome existente.
	 * </p>
	 */
	@Test
	public void findByNomeTest() {

		List<Doador> doadores = doadorRepository.findByNome("Ze");
		assertTrue("Deveria ter conteúdo na lista", doadores.size() > 0);

	}

	/**
	 * 
	 * <p>
	 * testa busca de doador por nome não existente.
	 * </p>
	 */
	@Test
	public void findByNomeNotFoundTest() {

		List<Doador> doadores = doadorRepository.findByNome("Zefinha");
		assertThat(true, is(doadores.size() == 0));
	}
	
	/**
	 * 
	 * <p>
	 * testa busca de doador filtrando por username de conta.
	 * </p>
	 */
	@Test
	public void findOneByContaUsernameTest() {

		Doador doador = doadorRepository.findOneByContaUsername("zefao");
		assertNotNull(doador);
	}
	
	
	/**
	 * 
	 * <p>
	 * testa busca de doador filtrando por username de conta.
	 * </p>
	 */
	@Test
	public void findOneByContaUsernameNotFoundTest() {

		Doador doador = doadorRepository.findOneByContaUsername("");
		assertNull(doador);
	}

}