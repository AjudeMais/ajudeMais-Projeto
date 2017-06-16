package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.campanha;

import static com.codeborne.selenide.Condition.appears;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.CriarInstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.InstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.login.LoginPage;

public class CampanhaPage extends AbstractPage {

	private static final String USERNAME_INSTIUTICAO = "42199149196";
	private static final String PASSWORD_INSTIUTICAO = "42199149196";
	private LoginPage loginPage;
	private InstituicaoCaridadePage instituicaoCaridadePage;


	/**
	 * 
	 * @param driver
	 */
	public CampanhaPage(WebDriver driver) {
		super(driver);
		loginPage = new LoginPage(driver);
		instituicaoCaridadePage = new InstituicaoCaridadePage(driver);
	}

	/**
	 * Verifica se a campanha foi criado sucesso.
	 * 
	 * @param nome
	 * @return
	 */
	public boolean foiCadastradaComSucessoCampanha(String nome) {
		$(By.xpath("//*[@id=\"dtCampanhas\"]/tbody")).should(appears);
		boolean campanha = driver.getPageSource().contains(nome);
		return campanha;
	}

	/**
	 * Verifica se a campanha foi editada com sucesso.
	 * 
	 * @param nome
	 * @return
	 */
	public boolean foiEditadoComSucessoCampanha(String nome) {
		$(By.xpath("//*[@id=\"dtCampanhas\"]/tbody")).should(appears);

		boolean nomeCampanha = driver.getPageSource().contains(nome);
		return nomeCampanha;
	}

	/**
	 * Verifica se a categoria foi removida com sucesso.
	 * 
	 * @param msg
	 * @return
	 */
	public boolean campanhaRemovidaComSucesso(String nome) {
		$(By.xpath("//*[@id=\"dtCampanhas\"]/tbody")).should(appears);
		boolean campanha = !(driver.getPageSource().contains(nome));
		return campanha;
	}

	/**
	 * acessa a pagina de login para que o usuário insira suas credenciais
	 */
	public void visita() {
		open(getUrlBase() + "/home/campaha");

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

			$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[6]/a")).click();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[4]/a")).click();

	}

	/**
	 * Adiciona uma instituição de caridade caso ela não exista.
	 */
	private void addInstituicao() {
		instituicaoCaridadePage.visita();

		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();

		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTIUTIÇÂO P TESTE", "CRIADA EM CATEGORIA PAGE",
				USERNAME_INSTIUTICAO, "(83) 99812-2196", "testecat123@teste.com", "58500-000", "Rua Teste", "123",
				"Centro", "casa");
	}

	/**
	 * Clicar no botão novo item e acessa a página de crição/edição de campanha.
	 * 
	 * @return
	 */
	public CreateCampanhaPage novo() {
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div/div/div[1]/div/button")).click();
		return new CreateCampanhaPage(driver);
	}

	/**
	 * Acessa a página de edição de campanha.
	 * 
	 * @return
	 */
	public EditaCampanhaPage edit(String name) {
		String xpath = String.format("//*[@id=\"dtCampanhas\"]/tbody/tr[td/text()=\"%s\"]/td/button[2]", name);
		$(By.xpath(xpath)).click();
		return new EditaCampanhaPage(driver);
	}

	/**
	 * Acessa a página de detalhes da campanha com username passado.
	 * 
	 * @param userName
	 * @return
	 * @throws InterruptedException
	 */
	public DetalhesCampanhaPage detail(String userName) {

		String xpath = String.format("//*[@id=\"dtCampanhas\"]/tbody/tr[td/text()=\"%s\"]/td/button[1]", userName);
		$(By.xpath(xpath)).click();

		return new DetalhesCampanhaPage(driver);
	}

	/**
	 * Adiciona ou edita uma campanha
	 * 
	 * @throws InterruptedException
	 */
	public void adicionarOuEditarCampanha(String nome, String descricao, String termino, boolean status, String itemDoavel, double qtd, String unidMedida) {

		$("#nome").setValue(nome);

		$("#descricao").setValue(descricao);

		$("#tDateEnd").setValue(termino);
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[1]/div[1]/input")).setValue(itemDoavel).pressEnter();
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[2]/select")).selectOption(unidMedida);;
		String value = String.valueOf(qtd);
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[3]/input")).setValue(value).pressTab();
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[4]/button")).click();
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[3]/input")).click();
	}

	/**
	 * Adiciona uma campanha criando item doável
	 * 
	 * @throws InterruptedException
	 */
	public void adicionarCampanhaCriandoItemDoavel(String nome, String descricao, String termino, String nomeItem,
			String descItem, boolean ativo, double qtd, String unidMedida) throws InterruptedException {

		$("#nome").setValue(nome);

		$("#descricao").setValue(descricao);

		$("#tDateEnd").setValue(termino);

		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[1]/div[1]/span/button")).click();
		Thread.sleep(1000l);
		$(By.xpath("//*[@id=\"modal-body\"]/div[1]/input")).setValue(nome);
		$(By.xpath("//*[@id=\"modal-body\"]/div[3]/textarea")).setValue(descricao);

		$(By.xpath("//*[@id=\"modal-body\"]/div[2]/div/div/span[3]")).click();

		$(By.xpath("/html/body/div[1]/div/div/form/div[2]/input")).click();

		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[2]/select")).selectOption(unidMedida);;
		String value = String.valueOf(qtd);
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[3]/input")).setValue(value).pressTab();
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[4]/button")).click();
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[3]/input")).click();
	}

	public void pesquisarCampanhaPorNome(String nome) {
		$(By.xpath("//*[@id=\"dtCampanhas_filter\"]/label/input")).setValue(nome);

	}

	public void pesquisarCampanhaPorData(String data) {
		$(By.xpath("//*[@id=\"dtCampanhas_filter\"]/label/input")).setValue(data);

	}

}
