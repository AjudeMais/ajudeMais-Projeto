
/**
 * 
 * <p>
 * <b> InstitucaoCaridadePage.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade;

import static com.codeborne.selenide.Condition.appears;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By.ByXPath;

import com.codeborne.selenide.commands.Click;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.AbstractPage;

/**
 * 
 * <p>
 * <b> InstitucaoCaridadePage.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class InstituicaoCaridadePage extends AbstractPage{

	private static final int COLUNA_NOME = 2;
	private static final int COLUNA_NOMEUSUARIO = 3;
	private static final int COLUNA_ENDERECO = 4;

	/**
	 * @param driver
	 */
	public InstituicaoCaridadePage(WebDriver driver) {
		super(driver);

	}
	
	
	/**
	 * 
	 */
	public void visita() {
		
		System.out.println(getUrlBase()+"/home/instituicao");
		open(getUrlBase()+"/home/instituicao");
		fazlogin();
		$(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]/a")).click();
	}
	
	/**
	 * 
	 * @return
	 */
	public CreateInstituicaoCaridadePage novo() {
		$(By.xpath("//*[@id=\"content-wrapper\"]/div/div[2]/div/div/div/div/div[1]/div/button")).click();

		return new CreateInstituicaoCaridadePage(driver);
	}
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public boolean foiCadastradoComSucessoInstituicaoCaridade(String userName) {
		$(By.xpath("//*[@id=\"dtInstituicoes\"]/tbody")).should(appears);
		boolean instituicaoCaridade = driver.getPageSource().contains(userName);
		return instituicaoCaridade;
	}

}
