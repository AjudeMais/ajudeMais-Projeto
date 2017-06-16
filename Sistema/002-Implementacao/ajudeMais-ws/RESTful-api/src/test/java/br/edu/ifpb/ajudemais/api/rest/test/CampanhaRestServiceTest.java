package br.edu.ifpb.ajudemais.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.api.rest.CampanhaRestService;
import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Meta;
import br.edu.ifpb.ajudeMais.domain.enumerations.UnidadeMedida;
import br.edu.ifpb.ajudeMais.service.negocio.CategoriaService;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;
import br.edu.ifpb.ajudeMais.service.negocio.InstituicaoCaridadeService;

/**
 * Classe utilizada para executar testes para {@link CampanhaRestService}
 * 
 * @author elson
 *
 */
public class CampanhaRestServiceTest extends AbstractRestTest {

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
	 * 
	 */
	@Autowired
	private CategoriaService categoriaService;
	
	/**
	 * 
	 */
	@Autowired
	private InstituicaoCaridadeService instituicaoCaridadeService;

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

		Categoria categoria = new Categoria();
		categoria.setNome("roupas");
		categoria.setDescricao("agasalho");
		categoria.setAtivo(true);
		categoriaService.save(categoria);
		
		final Conta conta1 = new Conta();
		conta1.setUsername("instituicaoTESTE1");
		conta1.setSenha("123456");
		conta1.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		conta1.setEmail("istituicaoteste1@gmail.com");
		conta1.setAtivo(true);
		
		InstituicaoCaridade instituicao = new InstituicaoCaridade();
		instituicao.setNome("ONG XPTO");
		instituicao.setDescricao("ONG visa algo.");
		instituicao.setTelefone("8399273464");
		instituicao.setDocumento("107.345.123-40");

		instituicao.setConta(conta1);
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua Maira Nunes");
		endereco.setBairro("Centro");
		endereco.setCep("58560-0000");
		endereco.setNumero("s/n");
		endereco.setLocalidade("Monteiro");
		endereco.setUf("PB");
		instituicao.setEndereco(endereco);
		instituicaoCaridadeService.save(instituicao);
	}

	/**
	 * cria uma campanha, deve retornar um status http 201
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createCampanhaOk() throws IOException, Exception {
		getCampanha();
		mockMvc.perform(post("/campanha").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoTESTE", "123456")).content(toJson(campanha)))
				.andExpect(status().isCreated());
	}

	/**
	 * atualiza uma campanha, deve retornar um status http 200
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void updateCampanhaOk() throws IOException, Exception {
		getCampanha();
		campanha.setNome("Outubro rosa");
		mockMvc.perform(put("/campanha").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoTESTE", "123456")).content(toJson(campanha)))
				.andExpect(status().isOk());
	}

	/**
	 * Tenta criar uma campanha nula, deve retornar um status http 400
	 * 
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
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCampanhasWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/campanha")).andExpect(status().isUnauthorized());

	}

	/**
	 * tenta atualizar campanha sem autorização, deve retornar status http 401
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void updateCampanhasWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/campanha")).andExpect(status().isUnauthorized());

	}

	/**
	 * pega todas as campanhas ja cadastradas retorna status http 200
	 * 
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
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getCampanhasByInstituicaoOk() throws IOException, Exception {
		mockMvc.perform(
				get("/campanha/filter/instituicao").header("Authorization", getAuth("instituicaoTESTE", "123456")))
				.andExpect(status().isOk());
	}

	/**
	 * Tenta buscar uma campanha pelo identificador sem está autenticado.
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findCampanhaByIdNotAuth() throws IOException, Exception {
		mockMvc.perform(get("/campanha/10")).andExpect(status().isUnauthorized());
	}

	/**
	 * recupera uma campanha pelo seu identificador, deve retornar status http
	 * 200
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findCampanhaIdOk() throws IOException, Exception {
		mockMvc.perform(get("/campanha/12").header("Authorization", getAuth("instituicaoTESTE", "123456")))
				.andExpect(status().isOk());
	}

	/**
	 * @throws ParseException
	 * 
	 * 
	 */
	private void getCampanha() throws ParseException {
		campanha = new Campanha();
		campanha.setNome("Natal sem fome");
		campanha.setDescricao("campanha para arrecadar alimentos para os moradores de rua");

		Categoria categoria = categoriaService.findAll().get(0);

		List<Meta> metas = new ArrayList<>();
		Meta meta = new Meta();
		meta.setCategoria(categoria);
		meta.setQuantidade(new BigDecimal(2000));
		meta.setUnidadeMedida(UnidadeMedida.UNIDADE);
		metas.add(meta);

		campanha.setMetas(metas);
		

		campanha.setInstituicaoCaridade(instituicaoCaridadeService.findAll().get(0));
	}
}
