
package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.appears;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;

/**
 * 
 * <p>
 * <b> {@link InstituicaoCaridadePage} </b>
 * </p>
 *
 * <p>
 * Apresenta métodos auxiliares para realizar ações na página inicial de instituição de caridade.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class InstituicaoCaridadePage extends AbstractPage {
	
	private static final String USERNAME = "admin123";
	private static final String PASSWORD = "admin123";

	/**
	 * @param driver
	 */
	public InstituicaoCaridadePage(WebDriver driver) {
		super(driver);

	}

	/**
	 * Verifica se uma institução de caridade foi cadastrada com sucesso.
	 * 
	 * @param userName
	 * @return
	 */
	public boolean foiCadastradoComSucessoInstituicaoCaridade(String userName) {
		$(By.xpath("//*[@id=\"dtInstituicoes\"]/tbody")).should(appears);
		boolean instituicaoCaridade = driver.getPageSource().contains(userName);
		return instituicaoCaridade;
	}

	/**
	 * Faz login como administrador e visita a página inicial de instituições de caridade.
	 */
	public void visita() {
		open(getUrlBase() + "/home/instituicao");
		fazlogin(USERNAME, PASSWORD);
		$("#op_instituicoes").click();
	}

	/**
	 * Clicar no botão criar nova instituição e acessa a página de criação.
	 * @return
	 */
	public CriarInstituicaoCaridadePage novo() {
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div/div/div[1]/div/button")).click();
		
		return new CriarInstituicaoCaridadePage(driver);
	}

	/**
	 * Acessa a página de edição de instituição de caridade.
	 * @return
	 */
	public EditatInstituicaoCaridadePage edit(String userName) {
		String xpath = String.format("//*[@id=\"dtInstituicoes\"]/tbody/tr[td/text()=\"%s\"]/td/button[2]", userName);
		$(By.xpath(xpath)).click();
		return new EditatInstituicaoCaridadePage(driver);
	}

	/**
	 * Acessa a página de detalhes da instituição com username passado.
	 * @param userName
	 * @return
	 * @throws InterruptedException 
	 */
	public DetalhesInstituicaoCaridadePage detail(String userName) {
		
		String xpath = String.format("//*[@id=\"dtInstituicoes\"]/tbody/tr[td/text()=\"%s\"]/td/button[1]", userName);
		
		$(By.xpath(xpath)).click();		

		return new DetalhesInstituicaoCaridadePage(driver);
	}

	/**
	 * Adiciona ou edita uma instituição de caridade
	 */
	public void addOrEditInstituicaoCaridade(String nome, String descricao,String documento, String telefone, String email, String cep,
			String cidade, String uf, String logradouro, String numeroEndereco, String bairro, String complemento) {

		
		try {
			$("#nome").setValue(nome);
			$("#descricao").setValue(descricao);
			
			if (documento.trim().length() > 0) {
				$("#documento").setValue(documento);
			}
			$("#telefone").setValue(telefone);
			$("#email").setValue(email);
			
			$("#zipCode").setValue(cep);
<<<<<<< HEAD
			$("#localidade").setValue(cidade);
			$("#uf").setValue(uf);
=======
			$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[1]/div[6]/div[1]/div/div[1]/span/button")).click();
			
			Thread.sleep(5000l);
			
>>>>>>> 7946d0ccb4ce119710b99a4225fc01ba33e27917
			$("#bairro").setValue(bairro);
			$("#complemento").setValue(complemento);
			$("#logradouro").setValue(logradouro);
			$("#numero").setValue(numeroEndereco);
			
			Thread.sleep(3000l);
			
			$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[2]/input")).click();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
