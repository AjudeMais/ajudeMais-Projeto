package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.mensageiro;

import static com.codeborne.selenide.Condition.appears;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;

/**
 * 
 * @author elson
 *
 */
public class MensageiroPage extends AbstractPage {
	/**
	 * 
	 */
	private static final String USERNAME_INSTIUTICAO = "17871668000107";
	/**
	 * 
	 */
	private static final String PASSWORD_INSTIUTICAO = "ongx";

	/**
	 * 
	 * @param driver
	 */
	public MensageiroPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * 
	 */
	public void visita() {
		open(getUrlBase() + "/home/associacao");
		fazlogin(USERNAME_INSTIUTICAO, PASSWORD_INSTIUTICAO);
		$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[5]/a")).click();
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
		String xpath = String.format("//*[@id=\"dtCategorias\"]/tbody/tr[td/text()=\"%s\"]/td/button", email);
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
	public boolean foiAssociadoMensageiroComSucesso(String msg) {
		$(By.xpath("//*[@id=\"loading-bar-container\"]/div")).should(appears);
		boolean value = driver.getPageSource().contains(msg);
		return value;
	}

	/**
	 * 
	 * @param nome
	 * @return
	 */
	public boolean erroAssociarMensageiroIgual(String nome) {
		$(By.xpath("//*[@id=\"loading-bar-container\"]/div")).should(appears);
		boolean value = driver.getPageSource().contains(nome);
		return value;
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

	/**
	 * Adiciona uma instituição de caridade caso ela não exista.
	 */
}
