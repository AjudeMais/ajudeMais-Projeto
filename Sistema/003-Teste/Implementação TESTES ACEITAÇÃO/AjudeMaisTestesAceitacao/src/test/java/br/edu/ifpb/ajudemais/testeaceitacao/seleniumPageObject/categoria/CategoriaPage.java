package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria;

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
 * Representa a página de categoria de itens doáveis, nela está contido todos os
 * métodos para auxiliar os teste de aceitação que precisaram de algum recurso
 * da página a qual ela representa.
 * 
 * @author elson
 *
 */
public class CategoriaPage extends AbstractPage {

	private static final String USERNAME_INSTIUTICAO = "42199149196";
	private static final String PASSWORD_INSTIUTICAO = "42199149196";
	private LoginPage loginPage;
	private InstituicaoCaridadePage instituicaoCaridadePage;

	/**
	 * Construtor padrão.
	 * 
	 * @param driver
	 */
	public CategoriaPage(WebDriver driver) {
		super(driver);
		loginPage = new LoginPage(driver);
		instituicaoCaridadePage = new InstituicaoCaridadePage(driver);
	}

	/**
	 * Verifica se a categoria foi criado sucesso.
	 * 
	 * @param nome
	 * @return
	 */
	public boolean foiCadastradaComSucessoCategoria(String nome) {
		$(By.xpath("//*[@id=\"dtCategorias\"]/tbody")).should(appears);
		boolean categoria = driver.getPageSource().contains(nome);
		return categoria;
	}
	
	/**
	 * Verifica se a categoria foi editada com sucesso.
	 * 
	 * @param nome
	 * @return
	 */
	public boolean foiEditadoComSucessoCategoria(String nome, String descricao) {
		$(By.xpath("//*[@id=\"dtCategorias\"]/tbody")).should(appears);
		
		boolean nomeCategoria = driver.getPageSource().contains(nome);
		boolean descricaoCategoria = driver.getPageSource().contains(descricao);
		return nomeCategoria && descricaoCategoria;
	}


	/**
	 * Verifica se a categoria foi removida com sucesso.
	 * 
	 * @param msg
	 * @return
	 */
	public boolean categoriaRemovidaComSucesso(String nome) {
		$(By.xpath("//*[@id=\"dtCategorias\"]/tbody")).should(appears);
		boolean categoria = !(driver.getPageSource().contains(nome));
		return categoria;
	}

	/**
	 * Acessa a página de login na web e inserir suas credencias. Após isso,
	 * verifica se login foi realizado com sucesso, se sim acessa a página de
	 * categoria, se não cadastra uma nova instituição e refaz todo o processo
	 * de login.
	 */
	public void visita() {
		open(getUrlBase() + "/home/categoria");

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

			$("#op_itens_doaveis").click();

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
	 * Clicar no botão novo item e acessa a página de crição/edição de categoria de item doável.
	 * @return
	 */
	public CreateCategoriaPage novo() {
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/div/div/div[1]/div/button")).click();
		return new CreateCategoriaPage(driver);
	}

	/**
	 * Clicar no botão remover e abri confim dialog para exclusão. 
	 * @param nome
	 * @return
	 */
	public RemoverCategoriaPage remove(String nome) {
		String xpath = String.format("//*[@id=\"dtCategorias\"]/tbody/tr[td/text()=\"%s\"]/td/button[2]", nome);
		$(By.xpath(xpath)).click();

		return new RemoverCategoriaPage(driver);
	}


	/**
	 * Adiciona ou edita uma categoria
	 */
	public void adicionarOuEditarCategoria(String nome, String descricao, boolean ativo) {

		$(By.xpath("//*[@id=\"modal-body\"]/div[1]/input")).setValue(nome);
		$(By.xpath("//*[@id=\"modal-body\"]/div[3]/textarea")).setValue(descricao);

		$(By.xpath("//*[@id=\"modal-body\"]/div[2]/div/div/span[3]")).click();

		$(By.xpath("/html/body/div[1]/div/div/form/div[2]/input")).click();

	}

	/**
	 * Clicar no botão editar da categoria com nome passado.
	 * @return
	 */
	public EditarCategoriaPage edit(String nome) {
		String xpath = String.format("//*[@id=\"dtCategorias\"]/tbody/tr[td/text()=\"%s\"]/td/button[1]", nome);
		$(By.xpath(xpath)).click();
		return new EditarCategoriaPage(driver);
	}
}
