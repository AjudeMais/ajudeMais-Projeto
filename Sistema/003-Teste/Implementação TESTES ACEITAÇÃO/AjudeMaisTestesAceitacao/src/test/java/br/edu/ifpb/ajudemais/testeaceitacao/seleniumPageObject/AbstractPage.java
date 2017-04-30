
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
	private static final String USERNAME = "admin123";
	private static final String PASSWORD = "admin123";

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
	public void fazlogin() {
		$("#tLogin").setValue(USERNAME);
		$("#tSenha").setValue(PASSWORD);
		$("#btnLogin").click();
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

	/**
	 * @return the username
	 */
	public static String getUsername() {
		return USERNAME;
	}

	/**
	 * @return the password
	 */
	public static String getPassword() {
		return PASSWORD;
	}
	
	

}
