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

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import br.edu.ifpb.ajudeMais.api.rest.AuthRestService;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;

/**
 * Testes de unidade para serviços de {@link AuthRestService}
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class AuthRestServiceTest extends AbstractRestTest {

	/**
	 * Cria usuário base para executar testes.
	 * 
	 */
	@Override
	protected void doInit() throws Exception {
		registerUser("penny", "bigbang", "ROLE_DOADOR", "penny@mail.com").andExpect(status().isCreated());
	}

	/**
	 * <p>
	 * Criar teste para login e deve obter sucesso.
	 * </p>
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void loginOk() throws Exception {
		login("penny", "bigbang").andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
	}

	/**
	 * <p>
	 * Criar teste para login e deve obter error. Tenta efetuar login com
	 * usuário inexistente.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void loginNotOk() throws Exception {
		login("bernadette", "microbiologic").andExpect(status().isUnauthorized());
	}
	
	/**
	 * <p>
	 * Criar teste para login e deve obter error. Tenta efetuar login com apenas username.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void loginWithPasswordNull() throws Exception {
		login("bernadette", null).andExpect(status().isUnauthorized());
	}
	
	/**
	 * 
	 * <p>
	 * </p>
	 * @throws Exception
	 */
	@Test
	public void loginWithUsernameNull() throws Exception {
		login(null, "bolo").andExpect(status().isUnauthorized());
	}
	


	/**
	 * Cria teste para exercitar endpoint que recupera o usuário.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getUserWithTokenIsAllowed() throws Exception {
		final String token = extractToken(login("penny", "bigbang").andReturn());
		mockMvc.perform(get("/auth/user").header("Authorization", token)).andExpect(status().isOk());
	}

	/**
	 * Teste tenta acessar endpoit sem autenticação.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getUserWithoutTokenIsNotAllowed() throws Exception {
		mockMvc.perform(get("/auth/user")).andExpect(status().isUnauthorized());
	}

	/**
	 * Testa atualição de token.
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateTokenExistent() throws Exception {
		final String token = extractToken(login("penny", "bigbang").andReturn());
		final String tokenUpdated = extractTokenHeader(
				mockMvc.perform(get("/auth/valida").header("Authorization", token)).andReturn());

		assertNotNull(token);
		assertNotNull(tokenUpdated);
		assertNotEquals(token, tokenUpdated);
	}

	/**
	 * <p>
	 * Criar um usuário base para ser utilizado nos testes.
	 * </p>
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @param role
	 * 
	 * @param email
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	private ResultActions registerUser(String username, String password, String role, String email) throws Exception {

		Conta conta = new Conta();
		conta.setUsername(username);
		conta.setSenha(password);
		conta.setEmail(email);
		conta.setAtivo(true);

		return mockMvc.perform(post("/conta").contentType(MediaType.APPLICATION_JSON).content(toJson(conta)));
	}
}
