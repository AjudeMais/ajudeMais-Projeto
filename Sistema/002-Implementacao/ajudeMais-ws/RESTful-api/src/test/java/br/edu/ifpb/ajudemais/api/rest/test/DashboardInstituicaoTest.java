
/**
 * 
 * <p>
 * <b> DashboardInstituicaoTest.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudemais.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ifpb.ajudeMais.api.rest.DashboardInstituicaoRestService;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;
import br.edu.ifpb.ajudeMais.service.negocio.InstituicaoCaridadeService;

/**
 * 
 * <p>
 * <b> {@link DashboardAdminRestTest} </b>
 * </p>
 *
 * <p>
 * Testes de unidade para {@link DashboardInstituicaoRestService}
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class DashboardInstituicaoTest extends AbstractRestTest{

	/**
	 * Injenta o serviço de conta
	 */
	@Autowired
	private ContaService contaService;
	
	/**
	 * Injenta o serviço de Instituicao
	 */
	@Autowired
	private InstituicaoCaridadeService instituicaoCaridadeService;
	
	/**
	 * 
	 * Cria usuários base para executar testes.
	 * 
	 */
	@Override
	protected void doInit() throws Exception {
		final Conta contaAdmin = new Conta();
		contaAdmin.setUsername("sheldonCoopper");
		contaAdmin.setSenha("bazinga");
		contaAdmin.setGrupos(Arrays.asList("ROLE_ADMIN"));
		contaAdmin.setEmail("coopper@gmail.com");
		contaAdmin.setAtivo(true);
		contaService.save(contaAdmin);
		
		final Conta contaInstituicao = new Conta();
		contaInstituicao.setUsername("Instituicao");
		contaInstituicao.setSenha("123456");
		contaInstituicao.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		contaInstituicao.setEmail("instituicao@gmail.com");
		contaInstituicao.setAtivo(true);

		InstituicaoCaridade instituicaoCaridade = new InstituicaoCaridade();
		instituicaoCaridade.setConta(contaInstituicao);
		instituicaoCaridade.setDescricao("Teste");
		instituicaoCaridade.setDocumento("32463030100");
		instituicaoCaridade.setNome("AJUDE MAIS");
		Endereco endereco = new Endereco();
		endereco.setBairro("Centro");
		endereco.setCep("58500-000");
		endereco.setComplemento("CASA");
		endereco.setLocalidade("Monteiro");
		endereco.setLogradouro("Leopoldino José Da Silva");
		endereco.setNumero("123");
		endereco.setUf("PB");
		
		instituicaoCaridade.setEndereco(endereco);
		instituicaoCaridade.setTelefone("(83)9980061984");
		instituicaoCaridadeService.save(instituicaoCaridade);

		
	}
	

	

	
	/**
	 * 
	 * <p>
	 * Tenta Recupera a quantidade de doação de uma instituição como Admin
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountDonativosNotAuthorization() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/instituicao/donativo/count").header("Authorization", getAuth("sheldonCoopper", "bazinga")))
		.andExpect(status().isForbidden());
		
	}
	

	
	/**
	 * <p>
	 *Tenta Recupera a quantidade de doações cadastradas sem Autorização
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountDonativosWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/instituicao/donativo/count")).andExpect(status().isUnauthorized());

	}
	

	/**
	 * 
	 * <p>
	 * Recupera a quantidade de doações recolhidas pela instituição logada
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountDonativosOk() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/instituicao/donativo/count").header("Authorization", getAuth("Instituicao", "123456")))
		.andExpect(status().isOk());
	}
	


	
	/**
	 * 
	 * <p>
	 * Recupera a quantidade de mensageiros cadastradas e ativos
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountMensageirosOk() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/instituicao/mensageiro/count").header("Authorization", getAuth("Instituicao", "123456")))
		.andExpect(status().isOk());
	}
	
	/**
	 * 
	 * <p>
	 * Tenta Recupera a quantidade de mensageiros cadastradas e ativos como Instituicao
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountMensageirosNotAuthorization() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/instituicao/mensageiro/count").header("Authorization", getAuth("sheldonCoopper", "bazinga")))
		.andExpect(status().isForbidden());
		
	}

	
	/**
	 * <p>
	 *Tenta Recupera a quantidade de mensageiros cadastrados e ativos sem Autorização
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountMensageirosWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/instituicao/mensageiro/count")).andExpect(status().isUnauthorized());

	}
	
	/**
	 * 
	 * <p>
	 * Recupera a quantidade de itens doáveis cadastradas 
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountCategoriasOk() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/instituicao/itens/count").header("Authorization", getAuth("Instituicao", "123456")))
		.andExpect(status().isOk());
	}
	
	/**
	 * <p>
	 *Tenta Recupera a quantidade de categorias cadastrados e ativos sem Autorização
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountCategoriasWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/instituicao/itens/count")).andExpect(status().isUnauthorized());

	}
	
	/**
	 * 
	 * <p>
	 * Tenta Recupera a quantidade de categorias cadastradas e ativos como Instituicao
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountCategoriasNotAuthorization() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/instituicao/itens/count").header("Authorization", getAuth("sheldonCoopper", "bazinga")))
		.andExpect(status().isForbidden());
		
	}

}
