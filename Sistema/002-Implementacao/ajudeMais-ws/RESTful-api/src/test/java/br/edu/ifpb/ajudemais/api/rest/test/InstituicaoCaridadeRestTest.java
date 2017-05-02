package br.edu.ifpb.ajudemais.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.api.rest.InstituicaoCaridadeRestService;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * Testes de unidade para {@link InstituicaoCaridadeRestService}
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class InstituicaoCaridadeRestTest extends AbstractRestTest{
	
	private InstituicaoCaridade instituicao;
	
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
	public void createInstituicaoOk() throws IOException, Exception {
		getInstituicao();

		mockMvc.perform(post("/instituicao")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCupper", "bazinga"))
				.content(toJson(instituicao)))
				.andExpect(status().isCreated());
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoNull() throws IOException, Exception {
		mockMvc.perform(post("/instituicao")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCupper", "bazinga"))
				.content(toJson(instituicao)))
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoNomeNull() throws IOException, Exception {
		getInstituicao();
		instituicao.setNome(null);
		
		mockMvc.perform(post("/instituicao")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCupper", "bazinga"))
				.content(toJson(instituicao)))
				.andExpect(status().isUnprocessableEntity());
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoDescricaoNull() throws IOException, Exception {
		getInstituicao();
		instituicao.setDescricao(null);
		
		mockMvc.perform(post("/instituicao")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCupper", "bazinga"))
				.content(toJson(instituicao)))
				.andExpect(status().isUnprocessableEntity());
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoEnderecoNull() throws IOException, Exception {
		getInstituicao();
		instituicao.setEndereco(null);;
		
		mockMvc.perform(post("/instituicao")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCupper", "bazinga"))
				.content(toJson(instituicao)))
				.andExpect(status().isUnprocessableEntity());
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoContaNull() throws IOException, Exception {
		getInstituicao();
		instituicao.setConta(null);
		
		mockMvc.perform(post("/instituicao")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCupper", "bazinga"))
				.content(toJson(instituicao)))
				.andExpect(status().isUnprocessableEntity());
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoDocumentoNull() throws IOException, Exception {
		getInstituicao();
		instituicao.setDocumento(null);
		
		mockMvc.perform(post("/instituicao")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCupper", "bazinga"))
				.content(toJson(instituicao)))
				.andExpect(status().isUnprocessableEntity());
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getInstituicoesWithoutAuth() throws IOException, Exception {
       mockMvc.perform(get("/instituicao"))
       .andExpect(status().isUnauthorized());

	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getInstituicoesNotAuthorization() throws IOException, Exception {
       mockMvc.perform(get("/instituicao")
    	.header("Authorization", getAuth("instituicaoXPTO", "myinst")))
        .andExpect(status().isForbidden());

	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getInstituicoesOk() throws IOException, Exception {
        mockMvc.perform(get("/instituicao")
        		.header("Authorization", getAuth("sheldonCupper", "bazinga")))
        		.andExpect(status().isOk());
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findInstituicaoByIdOk() throws IOException, Exception {
        mockMvc.perform(get("/instituicao/100")
        		.header("Authorization", getAuth("instituicaoXPTO", "myinst")))
        		.andExpect(status().isOk());
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findInstituicaoByIdNotAuth() throws IOException, Exception {
        mockMvc.perform(get("/instituicao/100"))
        		.andExpect(status().isUnauthorized());
	}
	
	/**
	 * 
	 * @return
	 */
	private Endereco getEndereco() {
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua Maira Nunes");
		endereco.setBairro("Centro");
		endereco.setCep("58560-0000");
		endereco.setNumero("s/n");
		endereco.setLocalidade("Monteiro");
		endereco.setUf("PB");
		return endereco;
		
	}
	
	/**
	 * 
	 */
	private void getInstituicao() {
		instituicao = new InstituicaoCaridade();
		instituicao.setNome("ONG XPTO");
		instituicao.setDescricao("ONG visa algo.");
		instituicao.setTelefone("8399273464");
		instituicao.setDocumento("107.345.123-40");
		
	
		Conta conta = new Conta();
		conta.setUsername("rajesh");
		conta.setSenha("euFaloComMulher");
		conta.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		conta.setEmail("raj@gmail.com");
		
		instituicao.setConta(conta);
		instituicao.setEndereco(getEndereco());
	}

}
