
package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject;


import org.openqa.selenium.WebDriver;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * 
 * <p>
 * <b> AbstractPage.java </b>
 * </p>
 *
 * <p>
 * Página abstract para reaproveitamento de código.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public abstract class AbstractPage {

	protected WebDriver driver;

	private static final String URL_BASE = "http://localhost:8000/#!";

	/**
	 * 
	 * @param driver
	 */
	public AbstractPage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * 
	 */
	public void visita() {
		open(getUrlBase() + "/login");
	}

	/**
	 * Faz login e se for primeiro acesso preenche os campos com a mesma senha anterior.
	 * @param username
	 * @param senha
	 */
	public void fazlogin(String username, String password) {
		$("#tLogin").setValue(username);
		$("#tSenha").setValue(password);
		$("#btnLogin").click();

		try {
			Thread.sleep(1000l);
			if ($("#nSenha").exists()) {
				$("#nSenha").setValue(password);
				$("#rSenha").setValue(password);
				$("#btnLogin").click();

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Recuperar mensagem exibida
	 * @return {@link SelenideElement}
	 */
	protected SelenideElement recuperarCaixaMensagem() {

		return $("#idMessages");
	}

	/**
	 * Recuperar mensagem de sucesso exibida

	 * @return {@link SelenideElement}
	 */
	protected SelenideElement recuperarCaixaMensagemSuc() {

		return $("#idMessagesSuc");
	}

	/**
	 * @return a URL base da aplicação 
	 */
	public static String getUrlBase() {
		return URL_BASE;
	}

	
}
