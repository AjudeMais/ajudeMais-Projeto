package br.edu.ifpb.ajudemais.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.api.rest.CampanhaRestService;
import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * Classe utilizada para executar testes para {@link CampanhaRestService}
 * @author elson
 *
 */
public class CampanhaRestServiceTest extends AbstractRestTest{
	
	/**
	 * 
	 */
	private Campanha campanha;
	
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
		
		final Conta conta = new Conta();
		conta.setUsername("instituicaoTESTE");
		conta.setSenha("123456");
		conta.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		conta.setEmail("istituicaoteste@gmail.com");
		conta.setAtivo(true);
		contaService.save(conta);
	}
	
	/**
	 * Tenta criar uma campanha nula, deve retornar um status http 400
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createCampanhaNull() throws IOException, Exception {
		mockMvc.perform(post("/campanha").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoTESTE", "123456")).content(toJson(campanha)))
				.andExpect(status().isBadRequest());
	}
	/**
	 * tenta pegar as campanhas sem autorização, retorna status http 401
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCampanhasWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/campanha")).andExpect(status().isUnauthorized());
		
	}
	/**
	 * tenta atualizar campanha sem autorização, deve retornar status http 401
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void updateCampanhasWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/campanha")).andExpect(status().isUnauthorized());
		
	}
	/**
	 * pega todas as campanhas ja cadastradas retorna status http 200
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCampanhasOk() throws IOException, Exception {
		mockMvc.perform(get("/campanha").header("Authorization", getAuth("instituicaoTESTE", "123456")))
				.andExpect(status().isOk());
	}
	/**
	 * tenta pegar as campanhas por instituiçao retorna status http 200
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCampanhasByInstituicaoOk() throws IOException, Exception {
		mockMvc.perform(get("/campanha/instituicao").header("Authorization", getAuth("instituicaoTESTE", "123456")))
				.andExpect(status().isOk());
	}
	
	/**
	 * Tenta buscar uma campanha pelo identificador sem está autenticado.
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findCampanhaByIdNotAuth() throws IOException, Exception {
		mockMvc.perform(get("/campanha/10")).andExpect(status().isUnauthorized());
	}
	/**
	 * recupera uma campanha pelo seu identificador, deve retornar status http 200
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findCampanhaIdOk() throws IOException, Exception {
		mockMvc.perform(get("/campanha/100").header("Authorization", getAuth("instituicaoTESTE", "123456")))
				.andExpect(status().isOk());
	}
	
	/**
	 * 
	 * 
	 */
	private void getCampanha() {
		campanha = new Campanha();
		campanha.setNome("Natal sem fome");
		campanha.setDescricao("campanha para arrecadar alimentos para os moradores de rua");
		campanha.setDataInicio(new Date(1497225600000l));
		campanha.setDataFim(new Date(1499817600000l));
		campanha.setInstituicaoCaridade(getInstituicao());
	}
	/**
	 * 
	 * @return
	 */
	private InstituicaoCaridade getInstituicao() {
		InstituicaoCaridade instituicao = new InstituicaoCaridade();
		instituicao.setNome("intituição do bem");
		instituicao.setDescricao("instituição do bem");
		instituicao.setTelefone("8399273464");
		instituicao.setDocumento("107.345.123-40");

		Conta conta = new Conta();
		conta.setUsername("instituicao");
		conta.setSenha("123456");
		conta.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		conta.setEmail("instituicao@gmail.com");

		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua Maira Nunes");
		endereco.setBairro("Centro");
		endereco.setCep("58560-0000");
		endereco.setNumero("s/n");
		endereco.setLocalidade("Monteiro");
		endereco.setUf("PB");

		instituicao.setConta(conta);
		instituicao.setEndereco(endereco);

		return instituicao;
	}
}
