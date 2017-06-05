package br.edu.ifpb.ajudemais.testeaceitacao.doacaoTest;

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

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.doacao.DoacaoPage;

/**
 * 
 * <p>
 * AcompahamentoDoacaoTest
 * </p>
 * 
 * <p>
 * Classe utilizada para execução de testes de aceitação relacionados ao
 * acompanhamento de doações.
 * </p>
 *
 * <pre>
 * </pre
 *
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AcompanhamentoDoacaoTest {

	private WebDriver driver;

	private DoacaoPage doacaoPage;

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
		doacaoPage = new DoacaoPage(driver);

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
	 * 
	 */
	@Test
	public void visita() {
		doacaoPage.visita();
		assertTrue(doacaoPage.visualizaPaginaPesquisa());
	}

	/**
	 * 
	 */
	@Test
	public void visualizaDetalhes() {
		doacaoPage.visita();

		assertTrue("Deveria ter mostrado os detalhes", doacaoPage.detail().validatePage());
	}

}
