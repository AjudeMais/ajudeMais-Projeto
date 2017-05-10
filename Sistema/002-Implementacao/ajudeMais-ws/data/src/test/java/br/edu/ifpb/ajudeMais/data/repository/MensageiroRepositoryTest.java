package br.edu.ifpb.ajudeMais.data.repository;

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

/**
 * 
 * <p>{@link MensageiroRepositoryTest} </p>
 * 
 * <p>
 * Classe utilizada para testes de {@link MensageiroRepositoryTest}
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
@DatabaseSetup("/mensageiro-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/mensageiro-entries.xml" })
@DirtiesContext
public class MensageiroRepositoryTest {
	
	/**
	 * 
	 */
	@Autowired
	private MensageiroRepository mensageiroRepository;
	
	/**
	 * 
	 * <p>
	 * </p>
	 */
	@Test
	public void filtersMensageiroCloserTest() {
		List<Object[]> mensageiros = mensageiroRepository.filtersMensageiroCloser("Rua ai", "centro", "Ouro velho", "PB");
		assertTrue(mensageiros.size() > 0);
	}
	
	/**
	 * 
	 * <p>
	 * </p>
	 */
	@Test
	public void filtersMensageiroCloserValidOnlyAddressTest() {
		List<Object[]> mensageiros = mensageiroRepository.filtersMensageiroCloser("Rua ai", "", "", "");
		assertFalse(mensageiros.size() > 0);
	}
	
	/**
	 * 
	 * <p>
	 * </p>
	 */
	@Test
	public void filtersMensageiroCloserNullParamsTest() {
		List<Object[]> mensageiros = mensageiroRepository.filtersMensageiroCloser(null, null, null, null);
		assertFalse(mensageiros.size() > 0);
	}
}
