package br.edu.ifpb.ajudemais.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

public class CategoriaRestTest extends AbstractRestTest{
	
	private Categoria categoria;
	
	@Autowired
	private ContaService contaService;
	
	/**
	 * Cria usuários base para executar testes.
	 * 
	 */
	@Override
	protected void doInit() throws Exception {
			
		Conta contaAdmin = new Conta();
		contaAdmin.setUsername("sheldonCupper");
		contaAdmin.setSenha("bazinga");
		contaAdmin.setGrupos(Arrays.asList("ROLE_ADMIN"));
		contaAdmin.setEmail("cupper@gmail.com");
		contaService.save(contaAdmin);
		
		Conta contaInst = new Conta();
		contaInst.setUsername("instituicaoXPTO");
		contaInst.setSenha("myinst");
		contaInst.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		contaInst.setEmail("inst@gmail.com");
		contaService.save(contaInst);		
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createCategoriaOk() throws IOException, Exception {
		getCategoria();

		mockMvc.perform(post("/categoria")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoXPTO", "myinst"))
				.content(toJson(categoria)))
				.andExpect(status().isCreated());
	}
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createCategoriaNull() throws IOException, Exception {
		mockMvc.perform(post("/categoria")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoXPTO", "myinst"))
				.content(toJson(categoria)))
				.andExpect(status().isBadRequest());
	}
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createCategoriaNomeNull()throws IOException, Exception{
		getCategoria();
		categoria.setNome(null);
		mockMvc.perform(post("/categoria")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoXPTO", "myinst"))
				.content(toJson(categoria)))
				.andExpect(status().isUnprocessableEntity());
	}
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createCategoriaDescricaoNull()throws IOException, Exception{
		getCategoria();
		categoria.setDescricao(null);
		mockMvc.perform(post("/categoria")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoXPTO", "myinst"))
				.content(toJson(categoria)))
				.andExpect(status().isUnprocessableEntity());
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCategoriasWithoutAuth() throws IOException, Exception {
       mockMvc.perform(get("/categoria"))
       .andExpect(status().isUnauthorized());

	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCategoriaNotAuthorization() throws IOException, Exception {
       mockMvc.perform(get("/categoria")
    	.header("Authorization", getAuth("sheldonCupper", "bazinga")))
        .andExpect(status().isForbidden());

	}
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCategoriasOk() throws IOException, Exception {
        mockMvc.perform(get("/categoria")
        		.header("Authorization", getAuth("instituicaoXPTO", "myinst")))
        		.andExpect(status().isOk());
	}
	

	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findCategoriaIdOk() throws IOException, Exception {
        mockMvc.perform(get("/categoria/100")
        		.header("Authorization", getAuth("instituicaoXPTO", "myinst")))
        		.andExpect(status().isOk());
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findCategoriaByIdNotAuth() throws IOException, Exception {
        mockMvc.perform(get("/categoria/100"))
        		.andExpect(status().isUnauthorized());
	}
	
	private void getCategoria() {
		categoria = new Categoria();
		categoria.setNome("Informatica");
		categoria.setDescricao("equipamentos de informatica");
		
	}

}
