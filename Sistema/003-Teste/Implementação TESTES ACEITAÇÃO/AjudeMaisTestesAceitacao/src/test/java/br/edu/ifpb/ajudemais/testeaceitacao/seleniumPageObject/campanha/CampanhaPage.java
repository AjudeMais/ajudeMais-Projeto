package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.campanha;

import static com.codeborne.selenide.Condition.appears;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

<<<<<<< HEAD
=======

>>>>>>> 7946d0ccb4ce119710b99a4225fc01ba33e27917
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.CriarInstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.InstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.login.LoginPage;

public class CampanhaPage extends AbstractPage {
	/**
	 * 
	 */
	private static final String USERNAME_INSTIUTICAO = "42199149196";
	/**
	 * 
	 */
	private static final String PASSWORD_INSTIUTICAO = "42199149196";
<<<<<<< HEAD
	/**
	 * 
	 */
	private InstituicaoCaridadePage instituicaoCaridadePage;
	/**
	 * 
	 */
	private LoginPage loginPage;
=======
	private LoginPage loginPage;
	private InstituicaoCaridadePage instituicaoCaridadePage;


>>>>>>> 7946d0ccb4ce119710b99a4225fc01ba33e27917
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
		
<<<<<<< HEAD
		boolean houveErroLogin = loginPage.houveLoginInvalido("Nome de usuário ou senha inválido");
		try {
			Thread.sleep(1000);
		if(houveErroLogin){
			addInstituicao();
=======
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
>>>>>>> 7946d0ccb4ce119710b99a4225fc01ba33e27917

			Thread.sleep(1000l);

			$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[1]/div/div/div[1]/a")).click();
			$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[1]/div/div/div[1]/ul/div/ul/a[2]")).click();

			fazlogin(USERNAME_INSTIUTICAO, PASSWORD_INSTIUTICAO);
		}
		$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[4]/a")).click();
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

		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTIUTIÇÂO P TESTE", "CRIADA EM CATEGORIA PAGE",
				USERNAME_INSTIUTICAO, "(83) 99812-2196", "testecat123@teste.com", "58500-000", "Monteiro", "PB", "Rua Teste", "123",
				"Centro", "casa");
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
		String value = String.valueOf(qtd);
<<<<<<< HEAD
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[3]/input")).setValue(value).pressTab();
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[4]/button")).click();
		CreateCampanhaPage campanha = new CreateCampanhaPage(driver);
		boolean erroItemDoavel = campanha.houveUmErroCampoObgItemDoavel();
		if(erroItemDoavel){
			try {
				$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[1]/div[1]/span/button")).click();
				Thread.sleep(1000l);
				$(By.xpath("//*[@id=\"modal-body\"]/div[1]/input")).setValue(itemDoavel);
				$(By.xpath("//*[@id=\"modal-body\"]/div[3]/textarea")).setValue(itemDoavel);
				
				$(By.xpath("//*[@id=\"modal-body\"]/div[2]/div/div/span[3]")).click();
				
				$(By.xpath("/html/body/div[1]/div/div/form/div[2]/input")).click();
				$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[4]/button")).click();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[3]/input")).click();
	}
	public void editarCampanha(String nome, String descricao, String termino){
=======

		validaEadicionarMeta(itemDoavel, "teste", unidMedida, value);
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[3]/input")).click();
	}
	
	public void validaEadicionarMeta(String nomeCategoria, String descricaoCategoria, String unidadeMedida, String quantidade){
		
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[1]/div[1]/input")).setValue(nomeCategoria).pressEnter();
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[4]/button")).click();

		boolean categoriaExiste = campoObgCategoriaNaoInformado();
		
		if (categoriaExiste) {
			cadastraNovaCategoria(nomeCategoria, descricaoCategoria);
		}

		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[2]/select")).selectOption(unidadeMedida);
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[3]/input")).setValue(quantidade).pressTab();
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[4]/button")).click();

	
	
	}
	
	private void cadastraNovaCategoria(String nomeCategoria, String descricaoCategoria){
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[1]/div[1]/span/button")).click();
		
		$(By.xpath("//*[@id=\"modal-body\"]/div[1]/input")).setValue(nomeCategoria);
		$(By.xpath("//*[@id=\"modal-body\"]/div[3]/textarea")).setValue(descricaoCategoria);
		
		$(By.xpath("/html/body/div[1]/div/div/form/div[2]/input")).click();

	}
	
	public boolean campoObgCategoriaNaoInformado(){
		return $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[1]/div[2]")).toString().contains("O item deve ser informado.");
	}

	/**
	 * Adiciona uma campanha criando item doável
	 * 
	 * @throws InterruptedException
	 */
	public void adicionarCampanhaCriandoItemDoavel(String nome, String descricao, String termino, String nomeItem,
			String descItem, boolean ativo, double qtd, String unidMedida) throws InterruptedException {

>>>>>>> 7946d0ccb4ce119710b99a4225fc01ba33e27917
		$("#nome").setValue(nome);

		$("#descricao").setValue(descricao);

		$("#tDateEnd").setValue(termino);
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[3]/input")).click();
	}
	public void pesquisarCampanhaPorNome(String nome) {
		$(By.xpath("//*[@id=\"dtCampanhas_filter\"]/label/input")).setValue(nome);

	}

	public void pesquisarCampanhaPorData(String data) {
		$(By.xpath("//*[@id=\"dtCampanhas_filter\"]/label/input")).setValue(data);

	}

}
