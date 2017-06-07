package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.campanha;

import static com.codeborne.selenide.Condition.appears;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria.CategoriaPage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.InstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.login.LoginPage;

public class CampanhaPage extends AbstractPage{
	
	private static final String USERNAME_INSTIUTICAO = "42199149196";
	private static final String PASSWORD_INSTIUTICAO = "42199149196";
	private LoginPage loginPage;
	private InstituicaoCaridadePage instituicaoCaridadePage;
	private CategoriaPage categoriaPage;
	/**
	 * 
	 * @param driver
	 */
	public CampanhaPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
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
	 * Acessa a página de login na web e inserir suas credencias. Após isso,
	 * verifica se login foi realizado com sucesso, se sim acessa a página de
	 * categoria, se não cadastra uma nova instituição e refaz todo o processo
	 * de login.
	 */
	public void visita() {
		open(getUrlBase() + "/home/campaha");

		fazlogin(USERNAME_INSTIUTICAO, PASSWORD_INSTIUTICAO);

		$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[4]/a")).click();
		
		
	}
	
	/**
	 * Clicar no botão novo item e acessa a página de crição/edição de campanha.
	 * @return
	 */
	public CreateCampanhaPage novo() {
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div/div/div[1]/div/button")).click();
		return new CreateCampanhaPage(driver);
	}
	/**
	 * Acessa a página de edição de campanha.
	 * @return
	 */
	public EditaCampanhaPage edit(String name) {
		String xpath = String.format("//*[@id=\"dtCampanhas\"]/tbody/tr[td/text()=\"%s\"]/td[1]/button[2]", name);
		$(By.xpath(xpath)).click();
		return new EditaCampanhaPage(driver);
	}
	
	/**
	 * Acessa a página de detalhes da campanha com username passado.
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
	 * @throws InterruptedException 
	 */
	public void adicionarOuEditarCampanha(String nome, String descricao, String termino, boolean status) {
		
			$("#nome").setValue(nome);
			
			$("#descricao").setValue(descricao);
			
			$("#tDateEnd").setValue(termino);
			
			$(By.xpath("//*[@id=\"content-wrappr\"]/div/div[3]/ui-view/div/div/form/div[1]/div[4]/div[1]/div/div/span[1]"));
				
			$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[1]/div/input")).setValue("Leite").pressEnter();
				
			$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[1]/div/div/button")).click();
			$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[3]/input")).click();
	}

	/**
	 * Adiciona uma campanha criando item doável
	 * @throws InterruptedException 
	 */
	public void adicionarCampanhaCriandoItemDoavel(String nome, String descricao, String termino, String nomeItem, String descItem, boolean ativo) throws InterruptedException {

		
			$("#nome").setValue(nome);
			
			$("#descricao").setValue(descricao);
			
			$("#tDateEnd").setValue(termino);
				
			$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/div[2]/div[2]/div/button")).click();
			Thread.sleep(1000l);
			$(By.xpath("//*[@id=\"modal-body\"]/div[1]/input")).setValue(nome);
			$(By.xpath("//*[@id=\"modal-body\"]/div[3]/textarea")).setValue(descricao);

			$(By.xpath("//*[@id=\"modal-body\"]/div[2]/div/div/span[3]")).click();

			$(By.xpath("/html/body/div[1]/div/div/form/div[2]/input")).click();
			
			
			$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[3]/input")).click();
		
	}
	
	public void pesquisarCampanhaPorNome(String nome){
		$(By.xpath("//*[@id=\"dtCampanhas_filter\"]/label/input")).setValue(nome);
		
	}
	public void pesquisarCampanhaPorData(String data){
		$(By.xpath("//*[@id=\"dtCampanhas_filter\"]/label/input")).setValue(data);
		
	}
	
}
