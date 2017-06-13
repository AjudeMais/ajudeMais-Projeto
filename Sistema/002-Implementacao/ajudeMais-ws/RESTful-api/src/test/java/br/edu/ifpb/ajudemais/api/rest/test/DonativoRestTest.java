package br.edu.ifpb.ajudemais.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * 
 * 
 * <p>
 * <b> {@link DonativoRestTest} </b>
 * </p>
 *
 * <p>
 *		Classe para testar os endpoints da parte de donativo
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
public class DonativoRestTest extends AbstractRestTest {

	private Donativo donativo;
	
	
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
		Conta contaInstituicao = new Conta();
		contaInstituicao.setUsername("Instituicao");
		contaInstituicao.setSenha("123456");
		contaInstituicao.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		contaInstituicao.setEmail("instituicao@gmail.com");
		contaInstituicao.setAtivo(true);
		contaService.save(contaInstituicao);
	}
	
	/**
	 * Tenta criar um donativo sem logar
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createDonativoWithoutAuth() throws IOException, Exception {
		getDonativo();
		mockMvc.perform(post("/donativo").contentType(MediaType.APPLICATION_JSON).content(toJson(donativo)))
				.andExpect(status().isUnauthorized());
	}
	
	/**
	 * Tenta criar um donativo utilizando um perfil de instituição de caridade
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createDonativoWithInstitutionProfile() throws IOException, Exception {
		getDonativo();
		mockMvc.perform(post("/donativo").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("Instituicao", "123456")).content(toJson(donativo)))
				.andExpect(status().isForbidden());
	}
	
	/**
	 *  Tenta buscar todos os donativos cadastrados no sistema
	 *  utilizando um perfil de instituição de caridade
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getDonativosOk() throws IOException, Exception {
		mockMvc.perform(get("/donativo").header("Authorization", getAuth("Instituicao", "123456")))
				.andExpect(status().isOk());
	}
	
	/**
	 *  Tenta buscar todos os donativos cadastrados no sistema sem logar
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getDonativosWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/donativo")).andExpect(status().isUnauthorized());
	}
	
	/**
	 *  Buscar todos os donativos de uma campanha com estado ativo depois de aceito.
	 *  utilizando um perfil de instituição de caridade
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void filterDonativoByEstadoAfterAceitoOk() throws IOException, Exception {
		mockMvc.perform(get("/donativo/filter/campanha/estado/2").header("Authorization", getAuth("Instituicao", "123456")))
				.andExpect(status().isOk());
	}
	
	private Donativo getDonativo() {
		donativo = new Donativo();
		donativo.setNome("Roupas");
		donativo.setDescricao("Algumas roupas velhas, porém, em bom estado");
		donativo.setQuantidade(10);
		
		return donativo;
	}
	
	
}
