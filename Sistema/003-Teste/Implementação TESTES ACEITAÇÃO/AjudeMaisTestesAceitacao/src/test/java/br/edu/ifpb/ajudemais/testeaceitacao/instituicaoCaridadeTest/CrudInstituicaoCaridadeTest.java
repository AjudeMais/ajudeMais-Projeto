
package br.edu.ifpb.ajudemais.testeaceitacao.instituicaoCaridadeTest;


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

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.InstituicaoCaridadePage;

/**
 * 
 * <p>
 * <b> CrudInstituicaoCaridadeTest.java </b>
 * </p>
 * <p>
 * Casos de teste de aceitação para instituição de caridade. 
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class CrudInstituicaoCaridadeTest {

	private WebDriver driver;

	private InstituicaoCaridadePage instituicaoCaridadePage;

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
		instituicaoCaridadePage = new InstituicaoCaridadePage(driver);

		WebDriverRunner.setWebDriver(driver);
		

	}

	@After
	public void tearDown() throws Exception {
		driver.close();
	}
	
	/**
	 * Adiciona uma instituição com todos os campos obrigatórios preenchidos com cpf.
	 */
	@Test
	public void adicionarInstituicaoCaridadeComCpf() {
		instituicaoCaridadePage.visita();
		instituicaoCaridadePage.novo().addInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES", "823.862.380-40", "(83) 99812-2196", "zefao2000@teste.com", "58500-000", "Rua Sete de setembro", "123", "Centro", "casa");
		boolean instituicaoCadastradaComSucesso = instituicaoCaridadePage.foiCadastradoComSucessoInstituicaoCaridade("82386238040");
		assertTrue("A instituição de caridade deveria ter sido cadastrada com sucesso", instituicaoCadastradaComSucesso);

	}
	
	
	/**
	 * Adiciona uma instituição com todos os campos obrigatórios preenchidos com cnpj.
	 */
	@Test
	public void adicionarInstituicaoCaridadeComCnpj() {
		instituicaoCaridadePage.visita();
		instituicaoCaridadePage.novo().addInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES COM CNPJ", "32.521.763/0001-74", "(83) 99812-2196", "zefao2001@teste.com", "58500-000", "Rua Sete de setembro", "123", "Centro", "casa");
		boolean instituicaoCadastradaComSucesso = instituicaoCaridadePage.foiCadastradoComSucessoInstituicaoCaridade("32521763000174");
		assertTrue("A instituição de caridade deveria ter sido cadastrada com sucesso", instituicaoCadastradaComSucesso);

	}
}
