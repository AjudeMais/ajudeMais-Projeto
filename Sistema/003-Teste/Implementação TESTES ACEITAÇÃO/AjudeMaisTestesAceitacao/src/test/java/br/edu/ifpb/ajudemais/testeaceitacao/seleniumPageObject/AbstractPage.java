
package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject;

import org.openqa.selenium.WebDriver;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

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

	/**
	 * 
	 * @param driver
	 */
	public AbstractPage(WebDriver driver) {
		this.driver = driver;
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

	
}
