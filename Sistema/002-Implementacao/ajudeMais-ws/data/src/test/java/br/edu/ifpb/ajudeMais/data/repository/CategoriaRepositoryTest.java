/**
 * Ajude Mais - Módulo Web Service
 * 
 * Sistema para potencializar o processo de doação.
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.data.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

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

import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;

/**
 * 
 * <p>{@link CategoriaRepository} </p>
 * 
 * <p>
 * Classe utilizada para 
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
@DatabaseSetup("/categoria-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/categoria-entries.xml" })
@DirtiesContext
public class CategoriaRepositoryTest {
	
	/**
	 * 
	 */
	private InstituicaoCaridade instituicao;
	
	/**
	 * 
	 */
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private InstituicaoCaridadeRepository instituicaoRepository;
	
	/**
	 * 
	 * <p>
	 * </p>
	 */
	@Before
	public void setUp() {
		instituicao = instituicaoRepository.findOne(1l);
		
	}
	
	
	/**
	 * 
	 * <p>
	 * </p>
	 */
	@Test
	public void findByInstituicaoCaridadeTest() {
		
		List<Categoria> instituicaoCaridades = categoriaRepository.findByInstituicaoCaridade(instituicao);
		assertTrue(instituicaoCaridades.size() > 0);
	}
	
	/**
	 * 
	 * <p>
	 * </p>
	 */
	@Test
	public void findByInstituicaoCaridadeNullTest() {
		List<Categoria> instituicaoCaridades = categoriaRepository.findByInstituicaoCaridade(null);
		assertFalse(instituicaoCaridades.size() > 0);
	}
}
