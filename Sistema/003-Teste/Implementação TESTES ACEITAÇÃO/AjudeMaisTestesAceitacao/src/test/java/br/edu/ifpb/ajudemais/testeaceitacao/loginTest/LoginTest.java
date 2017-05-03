
package br.edu.ifpb.ajudemais.testeaceitacao.loginTest;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.codeborne.selenide.WebDriverRunner;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.login.LoginPage;

/**
 * 
 * <p>
 * <b> LoginTest.java </b>
 * </p>
 *
 * <p>
 * Suíte de testes de aceitação login.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class LoginTest {
	
	private WebDriver driver;

	private LoginPage loginPage;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));

		driver = new ChromeDriver(capabilities);

		driver.manage().window().maximize();
		System.setProperty("selenide.browser", "Chrome");
		loginPage = new LoginPage(driver);

		WebDriverRunner.setWebDriver(driver);
	}
	
	@After
	public void tearDown() throws Exception {
		driver.close();
	}
	
	/**
	 * Faz login sem preencher dados
	 * @throws InterruptedException 
	 */
	@Test
	public void fazerLoginSemDados() throws InterruptedException{
		loginPage.fazerLogin("", "");
		
		boolean houveCampoObgUsername = loginPage.houveErroUsername("Nome do Usuário deve ser informado");
		boolean houveCampoObgSenha = loginPage.houveErroSenha("Senha deve ser informada");
		
		assertTrue("A operação devia ter exibido as mensagens informando que são campos obg.",
				houveCampoObgSenha && houveCampoObgUsername);
	}
	
	/**
	 * Faz login sem preencher username
	 * @throws InterruptedException 
	 */
	@Test
	public void fazerLoginSemUsername() throws InterruptedException{
		loginPage.fazerLogin("dedede", "");
		
		boolean houveCampoObgUsername = loginPage.houveErroUsername("Nome do Usuário deve ser informado");
		
		assertTrue("A operação devia ter exibido as mensagens informando que o username é obg.",!houveCampoObgUsername);
	}
	
	/**
	 * Faz login sem preencher campo senha
	 * @throws InterruptedException 
	 */
	@Test
	public void fazerLoginSemSenha() throws InterruptedException{
		loginPage.fazerLogin("", "dedede");
		
		boolean houveCampoObgSenha = loginPage.houveErroSenha("Senha deve ser informada");
		
		assertTrue("A operação devia ter exibido as mensagens informando que a senha é obg",!houveCampoObgSenha);
	}

	/**
	 * Faz login com login invalido
	 * @throws InterruptedException 
	 */
	@Test
	public void fazerLoginDadosInvalidos() throws InterruptedException{
		loginPage.fazerLogin("teste123", "teserw");
		
		loginPage.houveErroSenha("Senha deve ser informada");
		
        Thread.sleep(100);

		boolean houveLoginInvalido = loginPage.houveLoginInvalido("Nome de usuário ou senha inválido");
		
		assertTrue("A operação devia ter exibido as mensagens que login é inválido", houveLoginInvalido);
	}

}
