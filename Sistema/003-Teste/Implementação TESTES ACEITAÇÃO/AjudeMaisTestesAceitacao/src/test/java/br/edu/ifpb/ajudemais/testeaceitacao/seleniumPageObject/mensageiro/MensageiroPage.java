package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.mensageiro;

import static com.codeborne.selenide.Condition.appears;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.CriarInstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.InstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.login.LoginPage;

/**
 * 
 * @author elson
 *
 */
public class MensageiroPage extends AbstractPage {
	
	/**
	 * 
	 */
	private static final String USERNAME_INSTIUTICAO = "76914318301";
	/**
	 * 
	 */
	private static final String PASSWORD_INSTIUTICAO = "76914318301";


	/**
	 * 
	 */
	private InstituicaoCaridadePage instituicaoCaridadePage;
	private LoginPage loginPage;

	/**
	 * 
	 * @param driver
	 */
	public MensageiroPage(WebDriver driver) {
		super(driver);
		loginPage = new LoginPage(driver);
		instituicaoCaridadePage = new InstituicaoCaridadePage(driver);

	}
	/**
	 * 
	 */
	public void visita() {
		open(getUrlBase() + "/home/mensageirosAssociados");

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

			$("#op_mensageiros").click();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Adiciona uma instituição de caridade caso ela não exista.
	 */
	private void addInstituicao() {
		instituicaoCaridadePage.visita();

		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();

		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTIUTIÇÂO P2 TESTE", "CRIADA E2M CATEGORIA PAGE",
				USERNAME_INSTIUTICAO, "(83) 99812-2196", "testecat123454@teste.com", "58500-000", "Rua Teste", "123",
				"Centro", "casa");
	}

	/**
	 * 
	 * @return
	 */
	public AssociarMensageiroPage novo() {
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/div/div/div[1]/div/button")).click();
		return new AssociarMensageiroPage(driver);
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public EditarMensageiroPage edit(String email) {
		String xpath = String.format("//*[@id=\"dtCategorias\"]/tbody/tr[td/text()=\"%s\"]/td/button[1]", email);
		$(By.xpath(xpath)).click();
		return new EditarMensageiroPage(driver);
	}

	/**
	 * 
	 * @param nome
	 * @return
	 */
	public PesquisarMensageiroPage search(String nome) {

		return new PesquisarMensageiroPage(driver);
	}

	/**
	 * 
	 * @param nome
	 * @return
	 */
	public boolean foiAssociadoMensageiroComSucesso(String email) {
		String emailTable = $(By.xpath("//*[@id=\"dtCategorias\"]/tbody/tr/td[3]")).getText();

		if (emailTable.equals(email)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param nome
	 * @return
	 */
	public boolean erroAssociarMensageiroIgual() {

		if ($(By.xpath("//*[@id=\"toast-container\"]/div")).getText()
				.equals("Este mensageiro já esta associado a esta instituição.")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param nome
	 * @return
	 */
	public boolean foiEditadoMensageiroComSucesso(String nome) {
		$(By.xpath("//*[@id=\"loading-bar-container\"]/div")).should(appears);
		boolean value = driver.getPageSource().contains(nome);
		return value;
	}
}
