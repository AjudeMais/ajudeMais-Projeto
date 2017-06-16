package br.edu.ifpb.ajudemais.testeaceitacao.categoriaTest;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.codeborne.selenide.WebDriverRunner;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria.CategoriaPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria.CreateCategoriaPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria.EditarCategoriaPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria.RemoverCategoriaPage;

/**
 * 
 * classe que contém os testes de aceitção para categoria
 * 
 * @author elson
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoriaTest {

	private WebDriver driver;

	private CategoriaPage categoriaPage;

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
		categoriaPage = new CategoriaPage(driver);

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
	 * adiciona uma categoria
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void adicionarCategoria() throws InterruptedException {
		categoriaPage.visita();

		CreateCategoriaPage criarCategoriaPage = categoriaPage.novo();
		String nome = "utensílios";
		criarCategoriaPage.adicionarOuEditarCategoria(nome, "qualquer tipo de utensílio", true);
		Thread.sleep(2000);
		boolean categoriaCadastradaComSucesso = criarCategoriaPage.foiCadastradaComSucessoCategoria(nome);
		assertTrue("A categoria deveria ter sido cadastrada com sucesso", categoriaCadastradaComSucesso);
	}

	/**
	 * tenta adicionar uma categoria sem preencher o campo nome
	 *
	 */
	@Test
	public void adicionarCategoriaComCampoNomeNaoPreenchido() {
		categoriaPage.visita();

		CreateCategoriaPage criarCategoriaPage = categoriaPage.novo();

		criarCategoriaPage.adicionarOuEditarCategoria("", "qualquer tipo de utensílio", true);

		boolean houveErroCamposObgsNaoInformados = criarCategoriaPage.houveUmErroCampoObgNome();

		assertTrue("A operação devia ter exibido as mensagens informando que são campos obg.",
				houveErroCamposObgsNaoInformados);
	}

	/**
	 * tenta adicionar uma categoria sem preencher o campo descrição
	 */
	@Test
	public void adicionarCategoriaComCampoDescricaoNaoPreenchido() {
		categoriaPage.visita();

		CreateCategoriaPage criarCategoriaPage = categoriaPage.novo();

		criarCategoriaPage.adicionarOuEditarCategoria("utensílios", "", true);

		boolean houveErroCamposObgsNaoInformados = criarCategoriaPage.houveUmErroCampoObgDescricao();

		assertTrue("A operação devia ter exibido as mensagens informando que são campos obg.",
				houveErroCamposObgsNaoInformados);
	}

	/**
	 * tenta adicionar categoria sem informar os campos obrigatórios
	 */
	@Test
	public void adicionarCategoriaSemCamposObrigatoriosPreenchidos() {
		categoriaPage.visita();

		CreateCategoriaPage criarCategoriaPage = categoriaPage.novo();

		criarCategoriaPage.adicionarOuEditarCategoria("", "", true);

		boolean houveErroCamposObgsNaoInformados = criarCategoriaPage.houveUmErroTodosCamposObgs();

		assertTrue("A operação devia ter exibido as mensagens informando que são campos obg.",
				houveErroCamposObgsNaoInformados);
	}

	/**
	 * edita uma categoria
	 * 
	 */
	@Test
	public void editarCategoria() {
		categoriaPage.visita();

		CreateCategoriaPage criarCategoriaPage = categoriaPage.novo();

		try {
			criarCategoriaPage.adicionarOuEditarCategoria("Informatica ", "informatica em geral", true);
			boolean foiCadastradoComSucesso = categoriaPage.foiCadastradaComSucessoCategoria("Informatica");
			Thread.sleep(700l);
			EditarCategoriaPage editarCategoriaPage = categoriaPage.edit("Informatica");
			editarCategoriaPage.adicionarOuEditarCategoria("TESTE EDIT", "DESC EDIT", true);
			Thread.sleep(1000l);
			boolean foiEditadoComSucesso = categoriaPage.foiEditadoComSucessoCategoria("TESTE EDIT", "DESC EDIT");
			Thread.sleep(1000l);
			assertTrue("Uma mensagem deveria ter sido mostrada",foiCadastradoComSucesso && foiEditadoComSucesso);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * tenta editar uma categoria e desiste
	 * 
	 */
	@Test
	public void editarCategoriaEDesiste() {
		categoriaPage.visita();

		CreateCategoriaPage criarCategoriaPage = categoriaPage.novo();
		criarCategoriaPage.adicionarOuEditarCategoria("Eletronicos ", "eletronicos em geral", true);


		try {
			EditarCategoriaPage editarCategoriaPage = categoriaPage.edit("Eletronicos");
			Thread.sleep(1000l);
			editarCategoriaPage.desiteDeEditar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * tenta excluir uma categoria e desiste
	 * 
	 */
	@Test
	public void excluirCategoriaEDesiste() {
		categoriaPage.visita();

		CreateCategoriaPage criarCategoria = categoriaPage.novo();

		criarCategoria.adicionarOuEditarCategoria("Fraldas", "tipos de Fraldas", true);

		RemoverCategoriaPage removerCategoriaPage = categoriaPage.remove("Fraldas");
		try {
			Thread.sleep(1000l);

			removerCategoriaPage.desistirDaRemocao();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Remove uma categoria
	 */
	@Test
	public void removerCategoria() {
		categoriaPage.visita();

		CreateCategoriaPage criarCategoria = categoriaPage.novo();

		criarCategoria.adicionarOuEditarCategoria("Calçados", "categoria de Calçados", true);

		try {
			Thread.sleep(1000l);
			boolean foiCadastradoComSucesso = categoriaPage.foiCadastradaComSucessoCategoria("Calçados");

			RemoverCategoriaPage removerCategoriaPage = categoriaPage.remove("Calçados");

			removerCategoriaPage.remover();
			Thread.sleep(1000l);
			boolean categoriaRemovidaComSucesso = categoriaPage.categoriaRemovidaComSucesso("Calçados");

		
			assertTrue("deveria t sido cadastrada",	foiCadastradoComSucesso);

			assertTrue("deveria t sido removida",	categoriaRemovidaComSucesso);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
