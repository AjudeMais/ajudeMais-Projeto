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
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.data.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
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
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;

/**
 * 
 * <p>
 * {@link MensageiroAssociadoRepositoryTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes de unidade referentes ao repositório
 * {@link MensageiroAssociadoRepository}
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
@DatabaseSetup("/mensageiro-associado-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/mensageiro-associado-entries.xml" })
@DirtiesContext
public class MensageiroAssociadoRepositoryTest {

	/**
	 * 
	 */
	@Autowired
	private MensageiroAssociadoRepository mensageiroAssociadoRepository;

	private Conta conta;

	/**
	 * 
	 * <p>
	 * Configuração executada antes da execução de cada teste.
	 * </p>
	 */
	@Before
	public void setUp() {

		conta = new Conta();
		conta.setId(1l);

	}

	/**
	 * 
	 * <p>
	 * Exercita método de busca de associação por instituição filtrando por
	 * conta.
	 * </p>
	 */
	@Test
	public void findByInstituicaoCaridadeContaTest() {

		List<MensageiroAssociado> mensageiros = mensageiroAssociadoRepository
				.findByInstituicaoCaridadeConta(this.conta);

		assertFalse("", mensageiros.isEmpty());

	}

	/**
	 * 
	 * <p>
	 * Exercita método de busca de mensgeiro associado por ID do mensgeiro.
	 * </p>
	 */
	@Test
	public void findByMensageiroIdTest() {
		Optional<MensageiroAssociado> mensageiroAssOp = mensageiroAssociadoRepository.findByMensageiroId(1l);

		assertTrue(mensageiroAssOp.isPresent());
	}

	/**
	 * 
	 * <p>
	 * Exercita método de busca de mensgeiro associado por ID do mensgeiro.
	 * </p>
	 */
	@Test
	public void findByMensageiroIdNonexistTest() {
		Optional<MensageiroAssociado> mensageiroAssOp = mensageiroAssociadoRepository.findByMensageiroId(100l);

		assertFalse(mensageiroAssOp.isPresent());
	}

}
