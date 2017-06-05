package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.doacao;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DetalhesDoacaoPage extends DoacaoPage{
	
	/**
	 * @param driver
	 */
	public DetalhesDoacaoPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean validatePage(){
		
		boolean isColVisaoGeral = $(By.xpath("//*[@id='content-wrapper']/div/div[3]/ui-view/div/div/div[1]/div[2]/div[1]/div/table/tbody/tr[1]/th")).isDisplayed();
		
		boolean isColHorarios = $(By.xpath("//*[@id='content-wrapper']/div/div[3]/ui-view/div/div/div[2]/div[2]/div/div/table/thead/tr/th[1]")).isDisplayed();
		
		return isColVisaoGeral && isColHorarios;
	}

}
