package br.edu.ifpb.ajudemais.testeaceitacao.mensageiroTest;

import static org.junit.Assert.*;

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

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.mensageiro.AssociarMensageiroPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.mensageiro.EditarMensageiroPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.mensageiro.MensageiroPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.mensageiro.PesquisarMensageiroPage;

/**
 * Classe para realização dos testes de mensageiro
 * @author elson
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MensageiroTest {
	/**
	 * 
	 */
	private WebDriver driver;
	/**
	 * 
	 */
	private MensageiroPage mensageiroPage;

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
		mensageiroPage = new MensageiroPage(driver);

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
	 * classe para testar uma associação de um mensageiro a uma instituição
	 * 
	 */
	@Test
	public void associarMensageiro(){
		mensageiroPage.visita();

		AssociarMensageiroPage associar = mensageiroPage.novo();
		String email = "mensageiroTeste@gmail.com";
		associar.associarMensageiro(email);
		
		boolean mensageiroAssociadoComSucesso = mensageiroPage.foiAssociadoMensageiroComSucesso(email);
		assertTrue("O mensageiro deveria ter sido associado com sucesso", mensageiroAssociadoComSucesso);

	}
	/**
	 * testa associar um mensageiro ja associado a mesma instituição
	 */
	@Test
	public void associarMensageiroJaAssociado() {
		mensageiroPage.visita();

		AssociarMensageiroPage associar = mensageiroPage.novo();
		String email = "mensageiroTESTE@gmail.com";
		associar.associarMensageiro(email);

		boolean mensageiroNaoAssociado = mensageiroPage.erroAssociarMensageiroIgual();
		assertTrue("Deveria ter exibido mensagem de erro", mensageiroNaoAssociado);
	}
	/**
	 * testa que edita um mensageiro deixando ele ativo ou inativo
	 */
	@Test
	public void editarMensageiroJaAssociado() {
		mensageiroPage.visita();
		
		String email = "mensageiroTeste@gmail.com";
		EditarMensageiroPage editar = mensageiroPage.edit(email);		
		editar.editarMensageiro();

		boolean mensageiroEditado = mensageiroPage.foiEditadoMensageiroComSucesso(email);
		assertTrue("Deveria ter atualizado o mensageiro associado", mensageiroEditado);
	}
	/**
	 * teste que tenta editar um mensageiro, mas desiste;
	 */
	@Test
	public void desisteDeEditarMensageiro(){
		mensageiroPage.visita();
		
		String email = "mensageiroTeste@gmail.com";
		EditarMensageiroPage editar = mensageiroPage.edit(email);
		editar.desistirDeEditarMensageiro();
	}
	/**
	 * testa a pesquisa pelo atributo nome de um mensageiro associado
	 */
	@Test
	public void pesquisarMensageiroPorNome(){
		mensageiroPage.visita();
		
		String nome = "Fulano Costa";
		PesquisarMensageiroPage pmp = mensageiroPage.search(nome);
		pmp.pesquisarMensageiro(nome);
		
	}
	/**
	 * testa a pesquisa pelo atributo email de um mensageiro associado
	 */
	@Test
	public void pesquisarMensageiroPorEmail(){
		mensageiroPage.visita();
		
		String email = "mensageiroTeste@gmail.com";
		PesquisarMensageiroPage pmp = mensageiroPage.search(email);
		pmp.pesquisarMensageiro(email);
		
	}
	
	/**
	 * testa a associação de um mensageiro e desiste;
	 */
	@Test
	public void tentaAssociarMensageiroEDesiste(){
		mensageiroPage.visita();

		AssociarMensageiroPage associar = mensageiroPage.novo();
		String email = "mensageiro123@gmail.com";
		associar.desisteDeAssociarMensageiro(email);

	}
	

}
