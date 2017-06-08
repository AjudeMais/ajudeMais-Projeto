package br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.campanha;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DetalhesCampanhaPage extends CampanhaPage {

	public DetalhesCampanhaPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Verifica se os valores passados estão iguais aos valores apresentados na tela de detalhes da campanha
	 * @param nome
	 * @param descricao
	 * @param ativo
	 * @return
	 */
	public boolean validateFields(String nome, String descricao, boolean status, String termino) {

		boolean validateNome = $(By
				.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[1]/div[2]/div/div/table/tbody/tr[1]/td"))
						.getText().equals(nome);

		boolean validateDescricao = $(By
				.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[1]/div[2]/div/div/table/tbody/tr[2]/td"))
						.getText().equals(descricao);

		boolean ativict = $(By
				.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[1]/div[2]/div/div/table/tbody/tr[3]/td/span"))
						.getText().equals(status);
		boolean end = $(By
				.xpath("//*[@id=\"content-wrapper\"]/div/div[3]/ui-view/div/div/div[1]/div[2]/div/div/table/tbody/tr[4]/td"))
				.getText().equals(termino);
		

		return validateDescricao && (validateNome && ativict) && end;
	}
}
