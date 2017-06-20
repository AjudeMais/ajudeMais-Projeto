package br.edu.ifpb.ajudemais.testeaceitacao.campanhaTest;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
/**
 * classe que contém os testes de aceitção para campanha
 * @author elson
 *
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.codeborne.selenide.WebDriverRunner;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.campanha.CampanhaPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.campanha.CreateCampanhaPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.campanha.DetalhesCampanhaPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.campanha.EditaCampanhaPage;
/**
 * Classe que realiza os testes de campanha
 * @author elson
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CampanhaTest {
	/**
	 * 
	 */
	private WebDriver driver;
	/**
	 * 
	 */
	private CampanhaPage campanhaPage;
	
	/**
	 * 
	 */
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));

		driver = new ChromeDriver(capabilities);

		driver.manage().window().maximize();
		System.setProperty("selenide.browser", "Chrome");
		campanhaPage = new CampanhaPage(driver);

		WebDriverRunner.setWebDriver(driver);
	}
	/**
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		driver.close();
	}
	
	
	/**
	 * adiciona uma campanha
	 * 
	 * @throws InterruptedException
	 * @throws ParseException 
	 */
	@Test
	public void adicionarCampanhaOk() throws InterruptedException, ParseException {
		campanhaPage.visita();

		CreateCampanhaPage criarCampanhaPage = campanhaPage.novo();
		String nome = "Campanha solidaria";
		
		criarCampanhaPage.adicionarOuEditarCampanha(nome, "uma campanha para arrecadar alimentos", "17/11/2017", true, "Leite", 2000d, "Litro");
		Thread.sleep(2000);
		
		boolean campanhaCadastradaComSucesso = campanhaPage.foiCadastradaComSucessoCampanha(nome);
		assertTrue("A campanha deveria ter sido cadastrada com sucesso", campanhaCadastradaComSucesso);
	}
	
	
	/**
	 * tenta adicionar uma campanha sem preencher o campo nome
	 * @throws ParseException 
	 *
	 */
	@Test
	public void adicionarCampanhaComCampoNomeNaoPreenchido() throws ParseException {
		campanhaPage.visita();

		CreateCampanhaPage criarCampanhaPage = campanhaPage.novo();
		
		campanhaPage.adicionarOuEditarCampanha("", "Campanha para arrecadar roupas", "25/10/2017", true, "Roupas", 300d, "Unidade");

		boolean houveErroCampoObgNaoInformado = criarCampanhaPage.houveUmErroCampoObgNome();

		assertTrue("A operação devia ter exibido as mensagens informando que são campos obg.",
				houveErroCampoObgNaoInformado);
	}
	/**
	 * tenta adicionar uma campanha sem preencher o campo descrição
	 * @throws ParseException 
	 *
	 */
	@Test
	public void adicionarCampanhaComCampoDescricaoNaoPreenchido() throws ParseException {
		campanhaPage.visita();

		CreateCampanhaPage criarCampanhaPage = campanhaPage.novo();
		
		criarCampanhaPage.adicionarOuEditarCampanha("Campanha Solidária", "", "25/11/2017", true, "Leite", 5000d, "Litro");

		boolean houveErroCampoObgNaoInformado = criarCampanhaPage.houveUmErroCampoObgDescricao();

		assertTrue("A operação devia ter exibido as mensagens informando que são campos obg.",
				houveErroCampoObgNaoInformado);
	}
	
	/**
	 * tenta adicionar uma campanha sem preencher o campo Termino
	 * @throws ParseException 
	 *
	 */
	@Test
	public void adicionarCampanhaComCampoTerminoNaoPreenchido() throws ParseException {
		campanhaPage.visita();

		CreateCampanhaPage criarCampanhaPage = campanhaPage.novo();
		
		criarCampanhaPage.adicionarOuEditarCampanha("Campanha Solidária", "Uma campanha para os desabrigados", "", true, "Alimentos", 600d, "Quilograma");

		boolean houveErroCampoObgNaoInformado = criarCampanhaPage.houveUmErroCampoObgTermino();

		assertTrue("A operação devia ter exibido as mensagens informando que são campos obg.",
				houveErroCampoObgNaoInformado);
	}
	
	/**
	 * tenta adicionar uma campanha sem preencher todos os campos obrigatórios.
	 *
	 */
	@Test
	public void adicionarCampanhaComCamposNaoPreenchidos() {
		campanhaPage.visita();

		CreateCampanhaPage criarCampanhaPage = campanhaPage.novo();
		
		criarCampanhaPage.adicionarOuEditarCampanha("", "", "", true, "Leite", 0d, "Selecione");

		boolean houveErroCamposObgNaoInformados = criarCampanhaPage.houveUmErroTodosCamposObgs();

		assertTrue("A operação devia ter exibido as mensagens informando que são campos obg.",
				houveErroCamposObgNaoInformados);
	}
	
	/**
	 * edita uma campanha
	 * 
	 */
	@Test
	public void editarCampanha() {
		campanhaPage.visita();

		try {
			CreateCampanhaPage criarCampanhaPage = campanhaPage.novo();
			criarCampanhaPage.adicionarOuEditarCampanha("Campaha Teste", "Leva cobertores aos necessitados", "22/09/2017", true, "Cobertores", 1200d, "Unidade");
			Thread.sleep(1000l);
			boolean foiCadastradoComSucesso = campanhaPage.foiCadastradaComSucessoCampanha("Campanha solidaria");
			Thread.sleep(1000l);
			EditaCampanhaPage editarCampanhaPage = campanhaPage.edit("Campanha Teste");
			Thread.sleep(1000l);
			editarCampanhaPage.editarCampanha("Natal feliz editada", "Descrição da campanha", "25/11/2017");
			boolean foiEditadoComSucesso = campanhaPage.foiEditadoComSucessoCampanha("Natal feliz editada");
			Thread.sleep(1000l);
			assertTrue("Uma mensagem informando que o campo estava inválido deveria ter sido mostrada",foiCadastradoComSucesso && foiEditadoComSucesso);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * tenta editar uma campanha e desiste
	 * 
	 */
	@Test
	public void DesisteDeEditarCampanha() {
		campanhaPage.visita();

		CreateCampanhaPage criarCampanhaPage = campanhaPage.novo();
		criarCampanhaPage.adicionarOuEditarCampanha("Doe vida, doe sangue", "doação de sangue", "23/09/2017", true, "Sangue", 200d, "Unidade");
		boolean foiCadastradoComSucesso = campanhaPage.foiCadastradaComSucessoCampanha("Doe vida, doe sangue");

		try {
			Thread.sleep(1000l);
			
			EditaCampanhaPage editarCampanhaPage = campanhaPage.edit("Doe vida, doe sangue");
			editarCampanhaPage.desisteDeEditar();
			Thread.sleep(1000l);

			assertTrue("Uma mensagem informando que o campo estava inválido deveria ter sido mostrada",
					foiCadastradoComSucesso);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * testa a verificação dos detalhes de uma campanha.
	 */
	@Test
	public void verDetalheCampanha(){
		campanhaPage.visita();
		
		CreateCampanhaPage criarCampanhaPage = campanhaPage.novo();
		criarCampanhaPage.adicionarOuEditarCampanha("Natal feliz", "Leva carinho aos necessitados", "25/12/2017", true, "Roupas", 2000d, "Unidade");
		boolean foiCadastradoComSucesso = campanhaPage.foiCadastradaComSucessoCampanha("Natal feliz");
		
		DetalhesCampanhaPage detalhe = campanhaPage.detail("Natal feliz");
		detalhe.detail("Natal feliz");
		assertTrue("Uma mensagem informando que o campo estava inválido deveria ter sido mostrada",
				foiCadastradoComSucesso);

		
	}
	/**
	 * teste que busca uma campanha por nome.
	 */
	@Test
	public void buscarCampanhaPorNome(){
		campanhaPage.visita();
		String nome = "Campanha solidária";
		campanhaPage.pesquisarCampanhaPorNome(nome);
	}
	/**
	 * teste que busca uma campanha pela data de termino.
	 */
	@Test
	public void buscarCampanhaPorDataTermino(){
		campanhaPage.visita();
		String data = "25/12/2017";
		campanhaPage.pesquisarCampanhaPorNome(data);
	}

}
