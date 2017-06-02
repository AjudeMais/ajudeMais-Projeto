package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.campanha;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * Classe que contém os métodos necessários para atualizar uma campanha
 * @author elson
 *
 */
public class EditaCampanhaPage extends CampanhaPage{

	public EditaCampanhaPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void desisteDeEditar(){
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/form/div[3]/button"));
	}

}
