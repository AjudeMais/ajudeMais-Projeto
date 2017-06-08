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

import static org.junit.Assert.assertNotNull;

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

import br.edu.ifpb.ajudeMais.domain.entity.Imagem;

/**
 * 
 * <p>
 * {@link ImagemRepositoryTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes de unidade referentes ao repositório
 * {@link ImagemRepository}
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
@DatabaseSetup("/imagem-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/imagem-entries.xml" })
@DirtiesContext
public class ImagemRepositoryTest {

	/**
	 * 
	 */
	@Autowired
	private ImagemRepository imagemRepository;

	private Imagem imagem;	

	/**
	 * 
	 * <p>
	 * Configuração executada antes da execução de cada teste.
	 * </p>
	 */
	@Before
	public void setUp() {

		imagem = new Imagem();
		imagem.setId(1l);

	}

	/**
	 * 
	 * <p>
	 * Exercita método de busca de imagem por ID
	 * </p>
	 */
	@Test
	public void findByIdTest() {
		
		Imagem imagem = imagemRepository.findOne(1l);

		assertNotNull(imagem);

	}

	

}
