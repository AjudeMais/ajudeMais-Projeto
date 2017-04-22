
/**
 * 
 * <p>
 * <b> CreateInstituicaoCaridadePage.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;

/**
 * 
 * <p>
 * <b> CreateInstituicaoCaridadePage.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class CreateInstituicaoCaridadePage extends AbstractPage {

	/**
	 * @param driver
	 */
	public CreateInstituicaoCaridadePage(WebDriver driver) {
		super(driver);
	}

	/**
	 */
	public void addInstituicaoCaridade(String nome, String documento, String telefone, String email, String cep,
			String logradouro, String numeroEndereco, String bairro, String complemento) {

		// teste Preechimento automático
		$("#zipCode").setValue(cep);
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[5]/div[1]/div/div[1]/span/button"))
				.click();

		$("#nome").setValue(nome);
		$("#documento").setValue(documento);
		$("#telefone").setValue(telefone);
		$("#email").setValue(email);

		$("#bairro").setValue(bairro);
		$("#complemento").setValue(complemento);
		$("#logradouro").setValue(logradouro);
		$("#numero").setValue(numeroEndereco);

		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[2]/input")).click();
	}

}
