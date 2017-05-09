/**
 * <p>
 * Ajude Mais - Módulo Web Service
 * </p>
 * 
 * <p>
 * Sistema para potencializar o processo de doação.
 * </p>
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
public class InstituicaoCaridadeRestTest extends AbstractRestTest {

	/**
	 * 
	 */
	private InstituicaoCaridade instituicao;

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

		final Conta contaInst = new Conta();
		contaInst.setUsername("instituicaoXPTO");
		contaInst.setSenha("myinst");
		contaInst.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		contaInst.setEmail("inst@gmail.com");
		contaInst.setAtivo(true);
		contaService.save(contaInst);
	}

	/**
	 * 
	 * <p>
	 * Executa teste de criação de instituição para sucesso.
	 * 
	 * </p>
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoOk() throws IOException, Exception {
		getInstituicao();

		mockMvc.perform(post("/instituicao").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCoopper", "bazinga")).content(toJson(instituicao)))
				.andExpect(status().isCreated());
	}

	/**
	 * 
	 * <p>
	 * Executa teste de criação de instituição para um caso de falha. Tenta
	 * salvar uma insituições com valor nulo.
	 * </p>
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoNull() throws IOException, Exception {
		mockMvc.perform(post("/instituicao").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCoopper", "bazinga")).content(toJson(instituicao)))
				.andExpect(status().isBadRequest());
	}

	/**
	 * 
	 * <p>
	 * Executa teste de criação de instituição para um caso de falha. Tenta
	 * salvar uma insituições com nome nulo.
	 * </p>
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoNomeNull() throws IOException, Exception {
		getInstituicao();
		instituicao.setNome(null);

		mockMvc.perform(post("/instituicao").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCoopper", "bazinga")).content(toJson(instituicao)))
				.andExpect(status().isUnprocessableEntity());
	}

	/**
	 * <p>
	 * Executa teste de criação de instituição para um caso de falha. Tenta
	 * salvar uma insituições com descriçao nula.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoDescricaoNull() throws IOException, Exception {
		getInstituicao();
		instituicao.setDescricao(null);

		mockMvc.perform(post("/instituicao").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCoopper", "bazinga")).content(toJson(instituicao)))
				.andExpect(status().isUnprocessableEntity());
	}

	/**
	 * <p>
	 * Executa teste de criação de instituição para um caso de falha. Tenta
	 * salvar uma insituições com endereço nulo.
	 * </p>
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void createInstituicaoEnderecoNull() throws IOException, Exception {
		getInstituicao();
		instituicao.setEndereco(null);

		mockMvc.perform(post("/instituicao").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCoopper", "bazinga")).content(toJson(instituicao)))
				.andExpect(status().isUnprocessableEntity());
	}

	/**
	 * <p>
	 * Executa teste de criação de instituição para um caso de falha. Tenta
	 * salvar uma insituições com conta nula.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoContaNull() throws IOException, Exception {
		getInstituicao();
		instituicao.setConta(null);

		mockMvc.perform(post("/instituicao").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCoopper", "bazinga")).content(toJson(instituicao)))
				.andExpect(status().isUnprocessableEntity());
	}

	/**
	 * <p>
	 * Executa teste de criação de instituição para um caso de falha. Tenta
	 * salvar uma insituições com documento nulo.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createInstituicaoDocumentoNull() throws IOException, Exception {
		getInstituicao();
		instituicao.setDocumento(null);

		mockMvc.perform(post("/instituicao").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("sheldonCoopper", "bazinga")).content(toJson(instituicao)))
				.andExpect(status().isUnprocessableEntity());
	}

	/**
	 * <p>
	 *Tenta obter insituições sem autenticação.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getInstituicoesWithoutAuth() throws IOException, Exception {
		mockMvc.perform(get("/instituicao")).andExpect(status().isUnauthorized());

	}

	/**
	 * <p>
	 * Tenta obter intituições com usuário que não possui autorização para acessar
	 * endpoint.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void getInstituicoesNotAuthorization() throws IOException, Exception {
		mockMvc.perform(get("/instituicao").header("Authorization", getAuth("instituicaoXPTO", "myinst")))
				.andExpect(status().isForbidden());

	}

	/**
	 * <p>
	 * 
	 * Executa teste para get em insituições para caso de sucesso.
	 * 
	 * </p>
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Test
	public void getInstituicoesOk() throws IOException, Exception {
		mockMvc.perform(get("/instituicao").header("Authorization", getAuth("sheldonCoopper", "bazinga")))
				.andExpect(status().isOk());
	}

	/**
	 * <p>
	 * Executa teste para busca em instituições com sucesso.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findInstituicaoByIdOk() throws IOException, Exception {
		mockMvc.perform(get("/instituicao/100").header("Authorization", getAuth("instituicaoXPTO", "myinst")))
				.andExpect(status().isOk());
	}

	/**
	 * <p>
	 * Executa teste falho para busca de instituições por id sem autorização
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findInstituicaoByIdNotAuth() throws IOException, Exception {
		mockMvc.perform(get("/instituicao/100")).andExpect(status().isUnauthorized());
	}

	/**
	 * Cria um endereço com suas propriedades
	 * 
	 * @return
	 * 
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
	 * <p>
	 * Cria instituição base para ser utilizada nos testes.
	 * 
	 * </p>
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
