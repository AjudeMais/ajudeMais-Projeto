
/**
 * 
 * <p>
 * <b> DashboardAdminRestTest.java </b>
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.api.rest.DashboardAdminRestService;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * 
 * <p>
 * <b> {@link DashboardAdminRestTest} </b>
 * </p>
 *
 * <p>
 * Testes de unidade para {@link DashboardAdminRestService}
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class DashboardAdminRestTest extends AbstractRestTest{
	

	/**
	 * Injenta o serviço de conta
	 */
	@Autowired
	private ContaService contaService;
	
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
		contaService.save(contaInstituicao);

		
	}
	
	/**
	 * 
	 * <p>
	 * Recupera a quantidade de instituições cadastradas e ativas
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountInstituicaoCaridadeOk() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/admin/instituicao/count").header("Authorization", getAuth("sheldonCoopper", "bazinga")))
		.andExpect(status().isOk());
	}
	
	/**
	 * 
	 * <p>
	 * Tenta Recupera a quantidade de instituições cadastradas e ativas sem Autorização
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountInstituicaoCaridadeNotAuthorization() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/admin/instituicao/count").header("Authorization", getAuth("Instituicao", "123456")))
		.andExpect(status().isForbidden());
	}
	
	/**
	 * <p>
	 *Tenta Recupera a quantidade de instituições cadastradas e ativas sem Autorização
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getInstituicoesWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/admin/instituicao/count")).andExpect(status().isUnauthorized());

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
		mockMvc.perform(get("/dashboard/admin/donativo/count")).andExpect(status().isUnauthorized());

	}
	
	

	/**
	 * 
	 * <p>
	 * Recupera a quantidade de doações recolhidas por todas as instituições
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountDonativosOk() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/admin/donativo/count").header("Authorization", getAuth("sheldonCoopper", "bazinga")))
		.andExpect(status().isOk());
	}
	

	/**
	 * 
	 * <p>
	 * Recupera a quantidade de doadores cadastradas e ativas
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountDoadoresOk() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/admin/doador/count").header("Authorization", getAuth("sheldonCoopper", "bazinga")))
		.andExpect(status().isOk());
	}
	
	/**
	 * <p>
	 *Tenta Recupera a quantidade de doadores cadastrados e ativos sem Autorização
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCountDoadoresWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/dashboard/admin/doador/count")).andExpect(status().isUnauthorized());

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
		mockMvc.perform(get("/dashboard/admin/mensageiro/count").header("Authorization", getAuth("sheldonCoopper", "bazinga")))
		.andExpect(status().isOk());
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
		mockMvc.perform(get("/dashboard/admin/mensageiro/count")).andExpect(status().isUnauthorized());

	}
	
}
