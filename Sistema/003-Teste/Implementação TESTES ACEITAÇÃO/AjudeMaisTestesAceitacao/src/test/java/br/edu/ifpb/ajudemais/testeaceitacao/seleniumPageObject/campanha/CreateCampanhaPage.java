package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.campanha;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * 
 * @author elson
 *
 */
public class CreateCampanhaPage extends CampanhaPage{
	/**
	 * 
	 * @param driver
	 */
	public CreateCampanhaPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
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
	 * campanha.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgNome() {
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[1]/div[2]/div/div/div", "Nome deve ser informado");
		
		
	}
	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg descricao da
	 * campanha.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgDescricao() {

		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[1]/div[3]/div/div/div",
				"A descrição deve ser informada");
	}
	
	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg Termino da campanha.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgTermino() {
		return houveUmErroCampoObg("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[1]/div[4]/div[2]/div[2]",
				"Data de término deve ser informada");
	}
	
	
	/**
	 * Verifica erros existentes nos campos obrigatórios.
	 * @return boolean
	 */
	public boolean houveUmErroTodosCamposObgs() {		
	
				
		return (houveUmErroCampoObgNome() && houveUmErroCampoObgDescricao() && houveUmErroCampoObgTermino());
	}

}
