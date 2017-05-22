package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.mensageiro;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 */
public class PesquisarMensageiroPage extends MensageiroPage{
	/**
	 * 
	 * @param driver
	 */
	public PesquisarMensageiroPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param nome
	 */
	public void pesquisarMensageiro(String nome){
		try {
			Thread.sleep(2000l);
			$(By.xpath("//*[@id=\"dtCategorias_filter\"]/label/input")).setValue(nome);
			Thread.sleep(1000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
