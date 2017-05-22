package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.mensageiro;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * 
 * @author elson
 *
 */
public class EditarMensageiroPage extends MensageiroPage{
	/**
	 *
	 * @param driver
	 */
	public EditarMensageiroPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	/**
	 *  prepara para editar o mensageiro
	 */
	public void editarMensageiro(){
		try {
			Thread.sleep(1000l);
			$(By.xpath("//*[@id=\"modal-body\"]/div[3]/div")).click();
			Thread.sleep(1000l);
			$(By.xpath("/html/body/div[1]/div/div/form/div[2]/input")).click();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * prepara para editar o mensageiro, mas cancela
	 */
	public void desistirDeEditarMensageiro(){
		try {
			Thread.sleep(1000l);
			$(By.xpath("//*[@id=\"modal-body\"]/div[3]/div")).click();
			Thread.sleep(1000l);
			$(By.xpath("/html/body/div[1]/div/div/form/div[2]/button")).click();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
