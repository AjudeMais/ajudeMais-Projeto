package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.categoria;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditarCategoriaPage extends CategoriaPage{

	/**
	 * construtor.
	 * @param driver
	 */
	public EditarCategoriaPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Clica no botão cancelar no modal de categoria.
	 */
	public void desiteDeEditar(){
		$(By.xpath("/html/body/div[1]/div/div/form/div[2]/button"));
	}

}
