
package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.login;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;

/**
 * 
 * <p>
 * <b> LoginPage.java </b>
 * </p>
 *
 * <p>
 * Page para testar login na parte web </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class LoginPage extends AbstractPage{

	/**
	 * @param driver
	 */
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * 
	 * @param username
	 * @param senha
	 * @throws InterruptedException 
	 */
	public void fazerLogin(String username, String senha) throws InterruptedException{
		open(getUrlBase() + "/login");

		$("#tLogin").setValue(username);
		$("#tSenha").setValue(senha);
        Thread.sleep(600);

		$("#btnLogin").click();


		
	}
	
	/*
	 * 
	 */
	public boolean houveErroUsername(String message){
		return $(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[2]/form/fieldset/div[1]/div")).toString().contains(message);
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	public boolean houveErroSenha(String message){
		return $(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[2]/form/fieldset/div[2]/div")).toString().contains(message);
	}
	
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	public boolean houveLoginInvalido(String message){
		return $(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[2]/div")).toString().contains(message);
	}
	

}
