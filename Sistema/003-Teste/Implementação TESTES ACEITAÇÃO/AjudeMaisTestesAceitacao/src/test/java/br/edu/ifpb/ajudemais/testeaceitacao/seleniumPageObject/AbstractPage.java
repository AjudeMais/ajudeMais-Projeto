
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
	 * 
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
	 * 
	 * @return {@link SelenideElement}
	 */
	protected SelenideElement recuperarCaixaMensagem() {

		return $("#idMessages");
	}

	/**
	 * 
	 * @return {@link SelenideElement}
	 */
	protected SelenideElement recuperarCaixaMensagemSuc() {

		return $("#idMessagesSuc");
	}

	/**
	 * @return the urlBase
	 */
	public static String getUrlBase() {
		return URL_BASE;
	}

	
}
