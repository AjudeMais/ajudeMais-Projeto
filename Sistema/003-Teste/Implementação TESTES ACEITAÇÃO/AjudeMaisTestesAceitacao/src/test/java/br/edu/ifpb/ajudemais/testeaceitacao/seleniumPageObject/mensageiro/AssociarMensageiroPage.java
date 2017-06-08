package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.mensageiro;

import static com.codeborne.selenide.Selenide.$;

import java.io.FileNotFoundException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author elson
 *
 */
public class AssociarMensageiroPage extends MensageiroPage {

	/**
	 * 
	 * @param driver
	 */
	public AssociarMensageiroPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * prepara para associar um mensageiro
	 * 
	 * @param email
	 * @throws FileNotFoundException
	 */
	public void associarMensageiro(String email) {
		try {

			Thread.sleep(1000l);
			$(By.xpath("//*[@id=\"modal-body\"]/div[1]/input")).setValue(email).pressEnter();
			$(By.xpath("/html/body/div[1]/div/div/form/div[2]/input")).click();
			Thread.sleep(1000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * prepara para associar o mensageiro, mas cancela
	 * @param email
	 */
	public void desisteDeAssociarMensageiro(String email){
		try {

			Thread.sleep(1000l);
			$(By.xpath("//*[@id=\"modal-body\"]/div[1]/input")).setValue(email).pressEnter();
			Thread.sleep(000l);
			$(By.xpath("/html/body/div[1]/div/div/form/div[2]/button")).click();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
