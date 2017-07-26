package br.edu.ifpb.ajudeMais.data.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;

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
 * @author <a href="https://franckaj.github.io">Franck Aragão</a> and
 * 			<a href="https://github.com/amslv> Ana Silva </a>
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
		List<Donativo> donativos = donativoRepository
				.findByCategoriaInstituicaoCaridadeOrderByDataDesc(instituicaoCaridade);
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
		assertThat(count > 0);
	}

	/**
	 * 
	 */
	@Test
	public void findAllByOrderByDataAscTest() {
		List<Donativo> donativos = donativoRepository.findAllByOrderByDataDesc();
		assertThat(donativos.get(0).getId() == 2l);
	}

	/**
	 * Testa se existe um donativo vinculada a instituição com id que possui o
	 * estado NAOACEITO
	 */
	@Test
	public void filterDonativoByEstadoAndInstituicaoTest() {
		List<Donativo> donativos = donativoRepository.filterDonativoByEstadoAndInstituicao(1l, Estado.NAO_ACEITO);
		assertEquals(1, donativos.size());
	}

	/**
	 * Testa se lista estará null se não existi donativos com estado aceito
	 * vinculados a instituição com id 1.
	 */
	@Test
	public void filterDonativoByEstadoAndInstituicaoNullTest() {
		List<Donativo> donativos = donativoRepository.filterDonativoByEstadoAndInstituicao(1l, Estado.ACEITO);
		assertThat(donativos.isEmpty());
	}

	/**
	 * 
	 * <p>
	 * Testa método que busca donativos de um mensageiro. Deveria retornar true.
	 * </p>
	 */
	@Test
	public void findByMensageiroOrderByDataDescTest() {
		Mensageiro mensageiro = new Mensageiro();
		mensageiro.setId(1l);
		List<Donativo> donativos = donativoRepository.findByMensageiroOrderByDataDesc(mensageiro);
		assertThat(!donativos.isEmpty());

	}

	/**
	 * 
	 * <p>
	 * Testa método que busca donativos de um mensageiro. Deveria retornar
	 * false, para mensageiro são existente.
	 * </p>
	 */
	@Test
	public void findByMensageiroOrderByDataDescNullTest() {
		List<Donativo> donativos = donativoRepository.findByMensageiroOrderByDataDesc(null);
		assertThat(donativos.isEmpty());

	}

	/**
	 * 
	 * <p>
	 * Testa método que busca donativos de um mensageiro. Deveria retornar
	 * false, para mensageiro com ID inexistente.
	 * </p>
	 */
	@Test
	public void findByMensageiroOrderByDataDescNotExistTest() {
		Mensageiro mensageiro = new Mensageiro();
		mensageiro.setId(100l);
		List<Donativo> donativos = donativoRepository.findByMensageiroOrderByDataDesc(null);
		assertThat(donativos.isEmpty());

	}
	
	/**
	 * Testa método que busca a quantidade de donativos já recolhidos. Deveria retornar
	 * false, caso não exista nenhum donativo recolhido.
	 */
	@Test
	public void filterCountByEstadoRecolhidoTest() {
		Long quantidade = donativoRepository.filterCountByEstadoRecolhido();
		assertThat(quantidade > 0);
	}

	/**
	 * Testa método que busca a quantidade de donativos já recolhidos durante um intervalo de tempo.
	 * Deveria retornar false, caso não exista nenhum donativo recolhido durante aquela faixa de datas.
	 */
	@Test
	public void filterCountByEstadoRecolhidoAndDateBetweenTest() {
		Date dataInicial = convertDate("2017-06-05");
		Date dataFinal = convertDate("2017-06-07");
		Long quantidade = donativoRepository.filterCountByEstadoRecolhidoAndDateBetween(dataInicial, dataFinal);
		assertThat(quantidade > 0);
	}
	
	/**
	 * Testa método que busca a quantidade de donativos com base em seu estado e um intervalo de tempo.
	 * Deveria retornar false, caso não exista nenhum donativo recolhido
	 * durante aquela faixa de datas e/ou no estado pesquisado.
	 */
	@Test
	public void filterCountDonativoByEstadoAndDateBetweenTest() {
		Date dataInicial = convertDate("2017-06-05");
		Date dataFinal = convertDate("2017-06-09");
		Long quantidade = donativoRepository.filterCountDonativoByEstadoAndDateBetween(dataInicial, dataFinal, Estado.DISPONIBILIZADO);
		assertThat(quantidade > 0);
	}
	
	/**
	 * Testa método que busca a quantidade de donativos com base em seu estado e um intervalo de tempo e instituição.
	 * Deveria retornar false, caso não exista nenhum donativo recolhido
	 * durante aquela faixa de datas, no estado pesquisado ou instituição.
	 */
	@Test
	public void filterCountDonativoByEstadoAndDateBetweenAndInstTest() {
		Date dataInicial = convertDate("2017-06-05");
		Date dataFinal = convertDate("2017-06-09");
		Long quantidade = donativoRepository.filterCountDonativoByEstadoAndDateBetweenAndInst(dataInicial, dataFinal, Estado.NAO_ACEITO, 1l);
		assertThat(quantidade > 0);
	}
	
	/**
	 * 
	 */
	@Test
	public void countByCategoriaInstituicaoCaridadeIdTest() {
		Long quantidade = donativoRepository.countByCategoriaInstituicaoCaridadeId(1l);
		assertThat(quantidade > 0);
	}
	
	/**
	 * 
	 */
	@Test
	public void filterCountByMensageiroAndEstadoTest() {
		Mensageiro mensageiro = new Mensageiro();
		mensageiro.setId(1l);
		Long quantidade = donativoRepository.filterCountByMensageiroAndEstado(mensageiro);
		assertThat(quantidade > 0);
	}
	
	
	/**
	 * Utilitário para conversão de {@link String} em {@link Date}
	 * 
	 * @param date
	 * 		String que será convertida
	 * @return
	 * 		Data convertida
	 */
	private Date convertDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date data = null;
		try {
			data = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}
}
