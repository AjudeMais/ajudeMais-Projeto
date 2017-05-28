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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;
import javassist.tools.web.BadHttpRequest;

/**
 * 
 * <p>
 * {@link CategoriaRestTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para exercitar testes para {@link CategoriaRestService}.
 * </p>
 *
 * @author Elson</a>
 *
 */
public class CategoriaRestTest extends AbstractRestTest {

	/**
	 * 
	 */
	private Categoria categoria;

	/**
	 * 
	 */
	@Autowired
	private ContaService contaService;

	/**
	 * <p>
	 * Cria usuários base para executar testes.
	 * </p>
	 * 
	 */
	@Override
	protected void doInit() throws Exception {

		Conta contaAdmin = new Conta();
		contaAdmin.setUsername("sheldonCoopper1");
		contaAdmin.setSenha("bazinga");
		contaAdmin.setGrupos(Arrays.asList("ROLE_ADMIN"));
		contaAdmin.setEmail("cupper1@gmail.com");
		contaAdmin.setAtivo(true);
		contaService.save(contaAdmin);

		Conta contaInst = new Conta();
		contaInst.setUsername("instituicaoXPTO1");
		contaInst.setSenha("myinst1");
		contaInst.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		contaInst.setEmail("inst1@gmail.com");
		contaInst.setAtivo(true);
		contaService.save(contaInst);
	}

	/**
	 * 
	 * <p>
	 * testa para criação de uma categoria
	 * </p>
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Test
	public void createCategoriaOk() throws IOException, Exception {
		getCategoria();
		mockMvc.perform(post("/categoria").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoXPTO1", "myinst1")).content(toJson(categoria)))
				.andExpect(status().isCreated());
	}

	/**
	 * 
	 * <p>
	 * Tenta criar uma categoria nula. deveria lançar um {@link BadHttpRequest}
	 * </p>
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Test
	public void createCategoriaNull() throws IOException, Exception {
		mockMvc.perform(post("/categoria").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoXPTO1", "myinst1")).content(toJson(categoria)))
				.andExpect(status().isBadRequest());
	}

	/**
	 * 
	 * <p>
	 * Tenta criar uma categoria com nome nulo. Deveria retornar
	 * {@link HttpStatus}.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createCategoriaNomeNull() throws IOException, Exception {
		getCategoria();
		categoria.setNome(null);
		mockMvc.perform(post("/categoria").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoXPTO1", "myinst1")).content(toJson(categoria)))
				.andExpect(status().isUnprocessableEntity());
	}

	/**
	 * 
	 * <p>
	 * Tenta criar uma categoria com o campo descrição nulo. deveria retonar Erro 422 Http.
	 * </p>
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void createCategoriaDescricaoNull() throws IOException, Exception {
		getCategoria();
		categoria.setDescricao(null);
		mockMvc.perform(post("/categoria").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoXPTO1", "myinst1")).content(toJson(categoria)))
				.andExpect(status().isUnprocessableEntity());
	}

	/**
	 * 
	 * <p>
	 * tenta buscar as categoria sem autorização. Deveria retornar
	 * {@link HttpStatus} 403.
	 * </p>
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void getCategoriasWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/categoria")).andExpect(status().isUnauthorized());

	}

	/**
	 * <p>
	 * Teste para verificar end point /Get. Caso de erro para acesso não
	 * autorizado. Deveria retornar {@linkp HttpStatus} 403.
	 * </p>
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Test
	public void getCategoriaNotAuthorization() throws IOException, Exception {
		mockMvc.perform(get("/categoria").header("Authorization", getAuth("instituicaoXPTO1", "myinst1")))
				.andExpect(status().isForbidden());

	}

	/**
	 * <p>
	 * Teste para obtenção de categorias, deveria retornar {@link HttpStatus}
	 * 200.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCategoriasOk() throws IOException, Exception {
		mockMvc.perform(get("/categoria").header("Authorization", getAuth("sheldonCoopper1", "bazinga")))
				.andExpect(status().isOk());
	}
	
	/**
	 * <p>
	 * Teste para obtenção de categorias por insituição, deveria retornar {@link HttpStatus}
	 * 200.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCategoriasByInstituicaoOk() throws IOException, Exception {
		mockMvc.perform(get("/categoria/instituicao").header("Authorization", getAuth("instituicaoXPTO1", "myinst1")))
				.andExpect(status().isOk());
	}

	/**
	 * <p>
	 * Teste para obtenção de categoria por ID, deve retornar sucesso.
	 * </p>
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Test
	public void findCategoriaIdOk() throws IOException, Exception {
		mockMvc.perform(get("/categoria/100").header("Authorization", getAuth("instituicaoXPTO1", "myinst1")))
				.andExpect(status().isOk());
	}
	
	

	/**
	 * <p>
	 * Tenta buscar uma categoria sem esta autenticado.
	 * </p>
	 * 
	 * @throws IOException
	 *             -
	 * 
	 * @throws Exception
	 *             -
	 */
	@Test
	public void findCategoriaByIdNotAuth() throws IOException, Exception {
		mockMvc.perform(get("/categoria/100")).andExpect(status().isUnauthorized());
	}

	/**
	 * <p>
	 * Cria categoria base para ser utilizada nos testes.
	 * </p>
	 * 
	 */
	private void getCategoria() {
		categoria = new Categoria();
		categoria.setNome("Informatica");
		categoria.setDescricao("equipamentos de informatica");
		categoria.setAtivo(true);
	}
}
