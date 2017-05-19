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
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.api.rest.MensageiroAssociadoRestService;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.Imagem;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * 
 * <p>
 * {@link MensageiroAssociadoRestServiceTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para realização de testes de integração relacionados a
 * {@link MensageiroAssociadoRestService}.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class MensageiroAssociadoRestServiceTest extends AbstractRestTest {

	/**
	 * 
	 */
	private MensageiroAssociado mensageiroAssociado;

	/**
	 * 
	 */
	@Autowired
	private ContaService contaService;

	/**
	 * Método é executado apenas uma vez durante a execução dos testes.
	 */
	@Override
	protected void doInit() throws Exception {

		final Conta contaInst = new Conta();
		contaInst.setUsername("instituicao");
		contaInst.setSenha("instituicao");
		contaInst.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		contaInst.setEmail("instituicao@gmail.com");
		contaInst.setAtivo(true);
		contaService.save(contaInst);

	}

	/**
	 * 
	 * <p>
	 * teste para criação de uma associação com corpo da requisição invalido
	 * (Mensageiro e instituição inexistentes). Deveria retornar 400 (Bad
	 * Request).
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createAssociacaoWithBodyInvalid() throws IOException, Exception {

		getMensageiroAssociado();
		mockMvc.perform(post("/associacao").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicao", "instituicao")).content(toJson(mensageiroAssociado)))
				.andExpect(status().isBadRequest());
	}

	/**
	 * 
	 * <p>
	 * Teste para endpoint de criação de uma associação sem autenticação. Deveria
	 * retornar http 401.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createAssociacaoWithoutAuth() throws IOException, Exception {

		getMensageiroAssociado();
		mockMvc.perform(
				post("/associacao").contentType(MediaType.APPLICATION_JSON).content(toJson(mensageiroAssociado)))
				.andExpect(status().isUnauthorized());
	}
	
	/**
	 * 
	 * <p>
	 * Teste para PUT em /associacao sem autenticação. Deveria
	 * retornar http 401.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void updateAssociacaoWithoutAuth() throws IOException, Exception {

		getMensageiroAssociado();
		mockMvc.perform(
				put("/associacao").contentType(MediaType.APPLICATION_JSON).content(toJson(mensageiroAssociado)))
				.andExpect(status().isUnauthorized());
	}

	/**
	 * 
	 * <p>
	 * Teste para endpoint de /associacao/filter/insituicao sem autenticação.
	 * Deveria retornar http 401.
	 * </p>
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findAssociacaoByInstituicaoWithouAuth() throws IOException, Exception {

		getMensageiroAssociado();
		mockMvc.perform(get("/associacao/filter/instituicao").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(mensageiroAssociado))).andExpect(status().isUnauthorized());
	}

	/**
	 * 
	 * <p>
	 * cria instancias necessárias para execução dos testes.
	 * </p>
	 * 
	 * @return
	 */
	private MensageiroAssociado getMensageiroAssociado() {

		MensageiroAssociado mensageiroAssociado = new MensageiroAssociado();

		mensageiroAssociado.setData(new Date());
		mensageiroAssociado.setStatus(true);
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua Maira Nunes");
		endereco.setBairro("Centro");
		endereco.setCep("58560-0000");
		endereco.setNumero("s/n");
		endereco.setLocalidade("Monteiro");
		endereco.setUf("PB");

		Mensageiro mensageiro = new Mensageiro();
		mensageiro.setCpf("127.547.642-24");
		mensageiro.setEnderecos(Arrays.asList(endereco));
		mensageiro.setNome("MENSAGEIRO 1");
		mensageiro.setTelefone("83996885898");

		Imagem imagem = new Imagem();
		mensageiro.setFoto(imagem);

		Conta contaM = new Conta();
		contaM.setAtivo(true);
		contaM.setUsername("msg1");
		contaM.setSenha("msg1");
		contaM.setGrupos(Arrays.asList("ROLE_MENSAGEIRO"));
		contaM.setEmail("msg1@gmail.com");
		mensageiro.setConta(contaM);

		mensageiroAssociado.setInstituicaoCaridade(getInstituicao());
		mensageiroAssociado.setMensageiro(mensageiro);

		return mensageiroAssociado;
	}

	/**
	 * <p>
	 * Cria instituição base para ser utilizada nos testes.
	 * 
	 * </p>
	 */
	private InstituicaoCaridade getInstituicao() {
		InstituicaoCaridade instituicao = new InstituicaoCaridade();
		instituicao.setNome("ONG XPTO");
		instituicao.setDescricao("ONG visa algo.");
		instituicao.setTelefone("8399273464");
		instituicao.setDocumento("107.345.123-40");

		Conta conta = new Conta();
		conta.setUsername("rajesh");
		conta.setSenha("euFaloComMulher");
		conta.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		conta.setEmail("raj@gmail.com");

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
