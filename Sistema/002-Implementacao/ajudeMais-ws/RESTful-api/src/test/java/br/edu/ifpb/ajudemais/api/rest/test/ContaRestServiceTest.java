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
package br.edu.ifpb.ajudemais.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.api.dto.ChangePasswordDTO;
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
	 * 
	 */
	@Autowired
	private ContaService contaService;
	private ChangePasswordDTO changePasswordDTO;
	/**
	 * Inicia as configurações básicas para realização dos testes
	 */
	@Override
	protected void doInit() throws Exception {
		Conta conta = new Conta();
		conta.setUsername("admin");
		conta.setSenha("admin");
		conta.setGrupos(Arrays.asList("ROLE_ADMIN"));
		conta.setEmail("admin10@gmail.com");
		conta.setAtivo(true);
		contaService.save(conta);
	}

	/**
	 * 
	 * <p>
	 * Teste para criação de conta normalmente. Deveria retornar HttpStatus/201
	 * (Created)
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createContaOk() throws IOException, Exception {
		mockMvc.perform(post("/conta")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(getConta())))
				.andExpect(status().isCreated());
	}

	/**
	 * 
	 * <p>
	 * Teste para criação de conta com body nulo. Deveria retornar Http 400.
	 * (Bad Request)
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createContaNullBody() throws IOException, Exception {
		mockMvc.perform(post("/conta").contentType(MediaType.APPLICATION_JSON).content(toJson(null)))
				.andExpect(status().isBadRequest());
	}

	/**
	 * 
	 * <p>
	 * Teste para criação de conta com atributo username nulo. Deveria retornar
	 * Http 422. (Unprocessable Entity)
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createContaUsernameNull() throws IOException, Exception {
		Conta conta = getConta();
		conta.setUsername(null);
		mockMvc.perform(post("/conta")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(conta)))
				.andExpect(status().isUnprocessableEntity());
	}

	/**
	 * 
	 * <p>
	 * Teste para criação de conta com atributo senha nulo. Deveria retornar
	 * Http 422 (Unprocessable Entity).
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createContaPasswordNull() throws IOException, Exception {
		Conta conta = getConta();
		conta.setSenha(null);
		mockMvc.perform(post("/conta")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(conta)))
				.andExpect(status().isUnprocessableEntity());
	}

	/**
	 * 
	 * <p>
	 * Teste para criação de conta com atributo email nulo. Deveria retornar
	 * Http 422. (Unprocessable Entity)
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createContaEmailNull() throws IOException, Exception {
		Conta conta = getConta();
		conta.setEmail(null);
		mockMvc.perform(post("/conta")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(conta)))
				.andExpect(status().isUnprocessableEntity());
	}

	/**
	 * 
	 * <p>
	 * Teste para alteração de senha de uma conta. Deveria retornar 200 (OK)
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void changePasswordWithAuth() throws IOException, Exception {
		changePasswordDTO = new ChangePasswordDTO();
		changePasswordDTO.setNewPassword("novaSenha");
		changePasswordDTO.setPassword("admin");
		mockMvc.perform(post("/conta/changePassword")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("admin", "admin"))
				.content(toJson(changePasswordDTO)))
				.andExpect(status().isOk());
	}
	
	

	/**
	 * 
	 * <p>
	 * Teste para alteração de senha de uma conta sem fornecer autenticação.
	 * Deveria retornar Http 401.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void changePasswordWithoutAuth() throws IOException, Exception {
		changePasswordDTO = new ChangePasswordDTO();
		changePasswordDTO.setNewPassword( "novaSenha");
		changePasswordDTO.setPassword("admin");
		
		mockMvc.perform(post("/conta/changePassword")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(changePasswordDTO)))
				.andExpect(status().isUnauthorized());
	}

	/**
	 * 
	 * <p>
	 * 
	 * Cria uma nova conta e retorna-a
	 * </p>
	 * 
	 * @return conta criada
	 */
	private Conta getConta() {
		Conta conta = new Conta();
		conta.setUsername("sheldonCoopper");
		conta.setSenha("bazinga");
		conta.setGrupos(Arrays.asList("ROLE_ADMIN"));
		conta.setEmail("coopper10@gmail.com");
		conta.setAtivo(true);

		return conta;
	}

}
