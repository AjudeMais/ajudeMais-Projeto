package br.edu.ifpb.ajudemais.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;
/**
 * Classe que testa os end points do mensageiro
 * @author elson
 *
 */
public class MensageiroRestTest extends AbstractRestTest {

	private Mensageiro mensageiro;

	@Autowired
	private ContaService contaService;

	@Override
	protected void doInit() throws Exception {

		final Conta contaInst = new Conta();
		contaInst.setUsername("instituicaoZEFA");
		contaInst.setSenha("instituicaozefa");
		contaInst.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		contaInst.setEmail("instituicaozefa@gmail.com");
		contaInst.setAtivo(true);
		contaService.save(contaInst);
	}
	/**
	 * testa a criação de um mensageiro através de um administrador, havendo sucesso retorna http 201
	 * @throws IOException
	 * @throws Exception
	 */
	
	@Test
	public void criarMensageiroOk() throws IOException, Exception {

		getMensageiro();
		mockMvc.perform(post("/mensageiro").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoZEFA", "instituicaozefa")).content(toJson(mensageiro)))
				.andExpect(status().isCreated());
	}
	/**
	 * deve retornar um status http 400
	 * @throws IOException
	 * @throws Exception
	 */
	
	@Test
	public void criarMensageironullBody() throws IOException, Exception {

		getMensageiro();
		mockMvc.perform(post("/mensageiro").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoZEFA", "instituicaozefa"))
				.content(toJson(null)))
				.andExpect(status().isBadRequest());

	}
	/**
	 * testa a criação de um mensageiro, sem autorização deve retornar status http 401
	 * @throws IOException
	 * @throws Exception
	 */
	
	@Test
	public void criarMensageiroSemAutorizacao() throws IOException, Exception {

		getMensageiro();
		mockMvc.perform(post("/mensageiro").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
	/**
	 * deve retornar um status http 401 não autorizado
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void buscarTodosMesnsageirosSemAutorizacao() throws IOException, Exception {

		getMensageiro();
		mockMvc.perform(get("/mensageiro").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());

	}
	/**
	 * retorna um http 200 caso haja sucesso na busca
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void buscarTodosMensageirosOk() throws IOException, Exception {

		getMensageiro();
		mockMvc.perform(get("/mensageiro").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoZEFA", "instituicaozefa"))
				.content(toJson(mensageiro)))
				.andExpect(status().isOk());

	}
	/**
	 * retorna um http 200 confirmando que a busca foi realizada
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void buscarMensageirosPorIdOk() throws IOException, Exception {

		getMensageiro();
		mockMvc.perform(get("/mensageiro").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", getAuth("instituicaoZEFA", "instituicaozefa"))
				.content(toJson(mensageiro.getId())))
				.andExpect(status().isOk());

	}
	
	/**
	 * metodo que cria um mensageiro para utilização dos testes
	 * 
	 * @return
	 */
	private Mensageiro getMensageiro() {
		mensageiro = new Mensageiro();
		mensageiro.setCpf("127.547.642-24");
		mensageiro.setEnderecos(getEndereco());
		mensageiro.setNome("MENSAGEIRO 1");
		mensageiro.setTelefone("83996885898");

		Conta conta = new Conta();
		conta.setAtivo(true);
		conta.setUsername("msg1");
		conta.setSenha("msg1");
		conta.setGrupos(Arrays.asList("ROLE_MENSAGEIRO"));
		conta.setEmail("msg1@gmail.com");
		mensageiro.setConta(conta);

		return mensageiro;
	}
	/**
	 * cria um endereço
	 * @return
	 */
	private List<Endereco> getEndereco() {
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua dos mensageiros");
		endereco.setBairro("Centro");
		endereco.setCep("58500-000");
		endereco.setNumero("s/n");
		endereco.setLocalidade("Monteiro");
		endereco.setUf("PB");
		List<Endereco> enderecos = new ArrayList<>();
		enderecos.add(endereco);
		return enderecos;

	}
}
