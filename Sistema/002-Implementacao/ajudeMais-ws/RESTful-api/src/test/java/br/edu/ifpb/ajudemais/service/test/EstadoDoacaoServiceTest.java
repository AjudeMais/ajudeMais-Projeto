package br.edu.ifpb.ajudemais.service.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.edu.ifpb.ajudeMais.AjudeMaisApplication;
import br.edu.ifpb.ajudeMais.domain.entity.EstadoDoacao;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.EstadoDoacaoService;

/**
 * 
 * 
 * <p>
 * <b> {@link EstadoDoacaoServiceTest} </b>
 * </p>
 *
 * <p>
 *		Classe para testar os serviços oferecidos na parte de estados de uma doação
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
@SpringBootTest(classes = AjudeMaisApplication.class)
@ActiveProfiles(profiles = { "test" })
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class EstadoDoacaoServiceTest {
	
	private EstadoDoacao estadoDoacao;
	
	@Autowired
	private EstadoDoacaoService estadoDoacaoService;
	
	private EstadoDoacaoService mockedEstadoDoacaoService;
	
	
	/**
	 * metodo que prepara para as unidades de teste;
	 */
	@Before
	public void setUp() {
		mockedEstadoDoacaoService = mock(EstadoDoacaoService.class);
		getEstadoDoacao();
	}
	
	/**
	 * Testa para salvar o estado de uma doação
	 * @throws AjudeMaisException
	 */
	@Test
	public void saveEstadoDoacao() throws AjudeMaisException {
		mockedEstadoDoacaoService.save(estadoDoacao);
		verify(mockedEstadoDoacaoService).save(estadoDoacao);
	}
	
	/**
	 * Testa para atualizar o estado de uma doação
	 * @throws AjudeMaisException
	 */
	@Test
	public void updateEstadoDoacao() throws AjudeMaisException {
		mockedEstadoDoacaoService.update(estadoDoacao);
		verify(mockedEstadoDoacaoService).update(estadoDoacao);
	}
	
	/**
	 * Testa para remover o estado de uma doação
	 * @throws AjudeMaisException
	 */
	@Test
	public void removeEstadoDoacao() throws AjudeMaisException {
		mockedEstadoDoacaoService.remover(estadoDoacao);
		verify(mockedEstadoDoacaoService).remover(estadoDoacao);
	}

	private EstadoDoacao getEstadoDoacao() {
		estadoDoacao = new EstadoDoacao();
		estadoDoacao.setId(1l);
		estadoDoacao.setData(new Date());
		estadoDoacao.setAtivo(true);
		estadoDoacao.setEstadoDoacao(Estado.DISPONIBILIZADO);
		return estadoDoacao;
		
	}

}
