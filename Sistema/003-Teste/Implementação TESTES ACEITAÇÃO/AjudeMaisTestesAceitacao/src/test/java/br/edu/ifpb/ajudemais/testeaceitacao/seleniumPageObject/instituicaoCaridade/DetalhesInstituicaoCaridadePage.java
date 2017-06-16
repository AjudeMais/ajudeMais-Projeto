
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
 * Apresenta métodos auxiliares para realizar ações na página de detalhes de instituição de caridade.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class DetalhesInstituicaoCaridadePage extends InstituicaoCaridadePage{

	/**
	 * @param driver
	 */
	public DetalhesInstituicaoCaridadePage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Verifica se todos os valores dos campos passados estão iguais aos apresentados na tela de detalhes da instituição.
	 * @param nome
	 * @param descricao
	 * @param documento
	 * @param telefone
	 * @param email
	 * @param cep
	 * @param logradouro
	 * @param numeroEndereco
	 * @param bairro
	 * @param complemento
	 * @return
	 */
	
	public boolean validateFields(String nome, String descricao,String documento, String telefone, String email, String cep,
			String localidade, String uf, String logradouro, String numeroEndereco, String bairro, String complemento){
		
<<<<<<< HEAD
		System.out.println("EXECUTOU....");
=======
>>>>>>> 7946d0ccb4ce119710b99a4225fc01ba33e27917
		boolean validateNome = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[1]/div[2]/div/div/table/tbody/tr[1]/td")).getText().equals(nome);
		
		boolean validateDescricao = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[1]/div[2]/div/div/table/tbody/tr[2]/td")).getText().equals(descricao);
		
		boolean validateDoc = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[1]/div[2]/div/div/table/tbody/tr[3]/td")).getText().equals(documento);
		
<<<<<<< HEAD
		boolean validateTelefone = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[1]/div[2]/div/div/table/tbody/tr[3]/td")).getText().equals(telefone);
=======
		boolean validateTelefone = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[1]/div[2]/div/div/table/tbody/tr[4]/td")).getText().equals(telefone);
>>>>>>> 7946d0ccb4ce119710b99a4225fc01ba33e27917
		
		boolean validateLogradouro = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[2]/div[2]/div/div[1]/div/table/tbody/tr[1]/td")).getText().equals(logradouro);
		
		boolean validateNumero = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[2]/div[2]/div/div[1]/div/table/tbody/tr[2]/td")).getText().equals(numeroEndereco);
		
		boolean validateBairro = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[2]/div[2]/div/div[1]/div/table/tbody/tr[3]/td")).getText().equals(bairro);
			
		boolean validateComplemento = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[2]/div[2]/div/div[2]/div/table/tbody/tr[1]/td")).getText().equals(complemento);
		
		boolean validateEmail = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[3]/div[2]/div/div/table/tbody/tr[1]/td")).getText().equals(email);
<<<<<<< HEAD
		
		boolean validateLocalidade = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[2]/div[2]/div/div[2]/div/table/tbody/tr[2]/td")).getText().equals(localidade);
		
		boolean validateUf = $(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[2]/div[2]/div/div[2]/div/table/tbody/tr[3]/td")).getText().equals(uf);
=======
			
>>>>>>> 7946d0ccb4ce119710b99a4225fc01ba33e27917
		
		return validateDescricao && (validateNome && validateDoc) && (validateTelefone && validateLogradouro) && (validateNumero && validateBairro) && (validateComplemento && validateEmail) && (validateLocalidade && validateUf) ;
	}
}
