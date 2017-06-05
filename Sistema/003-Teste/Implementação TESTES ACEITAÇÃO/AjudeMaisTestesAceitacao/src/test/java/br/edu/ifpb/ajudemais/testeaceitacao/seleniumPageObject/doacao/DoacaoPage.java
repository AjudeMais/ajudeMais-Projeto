package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.doacao;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;

/**
 * 
 * <p>
 * DoacaoPage
 * </p>
 * 
 * <p>
 * Realiza ações em páginas relacionadas ao acompanhamento de doações.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class DoacaoPage extends AbstractPage {

	private static final String USERNAME = "admin123";
	private static final String PASSWORD = "admin123";

	public DoacaoPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Faz login como instituicao e visita a página inicial de instituições de
	 * caridade.
	 */
	public void visita() {
		open(getUrlBase() + "/home/instituicao");
		fazlogin(USERNAME, PASSWORD);
		$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]/a")).click();
	}

}
