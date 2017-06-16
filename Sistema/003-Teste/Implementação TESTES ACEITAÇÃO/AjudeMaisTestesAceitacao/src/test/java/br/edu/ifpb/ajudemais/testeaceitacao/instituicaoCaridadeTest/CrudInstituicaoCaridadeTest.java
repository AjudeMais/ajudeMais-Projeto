
package br.edu.ifpb.ajudemais.testeaceitacao.instituicaoCaridadeTest;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.codeborne.selenide.WebDriverRunner;

import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.CriarInstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.DetalhesInstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.EditatInstituicaoCaridadePage;
import br.edu.ifpb.ajudemais.testeaceitacao.seleniumPageObject.instituicaoCaridade.InstituicaoCaridadePage;

/**
 * 
 * <p>
 * <b> CrudInstituicaoCaridadeTest.java </b>
 * </p>
 * <p>
 * Casos de teste de aceitação para instituição de caridade.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrudInstituicaoCaridadeTest {

	private WebDriver driver;

	private InstituicaoCaridadePage instituicaoCaridadePage;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));

		driver = new ChromeDriver(capabilities);

		driver.manage().window().maximize();
		System.setProperty("selenide.browser", "Chrome");
		instituicaoCaridadePage = new InstituicaoCaridadePage(driver);

		WebDriverRunner.setWebDriver(driver);

	}

	/**
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		driver.close();
	}

	/**
	 * Adiciona uma instituição com todos os campos obrigatórios preenchidos com
	 * cpf.
	 */
	@Test
	public void adicionarInstituicaoCaridadeComCpf() {
		instituicaoCaridadePage.visita();

		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();

		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES","ONG DE ALIMENTOS" ,"823.862.380-40",
				"(83) 99812-2196", "zefao2000@teste.com", "58500-000", "Monteiro", "PB", "Rua Sete de setembro", "123", "Centro", "casa");

		boolean instituicaoCadastradaComSucesso = instituicaoCaridadePage
				.foiCadastradoComSucessoInstituicaoCaridade("82386238040");
		assertTrue("A instituição de caridade deveria ter sido cadastrada com sucesso",
				instituicaoCadastradaComSucesso);

	}

	/**
	 * Adiciona duas instituição com todos os campos obrigatórios preenchidos com e-mails iguais.
	 */
	@Test
	public void adicionarDuasInstituicaoCaridadeMesmosEmail() {
		instituicaoCaridadePage.visita();

		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();

		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES MSM INF.01","ONG DE ALIMENTOS" ,"32.125.616/0001-85",
				"(83) 99812-2196", "zefaomsminfo@teste.com", "58500-000", "Monteiro", "PB", "Rua Sete de setembro", "123", "Centro", "casa");

		boolean instituicaoCadastradaComSucesso = instituicaoCaridadePage.foiCadastradoComSucessoInstituicaoCaridade("32125616000185");
		

		createInstituicaoCaridadePage = instituicaoCaridadePage.novo();

		try {
			Thread.sleep(1000l);
			createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES MSM INF.01","ONG DE ALIMENTOS", "99.625.494/0001-67",
					"(83) 99812-2196", "zefaomsminfo@teste.com", "58500-000", "Monteiro", "PB", "Rua Sete de setembro", "123", "Centro", "casa");
			
			Thread.sleep(700l);

			boolean emailJaEstaSendoUtilizado = createInstituicaoCaridadePage.houveUmErrorCampoInformadoJaExiste("E-mail já está em uso");
			
			assertTrue("Deveria de sido cadastrada um instituição no cadastro da outra ser exibido uma msg com e-mail já em uso",
					instituicaoCadastradaComSucesso && emailJaEstaSendoUtilizado);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
	}
	
	
	/**
	 * Adiciona duas instituição com todos os campos obrigatórios preenchidos com documentos iguais.
	 */
	@Test
	public void adicionarDuasInstituicaoCaridadeMesmosDocumento() {
		instituicaoCaridadePage.visita();

		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();

		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES MSM INF.02","ONG DE ALIMENTOS2","43.122.425/0001-86",
				"(83) 99812-2196", "zefaomsminfo2@teste.com", "58500-000", "Monteiro", "PB", "Rua Sete de setembro", "123", "Centro", "casa");

		boolean instituicaoCadastradaComSucesso = instituicaoCaridadePage.foiCadastradoComSucessoInstituicaoCaridade("43122425000186");
		
		createInstituicaoCaridadePage = instituicaoCaridadePage.novo();
		
		try {
			Thread.sleep(1000l);

			createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES MSM INF.02","ONG DE ALIMENTOS2", "43.122.425/0001-86",
					"(83) 99812-2196", "zefaomsminfo2@teste.com", "58500-000", "Monteiro", "PB", "Rua Sete de setembro", "123", "Centro", "casa");
			
			Thread.sleep(700l);

			boolean docJaEstaSendoUtilizado = createInstituicaoCaridadePage.houveUmErrorCampoInformadoJaExiste("CPF/CNPJ já esta sendo usado");
			
			assertTrue("Deveria de sido cadastrada um instituição no cadastro da outra ser exibido uma msg com Documento já em uso",
					instituicaoCadastradaComSucesso && docJaEstaSendoUtilizado);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
	}

	/**
	 * Adiciona uma instituição com todos os campos obrigatórios preenchidos com
	 * cnpj.
	 */
	@Test
	public void adicionarInstituicaoCaridadeComCnpj() {
		instituicaoCaridadePage.visita();
		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();

		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES COM CNPJ","ONG DE ALIMENTOS123",
				"32.521.763/0001-74", "(83) 99812-2196", "zefao2001@teste.com", "58500-000", "Monteiro", "PB", "Rua Sete de setembro",
				"123", "Centro", "casa");
		boolean instituicaoCadastradaComSucesso = instituicaoCaridadePage
				.foiCadastradoComSucessoInstituicaoCaridade("32521763000174");

		assertTrue("A instituição de caridade deveria ter sido cadastrada com sucesso",
				instituicaoCadastradaComSucesso);

	}

	/**
	 * Tenta adicionar uma instituição sem preecher os campos obrigatórios.
	 * @throws InterruptedException 
	 */
	@Test
	public void adicionarInstituicaoCaridadeSemCamposObgs() throws InterruptedException {
		instituicaoCaridadePage.visita();

		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();
		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("","", "", "", "", "", "", "", "", "", "", "");
		Thread.sleep(1000l);
		boolean houveErroCamposObgsNaoInformados = createInstituicaoCaridadePage.houveUmErroTodosCamposObgs();

		assertTrue("A operação devia ter exibido as mensagens informando que são campos obg.",
				houveErroCamposObgsNaoInformados);

	}

	/**
	 * Tenta adicionar uma instituição de caridade documento CPF
	 * inválido.
	 */
	@Test
	public void adicionarInstituicaoCaridadeComCampoCpfInvalido() {
		instituicaoCaridadePage.visita();
		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();
		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES COM CNPJ INV01","TESTE DESCRICAO",
				"123.343.000-20", "(83) 99812-8196", "zefao2001123@yyr.com", "58500-000", "Monteiro", "PB", "Rua Sete de setembro", "123", "Centro",
				"casa");

		boolean documentoCpfInvalido = createInstituicaoCaridadePage.houveUmErroCampoDocumentoInvalido();


		assertTrue("Uma mensagem informando que os campos estavam inválidos deveria ter sido mostrada",
				documentoCpfInvalido);

	}

	/**
	 * Tenta adicionar uma instituição de caridade com e-mail inválido.
	 */
	@Test
	public void adicionarInstituicaoCaridadeComCampoEmailInvalido() {
		instituicaoCaridadePage.visita();
		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();
		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES COM CNPJ INV01","TESTE DESCRICAO",
				"415.158.917-12", "(83) 99812-8196", "zefao2001", "58500-000", "Monteiro", "PB", "Rua Sete de setembro", "123", "Centro",
				"casa");

		boolean emailInvalido = createInstituicaoCaridadePage.houveUmErroCampoEmailInvalido();

		assertTrue("Uma mensagem informando que os campos estavam inválidos deveria ter sido mostrada",emailInvalido);

	}

	
	/**
	 * Tenta adicionar uma instituição de caridade com documento CNPJ inválido.
	 */
	@Test
	public void adicionarInstituicaoCaridadeComCampoCnpjInvalido() {
		instituicaoCaridadePage.visita();
		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();
		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES COM CNPJ INV02","TESTE DESCRICAO",
				"30.387.784/0001-02", "(83) 99812-8196", "zefao2001@teste.com", "58500-000", "Monteiro", "PB", "Rua Sete de setembro",
				"123", "Centro", "casa");

		boolean documentoCnpjInvalido = createInstituicaoCaridadePage.houveUmErroCampoDocumentoInvalido();

		assertTrue("Uma mensagem informando que o campo estava inválido deveria ter sido mostrada",
				documentoCnpjInvalido);

	}


	/**
	 * Tenta adicionar uma instituição de caridade com telefone Inválido.
	 */
	@Test
	public void adicionarInstituicaoCaridadeComCampoTelefoneInvalido() {
		instituicaoCaridadePage.visita();
		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();
		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES COM CNPJ INV02","TESTE DESCRICAO",
				"557.438.467-15", "(83) 9981", "zefao2001@teste.com", "58500-000", "Monteiro", "PB", "Rua Sete de setembro", "123",
				"Centro", "casa");

		boolean telefoneInvalido = createInstituicaoCaridadePage.houveUmErroCampoTelefoneInvalido();

		assertTrue("Uma mensagem informando que o campo estava inválido deveria ter sido mostrada",
				telefoneInvalido);

	}

	
	/**
	 * Adicionar uma instituição de caridade e testa sua edição.
	 */
	@Test
	public void editarInstituicaoCaridade() {
		instituicaoCaridadePage.visita();

		CriarInstituicaoCaridadePage createInstituicaoCaridadePage = instituicaoCaridadePage.novo();
		createInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DOS ZEFÕES COM CNPJ EDIT01","TESTE DESCRICAO",
				"14268852000179", "(83) 9981-0010", "zefao2001edit@teste.com", "58500-000", "Monteiro", "PB", "Rua Sete de setembro",
				"123", "Centro", "casa");

		boolean foiCadastradoComSucesso = instituicaoCaridadePage.foiCadastradoComSucessoInstituicaoCaridade("14268852000179");

		EditatInstituicaoCaridadePage editInstituicaoCaridadePage = instituicaoCaridadePage.edit("14268852000179");
		
		editInstituicaoCaridadePage.addOrEditInstituicaoCaridade("INSTITUIÇÔES DAS ZEFAS COM CNPJ EDIT01(TRUE)","TESTE DESCRICAO2",
				"", "(83) 9981-0011", "zefao2002edit@teste.com", "69800-000", "Humaitá", "AM", "Rua Francisco Coelho",
				"1709", "Nova Humaitá", "casa");
		

		
		DetalhesInstituicaoCaridadePage detailInstituicaoCaridadePage = instituicaoCaridadePage.detail("14268852000179");
		
		boolean foiEditadoComSucesso = detailInstituicaoCaridadePage.validateFields(
				"INSTITUIÇÔES DAS ZEFAS COM CNPJ EDIT01(TRUE)","TESTE DESCRICAO2", "14268852000179", "(83) 9981-0011",
				"zefao2002edit@teste.com", "69800-000", "Humaitá", "AM", "Rua Francisco Coelho", "1709", "Nova Humaitá", "casa");

		assertTrue("Uma mensagem informando que o campo estava inválido deveria ter sido mostrada",
				foiCadastradoComSucesso && foiEditadoComSucesso);

	}
}
