
package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


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
public class CreateInstituicaoCaridadePage extends InstituicaoCaridadePage {

	/**
	 * @param driver
	 */
	public CreateInstituicaoCaridadePage(WebDriver driver) {
		super(driver);
	}
	
	
	/**
	 * Valida a existência de mensagens de erro exibidas
	 * @param mensagem
	 * @return
	 */
	public boolean houveUmErrorCampoInformadoJaExiste(String mensagem){
		return $(By.xpath("/html/body/div[2]/div/div")).getText().equals(mensagem);
	}

	
	/**
	 * Valida a existencia da mensageira no xpath passado
	 * @param xpath
	 * @return
	 */
	private boolean houveUmErroCampoObg(String xpath, String mensagem) {
		
		if ($(By.xpath(xpath)).toString().contains(mensagem)) {
			return true;
			
		}

		return false;
	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg nome da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgNome() {

		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[2]/div[1]/div/div",
				"Nome deve ser informado");
	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Documento da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgDocumento() {

		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[2]/div[2]/div/div", "CPF ou CNPJ deve ser informado corretamente");
		
	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Telefone da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgTelefone() {
		
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[4]/div[1]/div/div", "Telefone deve ser informado corretamente");

	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg e-mail da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgEmail() {

		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[4]/div[2]/div/div", "E-mail deve ser informado corretamente");

	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Logradouro da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgLogradouro() {

		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[6]/div[2]/div/div", "Logradouro deve ser informado");
	
	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Numero da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgNumero() {
		
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[6]/div[3]/div/div", "Número deve ser informado");

	}
	
	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg CEP da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgCep() {
		
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[6]/div[1]/div/div[2]", "CEP deve ser informado corretamente");

	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Bairro da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgBairro() {
		
	
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[6]/div[4]/div/div","Bairro deve ser informado");

	}
	
	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Cidade da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgCidade() {
		
	
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[7]/div[2]/div/div","Cidade deve ser informado");

	}
	
	
	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg UF da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgUf() {
		
	
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[7]/div[3]/div/div","UF deve ser informado");

	}
	
	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Descrição da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgDescricao() {
		
	
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[3]/div/div/div","A descrição deve ser informada");

	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean houveUmErroTodosCamposObgs() {		
	

		return (houveUmErroCampoObgNome() && houveUmErroCampoObgDocumento()) && (houveUmErroCampoObgEmail() && houveUmErroCampoObgTelefone()) 
				&& (houveUmErroCampoObgCep() && houveUmErroCampoObgLogradouro()) && (houveUmErroCampoObgNumero() && houveUmErroCampoObgBairro()) 
				&& (houveUmErroCampoObgCidade() && houveUmErroCampoObgUf()) && houveUmErroCampoObgDescricao() ;
	}
	
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean houveUmErroCampoEmailInvalido() {				
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[4]/div[2]/div/div", "E-mail deve ser informado corretamente");
				
	}
	
	
	/**
	 * 
	 * @return {@link Boolean}
	 */
	public boolean houveUmErroCampoDocumentoInvalido() {				
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[2]/div[2]/div/div", "CPF ou CNPJ deve ser informado corretamente");
				
	}
	
	/**
	 * 
	 * @return {@link Boolean}
	 */
	public boolean houveUmErroCampoTelefoneInvalido() {				
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/form/div[1]/div[4]/div[1]/div/div", "Telefone deve ser informado corretamente");
				
	}
}
