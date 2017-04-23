
package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade;

import static com.codeborne.selenide.Condition.appears;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;

/**
 * 
 * <p>
 * <b> InstitucaoCaridadePage.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class InstituicaoCaridadePage extends AbstractPage {

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
	 * 
	 */
	public void visita() {
		open(getUrlBase() + "/home/instituicao");
		fazlogin();
		$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]/a")).click();
	}

	/**
	 * 
	 * @return
	 */
	public CreateInstituicaoCaridadePage novo() {
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/div/div/div[1]/div/button")).click();

		return new CreateInstituicaoCaridadePage(driver);
	}

	/**
	 * 
	 * @return
	 */
	public EditInstituicaoCaridadePage edit(String userName) {
		String xpath = String.format("//*[@id=\"dtInstituicoes\"]/tbody/tr[td/text()=\"%s\"]/td/button[2]", userName);
				
		$(By.xpath(xpath)).click();
		return new EditInstituicaoCaridadePage(driver);
	}

	/**
	 * 
	 * @param userName
	 * @return
	 * @throws InterruptedException 
	 */
	public DetailInstituicaoCaridadePage detail(String userName) {
		
		String xpath = String.format("//*[@id=\"dtInstituicoes\"]/tbody/tr[td/text()=\"%s\"]/td/button[1]", userName);
		
		$(By.xpath(xpath)).click();		

		return new DetailInstituicaoCaridadePage(driver);
	}

	/**
	 * Adiciona ou edita uma instituição de caridade
	 */
	public void addOrEditInstituicaoCaridade(String nome, String documento, String telefone, String email, String cep,
			String logradouro, String numeroEndereco, String bairro, String complemento) {

		$("#zipCode").setValue(cep);
		
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[5]/div[1]/div/div[1]/span/button")).click();

		$("#nome").setValue(nome);
		
		if (documento.trim().length() > 0) {
			$("#documento").setValue(documento);
		}
		
		$("#telefone").setValue(telefone);
		$("#email").setValue(email);

		$("#bairro").setValue(bairro);
		$("#complemento").setValue(complemento);
		$("#logradouro").setValue(logradouro);
		$("#numero").setValue(numeroEndereco);

		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[2]/input")).click();
	}

}
