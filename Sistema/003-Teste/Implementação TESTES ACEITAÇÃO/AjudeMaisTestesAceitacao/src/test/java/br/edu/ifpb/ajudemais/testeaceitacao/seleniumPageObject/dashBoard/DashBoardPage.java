package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.dashBoard;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.CriarInstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.InstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.login.LoginPage;

public class DashBoardPage extends AbstractPage {

	private static final String USERNAME = "admin123";
	private static final String PASSWORD = "admin123";
	private static final String USERNAME_INSTIUTICAO = "43325904491";
	private static final String PASSWORD_INSTIUTICAO = "43325904491";
	private LoginPage loginPage;
	private InstituicaoCaridadePage instituicaoCaridadePage;

	public DashBoardPage(WebDriver driver) {
		super(driver);
		loginPage = new LoginPage(driver);
		instituicaoCaridadePage = new InstituicaoCaridadePage(driver);
				
	}

	/**
	 * 
	 */
	public void visitaAdministrador() {
		open(getUrlBase() + "home/dashboard/admin");

		fazlogin(USERNAME, PASSWORD);

		$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[2]")).click();
		
	}
	
	public void visitaInstituicao() {
		open(getUrlBase() + "home/dashboard/instituicao");

		fazlogin(USERNAME_INSTIUTICAO, PASSWORD_INSTIUTICAO);

		try {
			Thread.sleep(1000);

			boolean houveLoginInvalido = loginPage.houveLoginInvalido("Nome de usuário ou senha inválido");

			if (houveLoginInvalido) {
				addInstituicao();

				Thread.sleep(1000l);

				$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[1]/div/div/div[1]/a")).click();
				$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[1]/div/div/div[1]/ul/div/ul/a[2]")).click();

				fazlogin(USERNAME_INSTIUTICAO, PASSWORD_INSTIUTICAO);
			}

			$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]")).click();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Adiciona uma instituição de caridade caso ela não exista.
	 */
	private void addInstituicao() {
		instituicaoCaridadePage.visita();

		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();

		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTIUTIÇÃO PARA TESTE", "INSTITUIÇÃO PARA TESTE",
				USERNAME_INSTIUTICAO, "(83) 99812-2196", "testecat123@teste.com", "58500-000", "Monteiro", "PB", "Rua Teste", "123",
				"Centro", "casa");
	}
	
	public boolean visualizaDashBoard() {
		boolean isVisivel = $(By.xpath("//*[@id=\"content-wrapper\"]")).isDisplayed();
		$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[1]/a/span")).click();
		
		return isVisivel;
	}
	
	public void vizualizarGraficoInstituicao() throws InterruptedException{
		driver.findElement(By.id("lineInst")).click();
		$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[1]/a/span")).click();
		Thread.sleep(2000l);
	}
}
