package br.edu.ifpb.ajudemais.api.rest.test;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;

import br.edu.ifpb.ajudeMais.AjudeMaisApplication;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;

@SpringBootTest(classes = AjudeMaisApplication.class)
@ActiveProfiles(profiles={"test"})
@RunWith(SpringRunner.class)
public class AbstractRestTest {

	protected MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private Gson gson = new Gson();
	
	@SuppressWarnings("rawtypes")
	private static Set<Class> inited = new HashSet<>();

	@Autowired
	private WebApplicationContext webApplicationContext;

	/**
	 * 
	 */
	@Before
	public void setup() {
		mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
	}

	/**
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

	protected void doInit() throws Exception {
	}

	/**
	 * 
	 * @param o
	 * @return
	 * @throws IOException
	 */
	protected String json(Object o) throws IOException {
		return mapper.writeValueAsString(o);
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 * @throws IOException
	 */
	protected String toJson(Object o) throws IOException {
		String body = gson.toJson(o);
		return body;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	protected ResultActions login(String username, String password) throws Exception {
		final Conta auth = new Conta();
		auth.setUsername(username);
		auth.setSenha(password);
		return mockMvc.perform(post("/auth/login").content(json(auth)).contentType(MediaType.APPLICATION_JSON));
	}

	/**
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
	 * @param result
	 * @return header
	 */
	protected String extractTokenHeader(MvcResult result) {
		return result.getResponse().getHeader("Authorization");
	}

}
