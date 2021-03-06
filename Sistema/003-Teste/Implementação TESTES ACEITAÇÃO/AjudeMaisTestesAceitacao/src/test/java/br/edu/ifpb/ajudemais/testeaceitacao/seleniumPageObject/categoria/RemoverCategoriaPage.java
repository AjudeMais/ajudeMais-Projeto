package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Apresentas os rcursos para remoção de categoria.
 * @author elson
 *
 */
public class RemoverCategoriaPage extends CategoriaPage{
	
	/**
	 * 
	 * @param driver
	 */
	public RemoverCategoriaPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Clica no botão Sim no confirm dialog de remover categoria.
	 */
	public void remover(){
		$(By.xpath("/html/body/div[1]/div/div/div[3]/button[1]")).click();
		
	}
	
	/**
	 * Clica no botão cancelar no confirm dialog de remover categoria.
	 */
	public void desistirDaRemocao(){
		$(By.xpath("/html/body/div[1]/div/div/div[3]/button[2]")).click();
		$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[4]/a")).click();
		
	}

}
