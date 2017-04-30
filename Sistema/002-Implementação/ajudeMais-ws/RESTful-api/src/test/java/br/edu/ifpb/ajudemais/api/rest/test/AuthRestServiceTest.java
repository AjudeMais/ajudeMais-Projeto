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
		registerUser("penny", "bigbang", "ROLE_DOADOR", "penny@mail.com")
		.andExpect(status().isCreated());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void loginOk() throws Exception {
		login("penny", "bigbang")
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.token").exists())
		.andReturn();
	}
	
	/**
	 * 
	 * @throws Exception
	 */
    @Test
    public void loginNok() throws Exception {
        login("bernadette", "microbiologic")
        .andExpect(status().isUnauthorized());
    }
    
    /**
     * 
     * @throws Exception
     */
    @Test
    public void getUserWithTokenIsAllowed() throws Exception {
        final String token = extractToken(login("penny", "bigbang").andReturn());
        mockMvc.perform(get("/auth/user")
        		.header("Authorization", token))
                .andExpect(status().isOk());
    }
    
    /**
     * 
     * @throws Exception
     */
    @Test
    public void getUserWithoutTokenIsNotAllowed() throws Exception {
        mockMvc.perform(get("/auth/user"))
        .andExpect(status().isUnauthorized());
    }
    
    /**
     * Testa atuaizazação de token.
     * 
     * @throws Exception
     */
    @Test
    public void validateTokenExistent() throws Exception {
        final String token = extractToken(login("penny", "bigbang").andReturn());
        final String tokenUpdated = extractTokenHeader(mockMvc.perform(get("/auth/valida")
        		.header("Authorization", token))
                .andReturn());
        
        assertNotNull(token);
        assertNotNull(tokenUpdated);
        assertNotEquals(token, tokenUpdated);
    }

	/**
	 * 
	 * @param username
	 * @param password
	 * @param role
	 * @param email
	 * @return
	 * @throws Exception
	 */
	private ResultActions registerUser(String username, String password, String role, String email) throws Exception {

		Conta conta = new Conta();
		conta.setUsername(username);
		conta.setSenha(password);
		conta.setEmail(email);
		
		return mockMvc.perform(post("/conta")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(conta)));
	}
}
