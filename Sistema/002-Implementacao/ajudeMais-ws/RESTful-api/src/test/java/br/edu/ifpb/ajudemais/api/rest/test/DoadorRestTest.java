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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.api.rest.DoadorRestService;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Doador;

/**
 * 
 * <p>
 * {@link DoadorRestTest}
 * </p>
 * 
 * <p>
 * Testes de unidade para {@link DoadorRestService}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class DoadorRestTest extends AbstractRestTest {
	
	/**
	 * 
	 */
	private Doador doador;

	/**
	 * 
	 * Cria usuários base para executar testes.
	 * 
	 */
	@Override
	protected void doInit() throws Exception {

	}

	/**
	 * 
	 * <p>
	 * Cria um doador para caso de sucesso. Deveria retornar Http 201 (Created)
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createDoadorOk() throws IOException, Exception {
		getDoador();
		mockMvc.perform(post("/doador").contentType(MediaType.APPLICATION_JSON).content(toJson(doador)))
				.andExpect(status().isCreated());
	}
	
	/**
	 * 
	 * <p>
	 * Tenta atualizar um doador existente. Deveria retornar Http 401.
	 * Usuário autenticado para dispositivo web.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void updateDoadorModuleNotAllowed() throws IOException, Exception {
		getDoador();
		mockMvc.perform(put("/doador").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(doador)))
				.andExpect(status().isUnauthorized());
	}

	/**
	 * 
	 * <p>
	 * Cria um doador para caso de erro, passando body null. Deveria retornar
	 * Http 400 (Bad Request)
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createDoadorNullBody() throws IOException, Exception {
		mockMvc.perform(post("/doador").contentType(MediaType.APPLICATION_JSON).content(toJson(null)))
				.andExpect(status().isBadRequest());
	}

	/**
	 * <p>
	 * Executa teste para obtenção de doadores cadastrados, considerando um caso
	 * de falha. Deveria retornar Http 401
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findAllDoadorWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/doador")).andExpect(status().isUnauthorized());

	}

	/**
	 * 
	 * <p>
	 * Cria e retorna um doador para execução dos testes.
	 * </p>
	 * 
	 * @return novo doador
	 */
	private Doador getDoador() {
		doador = new Doador();
		doador.setNome("Jão Miguel");
		doador.setTelefone("8396463738");

		final Conta conta = new Conta();
		conta.setUsername("doadorX");
		conta.setSenha("doadorX");
		conta.setGrupos(Arrays.asList("ROLE_DOADOR"));
		conta.setEmail("doadorX@gmail.com");
		conta.setAtivo(true);
		doador.setConta(conta);

		return doador;
	}

}
