/**
 * Ajude Mais - Módulo Web Service
 * 
 * Sistema para potencializar o processo de doação.
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudemais.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.api.rest.ContaRestService;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * 
 * <p>
 * {@link ContaRestServiceTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes referentes à {@link ContaRestService}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class ContaRestServiceTest extends AbstractRestTest {

	/**
	 * Injenta o serviço de conta
	 */
	@Autowired
	private ContaService contaService;
	
	private Conta conta;

	/**
	 * Inicia as configurações básicas para realização dos testes
	 */
	@Override
	protected void doInit() throws Exception {

		conta = new Conta();
		conta.setUsername("sheldonCoopper");
		conta.setSenha("bazinga");
		conta.setGrupos(Arrays.asList("ROLE_ADMIN"));
		conta.setEmail("coopper@gmail.com");
		conta.setAtivo(true);
		contaService.save(conta);

	}
	
	/**
	 * 
	 * <p>
	 * </p>
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createContaOk() throws IOException, Exception {
		mockMvc.perform(post("/conta").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(conta)))
				.andExpect(status().isOk());
	}

}
