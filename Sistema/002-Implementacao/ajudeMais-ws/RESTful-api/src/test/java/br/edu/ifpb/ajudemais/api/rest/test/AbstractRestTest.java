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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;

import br.edu.ifpb.ajudeMais.AjudeMaisApplication;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;

/**
 * Classe com configuração para execução de testes com o {@link MockMvc}
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */

/**
 * Configurações necessárias para execução dos testes e para execução com
 * profile exclusivo para testes
 *
 */
@SpringBootTest(classes = AjudeMaisApplication.class)
@ActiveProfiles(profiles = { "test" })
@WebAppConfiguration
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class AbstractRestTest {

	/**
	 * 
	 */
	protected MockMvc mockMvc;

	/**
	 * 
	 */
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 */
	private Gson gson = new Gson();

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private static Set<Class> inited = new HashSet<>();

	/**
	 * 
	 */
	@Autowired
	private WebApplicationContext webApplicationContext;

	/**
	 * Configura o o contexto do spring, incluindo segurança.
	 */
	@Before
	public void setup() {
		mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
	}

	/**
	 * Método é chamando em antes de cada execução, mas considera o init das
	 * classes filhas apenas uma vez.
	 * 
	 * @throws Exception
	 */
	@Before
	public void init() throws Exception {
		if (!inited.contains(getClass())) {
			doInit();
			inited.add(getClass());
		}
	}

	/**
	 * 
	 * Deve ser implementado por toda classe que extende essa classe.
	 * 
	 * É utilizado para resolver o problema do {@link @Before}
	 * 
	 * @throws Exception
	 */
	protected void doInit() throws Exception {
	}

	/**
	 * Converte um objeto para JSON, usando {@link ObjectMapper}
	 * 
	 * @param o
	 *            - objeto para ser convertido para JSON.
	 * 
	 * @return Objeto serializado para JSON.
	 * 
	 * @throws IOException
	 *             -
	 */
	protected String json(Object o) throws IOException {
		return mapper.writeValueAsString(o);
	}

	/**
	 * Converte um objeto para JSON, utilizando {@link Gson}
	 * 
	 * @param o
	 *            - objeto a ser serializado
	 * 
	 * @return json
	 * 
	 * @throws IOException
	 */
	protected String toJson(Object o) throws IOException {
		String body = gson.toJson(o);
		return body;
	}

	/**
	 * Criar autenticação, usando {@link MockMvc}
	 * 
	 * @param username
	 *            - nome de usuário a ser autenticado
	 * 
	 * @param password
	 *            - senha para login.
	 * 
	 * @return resultado da autenticação
	 * 
	 * @throws Exception
	 */
	protected ResultActions login(String username, String password) throws Exception {
		final Conta auth = new Conta();
		auth.setUsername(username);
		auth.setSenha(password);
		return mockMvc.perform(post("/auth/login").content(json(auth)).contentType(MediaType.APPLICATION_JSON));
	}

	/**
	 * Testa uma autenticação passada por parametro e cria uma autenticação base
	 * para ser usada nos testes;
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	protected String getAuth(String username, String password) throws Exception {
		final String token = extractToken(
				login(username, password).andExpect(jsonPath("$.token").exists()).andReturn());

		return token;
	}

	/**
	 * Extrai token de acesso do response de uma requisição.
	 * 
	 * @param result
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected String extractToken(MvcResult result) throws UnsupportedEncodingException {
		return JsonPath.read(result.getResponse().getContentAsString(), "$.token");
	}

	/**
	 * 
	 * Extrai token de acesso do cabecalho da requisição.
	 * 
	 * @param result
	 * @return header
	 */
	protected String extractTokenHeader(MvcResult result) {
		return result.getResponse().getHeader("Authorization");
	}

}
