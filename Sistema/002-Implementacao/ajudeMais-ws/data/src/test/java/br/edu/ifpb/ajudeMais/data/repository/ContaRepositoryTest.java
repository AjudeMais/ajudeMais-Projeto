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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

import br.edu.ifpb.ajudeMais.domain.entity.Conta;

/**
 * 
 * <p>
 * {@link ContaRepositoryTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes de {@link ContaRepositoryTest}
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
@DatabaseSetup("/conta-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/conta-entries.xml" })
@DirtiesContext
public class ContaRepositoryTest {

	/**
	 * 
	 */
	@Autowired
	private ContaRepository contaRepository;

	/**
	 * 
	 * <p>
	 * Verifica método que busca conta por nome de usuário e estado.
	 * </p>
	 */
	@Test
	public void findOneByUsernameAndAtivoTest() {
		Optional<Conta> contaOp = contaRepository.findOneByUsernameAndAtivo("zefao", true);
		assertTrue(contaOp.isPresent());
	}

	/**
	 * 
	 * <p>
	 * testa método que busca conta por nome de usuário e estado falso.
	 * </p>
	 */
	@Test
	public void findOneByUsernameAndAtivoFalseTest() {
		Optional<Conta> contaOp = contaRepository.findOneByUsernameAndAtivo("zefao", false);
		assertFalse(contaOp.isPresent());
	}

	/**
	 * 
	 * <p>
	 * Testa busca de conta por nome de usuário passando nome de usuário válido
	 * </p>
	 */
	@Test
	public void findOneByUsernameTest() {
		Optional<Conta> contaOp = contaRepository.findOneByUsername("zefao");
		assertTrue(contaOp.isPresent());
	}

	/**
	 * 
	 * <p>
	 * Testa busca de conta por nome de usuário passando nome de usuário que não
	 * existe.
	 * </p>
	 */
	@Test
	public void findOneByUsernameNotPresentTest() {
		Optional<Conta> contaOp = contaRepository.findOneByUsername("zefaoo");
		assertFalse(contaOp.isPresent());
	}

	/**
	 * 
	 * <p>
	 * Testa busca de conta por email de usuário passando email válido
	 * </p>
	 */
	@Test
	public void findOneByEmailTest() {
		Optional<Conta> contaOp = contaRepository.findOneByEmail("zefao@mail.com");
		assertTrue(contaOp.isPresent());
	}

}
