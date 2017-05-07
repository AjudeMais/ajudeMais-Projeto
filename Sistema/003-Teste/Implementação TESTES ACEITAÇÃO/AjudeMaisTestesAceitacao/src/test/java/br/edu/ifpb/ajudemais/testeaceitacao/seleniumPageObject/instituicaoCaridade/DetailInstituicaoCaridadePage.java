
package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static com.codeborne.selenide.Selenide.$;



/**
 * 
 * <p>
 * <b> DetailInstituicaoCaridadePage.java </b>
 * </p>
 *
 * <p>
 * Representa o acesso a tela de detalhes de instituição de caridade
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class DetailInstituicaoCaridadePage extends InstituicaoCaridadePage{

	/**
	 * @param driver
	 */
	public DetailInstituicaoCaridadePage(WebDriver driver) {
		super(driver);
	}

	
	public boolean validateFields(String nome, String descricao,String documento, String telefone, String email, String cep,
			String logradouro, String numeroEndereco, String bairro, String complemento){
		
		System.out.println("EXECUTOU....");
		boolean validateNome = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/div[1]/div[2]/div/div/table/tbody/tr[1]/td")).getText().equals(nome);
		
		boolean validateDescricao = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/div[1]/div[2]/div/div/table/tbody/tr[2]/td")).getText().equals(descricao);
		
		boolean validateDoc = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/div[1]/div[2]/div/div/table/tbody/tr[3]/td")).getText().equals(documento);
		
		boolean validateTelefone = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/div[1]/div[2]/div/div/table/tbody/tr[4]/td")).getText().equals(telefone);
		
		boolean validateLogradouro = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/div[3]/div[2]/div/div[1]/div/table/tbody/tr[1]/td")).getText().equals(logradouro);
		
		boolean validateNumero = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/div[3]/div[2]/div/div[1]/div/table/tbody/tr[2]/td")).getText().equals(numeroEndereco);
		
		boolean validateBairro = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/div[3]/div[2]/div/div[1]/div/table/tbody/tr[3]/td")).getText().equals(bairro);
			
		boolean validateComplemento = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/div[3]/div[2]/div/div[2]/div/table/tbody/tr[1]/td")).getText().equals(complemento);
		
		boolean validateEmail = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/div[4]/div[2]/div/div/table/tbody/tr[1]/td")).getText().equals(email);
			
		
		return validateDescricao && (validateNome && validateDoc) && (validateTelefone && validateLogradouro) && (validateNumero && validateBairro) && (validateComplemento && validateEmail);
	}
}
