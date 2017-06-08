package br.edu.ifpb.ajudemais.api.rest.test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;

import br.edu.ifpb.ajudeMais.api.rest.ImagemStorageRestService;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;

/**
 * 
 * <p>
 * {@link ImagemStorageRestServiceTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes de integração referentes a
 * {@link ImagemStorageRestService}.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class ImagemStorageRestServiceTest extends AbstractRestTest {

	/**
	 * Registra usuário para autenticação.
	 */
	@Override
	protected void doInit() throws Exception {
		registerUser("penny", "bigbang", "ROLE_DOADOR", "penny@mail.com").andExpect(status().isCreated());
	}

	/**
	 * 
	 * <p>
	 * Realiza teste para endpoint de upload de imagem. Deveria retornar 200
	 * (OK).
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void uploadTestOk() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		final String token = extractToken(login("penny", "bigbang").andReturn());
		mockMvc.perform(fileUpload("/upload/imagem").file(file).header("Authorization", token))
				.andExpect(status().isOk());

	}
	

	
	/**
	 * 
	 * <p>
	 * Realiza teste para endpoint de upload de imagem, sem autenticação.
	 * Deveria retornar 401.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void uploadNotAuth() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		mockMvc.perform(fileUpload("/upload/imagem").file(file)).andExpect(status().isUnauthorized());

	}

	/**
	 * 
	 * <p>
	 * realiza teste para endpoint de upload de imagem passando null em nome do
	 * arquivo. Deveria lançar exceção.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void uploadNullNameFile() throws Exception {
		MockMultipartFile file = new MockMultipartFile(null, "orig", null, "bar".getBytes());
		final String token = extractToken(login("penny", "bigbang").andReturn());
		mockMvc.perform(fileUpload("/upload/imagem").file(file).header("Authorization", token))
				.andExpect(status().isOk());

	}

	/**
	 * 
	 * <p>
	 * realiza teste para endpoint de upload de imagem passando null MultipPart.
	 * Deveria lançar exceção.
	 * </p>
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void uploadNullMultiPartFile() throws UnsupportedEncodingException, Exception {
		final String token = extractToken(login("penny", "bigbang").andReturn());
		mockMvc.perform(fileUpload("/upload/imagem").file(null).header("Authorization", token))
				.andExpect(status().isOk());
	}

	/**
	 * 
	 * <p>
	 * Teste para endpoint de recuperar imagem, passando um nome de uma imagem
	 * não existente.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void getImageNotFoundTest() throws Exception {
		final String token = extractToken(login("penny", "bigbang").andReturn());
		mockMvc.perform(get("/upload/imagem/x").header("Authorization", token)).andExpect(status().isBadRequest());
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
