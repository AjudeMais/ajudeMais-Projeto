package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.doacao;

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

	private static final String USERNAME_INSTIUTICAO = "42199149196";
	private static final String PASSWORD_INSTIUTICAO = "42199149196";
	private LoginPage loginPage;
	private InstituicaoCaridadePage instituicaoCaridadePage;

	/**
	 * Construtor padrão.
	 * 
	 * @param driver
	 */
	public DoacaoPage(WebDriver driver) {
		super(driver);
		loginPage = new LoginPage(driver);
		instituicaoCaridadePage = new InstituicaoCaridadePage(driver);
	}

	/**
	 * Acessa a página de login na web e inserir suas credencias. Após isso,
	 * verifica se login foi realizado com sucesso, se sim acessa a página de
	 * categoria, se não cadastra uma nova instituição e refaz todo o processo
	 * de login.
	 */
	public void visita() {
		open(getUrlBase() + "home/donativo/list");

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

			$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[5]/a")).click();

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

		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTIUTIÇÃO P TESTE", "CRIADA EM CATEGORIA PAGE",
				USERNAME_INSTIUTICAO, "(83) 99812-2196", "testecat123@teste.com", "58500-000", "Rua Teste", "123",
				"Centro", "casa");
	}
	
	public boolean visualizaPaginaPesquisa() {
		boolean isVisivel = $(By.xpath("//*[@id='content-wrapper']/div/section/h1")).isDisplayed();
		return isVisivel;
	}
	
	/**
	 * 
	 * <p>
	 * </p>
	 * @return
	 */
	public DetalhesDoacaoPage detail() {
		$(By.xpath("//*[@id='dtDonativos']/tbody/tr/td[1]/button")).click();		

		return new DetalhesDoacaoPage(driver);
	}

}
