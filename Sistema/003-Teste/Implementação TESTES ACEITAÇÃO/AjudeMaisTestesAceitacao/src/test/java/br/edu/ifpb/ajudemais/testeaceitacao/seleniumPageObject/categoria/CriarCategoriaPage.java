package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * Entidade
 * @author elson
 *
 */
public class CriarCategoriaPage extends CategoriaPage{
	/**
	 * 
	 * @param driver
	 */
	public CriarCategoriaPage(WebDriver driver) {
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
	 * categoria.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgNome() {
		return houveUmErroCampoObg("//*[@id=\"modal-body\"]/div[1]/div", "Nome deve ser informado");
	}
	/**
	 * Verifica se é exibido uma mensagem de erro para campo obg descricao da
	 * categoria.
	 * 
	 * @return
	 */
	public boolean houveUmErroCampoObgDescricao() {

		return houveUmErroCampoObg("//*[@id=\"modal-body\"]/div[3]/div",
				"Descrição deve ser informada");
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean houveUmErroTodosCamposObgs() {		
	
				
		return (houveUmErroCampoObgNome() && houveUmErroCampoObgDescricao());
	}
	

}

