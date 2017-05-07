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
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria.CriarCategoriaPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria.EditarCategoriaPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria.RemoverCategoriaPage;
/**
 * 
 * classe que contém os testes de categoria
 * @author elson
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoriaTest{
	
	private WebDriver driver;
	
	private CategoriaPage categoriaPage;
	
	@Before
	public void setUp(){
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
	
	@After
	public void tearDown() throws Exception {
		driver.close();
	}
	/**
	 * adiciona uma categoria
	 * @throws InterruptedException 
	 */
	@Test
	public void adicionarCategoria() throws InterruptedException{
		categoriaPage.visita();
		
		CriarCategoriaPage criarCategoriaPage = categoriaPage.novo();
		String nome = "utensílios";
		criarCategoriaPage.adicionarOuEditarCategoria(nome, "qualquer tipo de utensílio", true);
		Thread.sleep(2000);
		boolean categoriaCadastradaComSucesso = criarCategoriaPage.foiCadastradaComSucessoCategoria(nome);
		assertTrue("A categoria deveria ter sido cadastrada com sucesso",
				categoriaCadastradaComSucesso);
	}
	
	/**
	 * tenta adicionar uma categoria sem preencher o campo nome
	 *
	 *
	 */
	@Test
	public void adicionarCategoriaComCampoNomeNaoPreenchido(){
		categoriaPage.visita();
		
		CriarCategoriaPage criarCategoriaPage = categoriaPage.novo();
		
		criarCategoriaPage.adicionarOuEditarCategoria("", "qualquer tipo de utensílio", true);
		
		boolean houveErroCamposObgsNaoInformados = criarCategoriaPage.houveUmErroCampoObgNome();

		assertTrue("A operação devia ter exibido as mensagens informando que são campos obg.",
				houveErroCamposObgsNaoInformados);
	}
	/**
	 * tenta adicionar uma categoria sem preencher o campo descrição
	 */
	@Test
	public void adicionarCategoriaComCampoDescricaoNaoPreenchido(){
		categoriaPage.visita();
		
		CriarCategoriaPage criarCategoriaPage = categoriaPage.novo();
		
		criarCategoriaPage.adicionarOuEditarCategoria("utensílios", "", true);
		
		boolean houveErroCamposObgsNaoInformados = criarCategoriaPage.houveUmErroCampoObgDescricao();

		assertTrue("A operação devia ter exibido as mensagens informando que são campos obg.",
				houveErroCamposObgsNaoInformados);
	}
	/**
	 * tenta adicionar categoria sem informar os campos obrigatórios
	 */
	@Test
	public void adicionarCategoriaSemCamposObrigatoriosPreenchidos(){
		categoriaPage.visita();
		
		CriarCategoriaPage criarCategoriaPage = categoriaPage.novo();
		
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
	public void editarCategoria(){
		categoriaPage.visita();

		CriarCategoriaPage criarCategoriaPage = categoriaPage.novo();
		criarCategoriaPage.adicionarOuEditarCategoria("Informatica ", "informatica em geral", true);
		boolean foiCadastradoComSucesso = categoriaPage.foiCadastradaComSucessoCategoria("Informatica");

		EditarCategoriaPage editarCategoriaPage = categoriaPage.edit("Informatica");
		
		
		try {
			Thread.sleep(1000l);
			editarCategoriaPage.adicionarOuEditarCategoria(
					"INFORMATICA EDITADO", "INFORMATICA EM GERAL EDITADO", true);
			Thread.sleep(1000l);
			assertTrue("Uma mensagem informando que o campo estava inválido deveria ter sido mostrada",
					foiCadastradoComSucesso);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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

		CriarCategoriaPage criarCategoriaPage = categoriaPage.novo();
		criarCategoriaPage.adicionarOuEditarCategoria("Eletronicos ", "eletronicos em geral", true);
		boolean foiCadastradoComSucesso = categoriaPage.foiCadastradaComSucessoCategoria("Eletronicos");

		EditarCategoriaPage editarCategoriaPage = categoriaPage.edit("Eletronicos");
		
		try {
			Thread.sleep(1000l);
			editarCategoriaPage.desiteDeEditar();
			
			Thread.sleep(1000l);
			assertTrue("não era para editar a categoria", foiCadastradoComSucesso);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	/**
	 * tenta excluir uma categoria e desiste
	 * 
	 */
	@Test
	public void excluirCategoriaEDesiste(){
		categoriaPage.visita();
		
		CriarCategoriaPage criarCategoria = categoriaPage.novo();
		
		criarCategoria.adicionarOuEditarCategoria("Roupas", "tipos de roupas", true);
		
		boolean foiCadastradoComSucesso = categoriaPage.foiCadastradaComSucessoCategoria("Roupas");
		
		
		RemoverCategoriaPage removerCategoriaPage = categoriaPage.tentaRemoverCategoriaEDesiste();
		try {
			Thread.sleep(1000l);
			
			removerCategoriaPage.desistirDaRemocao();
			
			Thread.sleep(1000l);
			assertTrue("não deveria ter removido a categoria", foiCadastradoComSucesso);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove uma categoria
	 */
	@Test
	public void removerCategoria(){
		categoriaPage.visita();
		
		CriarCategoriaPage criarCategoria = categoriaPage.novo();
		
		criarCategoria.adicionarOuEditarCategoria("zefa", "categoria da zefa", true);
		
		boolean foiCadastradoComSucesso = categoriaPage.foiCadastradaComSucessoCategoria("zefa");
		
		
		RemoverCategoriaPage removerCategoriaPage = categoriaPage.remove();
		
		removerCategoriaPage.remover();
		
		boolean categoriaRemovidaComSucesso = categoriaPage.categoriaRemovidaComSucesso("zefa");
		
		
		assertTrue("Uma mensagem informando que o campo estava inválido deveria ter sido mostrada",
				foiCadastradoComSucesso && categoriaRemovidaComSucesso);
	}
	
	
	

}
