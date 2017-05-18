package br.edu.ifpb.ajudemais.api.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import br.edu.ifpb.ajudeMais.domain.entity.Imagem;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;
/**
 * Classe que testa os end points do mensageiro
 * @author elson
 *
 */
public class MensageiroRestTest extends AbstractRestTest {
	/**
	 * 
	 */
	private Mensageiro mensageiro;
	/**
	 * 
	 */
	@Autowired
	private ContaService contaService;
	/**
	 * 
	 */
	@Override
	protected void doInit() throws Exception {
		
		final Conta contaInst = new Conta();
		contaInst.setUsername("zefinha");
		contaInst.setSenha("zefinha");
		contaInst.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		contaInst.setEmail("zefinha@gmail.com");
		contaInst.setAtivo(true);
		contaService.save(contaInst);
	}
	/**
	 * testa a criação de um mensageiro, havendo sucesso retorna http 201
	 * @throws IOException
	 * @throws Exception
	 */
	
	@Test
	public void createMensageiroOk() throws IOException, Exception {
		
		getMensageiro();
		mockMvc.perform(post("/mensageiro").contentType(MediaType.APPLICATION_JSON).content(toJson(mensageiro)))		
		.andExpect(status().isCreated());
	}
	/**
	 * tenta criar um mensageiro com body nulo, deve retornar um status http 400
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void createMensageiroNullBody() throws IOException, Exception {
		mockMvc.perform(post("/mensageiro").contentType(MediaType.APPLICATION_JSON).content(toJson(null)))
		.andExpect(status().isBadRequest());
	}
	/**
	 * tenta buscar 'sem autorização' todos os mensageiros, deve retornar um status http 401
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findAllMensageirowithoutAuth() throws IOException, Exception {
		
		mockMvc.perform(get("/mensageiro")).andExpect(status().isUnauthorized());
	}
	/**
	 * tenta recuperar todos os mensageiros, em caso de sucesso retorna status http 200
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findAllMensageiroOk() throws IOException, Exception {
		mockMvc.perform(get("/mensageiro").contentType(MediaType.APPLICATION_JSON)
		.header("Authorization", getAuth("zefinha", "zefinha")).content(toJson(mensageiro)))
		.andExpect(status().isOk());
		
	}
	/**
	 * tenta recuperar um mensageiro através do seu identificador, retorna o status http 200
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void findMensageiroByIdllOk() throws IOException, Exception {
		mockMvc.perform(get("/mensageiro/200").contentType(MediaType.APPLICATION_JSON)
		.header("Authorization", getAuth("zefinha", "zefinha")).content(toJson(mensageiro)))
		.andExpect(status().isOk());
		
	}
	/**
	 * tenta atualizar, sem autorização, um mensageiro. retorna um status http 401
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void updateMensageiroWithoutAuth() throws IOException, Exception {
		getMensageiro();
		mockMvc.perform(put("/mensageiro").contentType(MediaType.APPLICATION_JSON).content(toJson(mensageiro)))
		.andExpect(status().isUnauthorized());
		
	}

	/**
	 * metodo que cria um mensageiro para utilização dos testes
	 * 
	 * @return
	 */
	private Mensageiro getMensageiro() {
		mensageiro = new Mensageiro();
		mensageiro.setNome("MENSAGEIRO 1");
		mensageiro.setCpf("127.547.642-24");
		mensageiro.setEnderecos(getEndereco());
		mensageiro.setTelefone("83996885898");
		
		Imagem imagem = new Imagem();				
		mensageiro.setFoto(imagem);

		Conta conta = new Conta();
		conta.setUsername("mensageiro");
		conta.setSenha("mensageiro");
		conta.setGrupos(Arrays.asList("ROLE_MENSAGEIRO"));
		conta.setEmail("msg1@gmail.com");
		conta.setAtivo(true);
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
