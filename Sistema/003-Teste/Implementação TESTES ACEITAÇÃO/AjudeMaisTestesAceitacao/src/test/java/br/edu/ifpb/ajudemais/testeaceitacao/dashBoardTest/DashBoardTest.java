package br.edu.ifpb.ajudemais.testeaceitacao.dashBoardTest;

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

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.dashBoard.DashBoardPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DashBoardTest {

	private WebDriver driver;
	private DashBoardPage dashBoardPage;

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
		dashBoardPage = new DashBoardPage(driver);

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
	@Test
	public void visualizaPaginaAdministrador() throws InterruptedException{
		dashBoardPage.visitaAdministrador();
		boolean ll = dashBoardPage.visualizaDashBoard();
		Thread.sleep(2000);
		
		assertTrue("Deveria ter mostrado a tela dash board", ll);
	}
	
	@Test
	public void visualizaPaginaInstituicao() throws InterruptedException{
		dashBoardPage.visitaInstituicao();
		boolean val = dashBoardPage.visualizaDashBoard();
		Thread.sleep(2000);
		
		assertTrue("Deveria ter mostrado a tela dash board", val);
	}
	
	@Test
	public void mostraVisaoGeralInstituicao() throws InterruptedException{
		dashBoardPage.visitaAdministrador();
		dashBoardPage.vizualizarGraficoInstituicao();
		Thread.sleep(2000);
		
		
	}
	
}
