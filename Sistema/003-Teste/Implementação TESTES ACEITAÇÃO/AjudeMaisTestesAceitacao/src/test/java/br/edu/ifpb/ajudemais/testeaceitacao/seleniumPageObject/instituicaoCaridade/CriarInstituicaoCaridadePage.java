
package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * 
 * <p>
 * <b> {@link CriarInstituicaoCaridadePage} </b>
 * </p>
 *
 * <p>
 * Apresenta métodos auxiliares para realizar ações na página de criação de instituição de caridade.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class CriarInstituicaoCaridadePage extends InstituicaoCaridadePage {

	/**
	 * @param driver
	 */
	public CriarInstituicaoCaridadePage(WebDriver driver) {
		super(driver);
	}
	
	
	/**
	 * Valida a existência de mensagens de erro exibidas
	 * @param mensagem
	 * @return
	 */
	public boolean houveUmErrorCampoInformadoJaExiste(String mensagem){
		
		return $(By.xpath("//*[@id=\"loading-bar-container\"]/div/div")).getText().equals(mensagem);
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

		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[2]/div[1]/div/div",
				"Nome deve ser informado");
	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Documento da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgDocumento() {

		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[2]/div[2]/div/div", "CPF ou CNPJ deve ser informado corretamente");
		
	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Telefone da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgTelefone() {
		
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[4]/div[1]/div/div", "Telefone deve ser informado corretamente");

	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg e-mail da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgEmail() {

		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[4]/div[2]/div/div", "E-mail deve ser informado corretamente");

	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Logradouro da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgLogradouro() {

		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[6]/div[2]/div/div", "Logradouro deve ser informado");
	
	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Numero da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgNumero() {
		
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[6]/div[3]/div/div", "Número deve ser informado");

	}
	
	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg CEP da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgCep() {
		
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[6]/div[1]/div/div[2]", "CEP deve ser informado corretamente");

	}

	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Bairro da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgBairro() {
		
	
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[6]/div[4]/div/div","Bairro deve ser informado");

	}
	
	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Cidade da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgCidade() {
		
	
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[7]/div[2]/div/div","Cidade deve ser informado");

	}
	
	
	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg UF da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgUf() {
		
	
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[7]/div[3]/div/div","UF deve ser informado");

	}
	
	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Descrição da
	 * instituição.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgDescricao() {
		
	
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[3]/div/div/div","A descrição deve ser informada");

	}
	
	/**
	 * Verifica a existência de mensagens de erros nos campos obrigatórios no cadastro de isntiuições de caridade.
	 * @return boolean
	 */
	public boolean houveUmErroTodosCamposObgs() {		
	
				
		return (houveUmErroCampoObgNome() && houveUmErroCampoObgDocumento()) && (houveUmErroCampoObgEmail() && houveUmErroCampoObgTelefone()) 
				&& (houveUmErroCampoObgCep() && houveUmErroCampoObgLogradouro()) && (houveUmErroCampoObgNumero() && houveUmErroCampoObgBairro()) 
				&& (houveUmErroCampoObgCidade() && houveUmErroCampoObgUf()) && houveUmErroCampoObgDescricao() ;
	}
	
	
	/**
	 * Verifica se houve um erro de email inválido. 
	 * @return boolean
	 */
	public boolean houveUmErroCampoEmailInvalido() {				
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[4]/div[2]/div/div", "E-mail deve ser informado corretamente");
				
	}
	
	
	/**
	 * Verifica se houve um erro de documento inválido
	 * @return {@link Boolean}
	 */
	public boolean houveUmErroCampoDocumentoInvalido() {				
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[2]/div[2]/div/div", "CPF ou CNPJ deve ser informado corretamente");
				
	}
	
	/**verifica se houve um erro de telefone inválido
	 * 
	 * @return {@link Boolean}
	 */
	public boolean houveUmErroCampoTelefoneInvalido() {				
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/div/div/form/div[1]/div[4]/div[1]/div/div", "Telefone deve ser informado corretamente");
				
	}
}
