package br.edu.ifpb.ajudemais.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.edu.ifpb.ajudeMais.AjudeMaisApplication;
import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.DonativoCampanha;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Meta;
import br.edu.ifpb.ajudeMais.domain.enumerations.UnidadeMedida;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoCampanhaService;

/**
 * 
 * 
 * <p>
 * { @link DonativoCampanhaServiceTest }
 * </p>
 * 
 * <p>
 * Classe para testar os serviços oferecidos em {@link DonativoCampanhaService}
 * </p>
 *
 * <pre>
 * </pre>
 *
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>

 *
 */
@SpringBootTest(classes = AjudeMaisApplication.class)
@ActiveProfiles(profiles = { "test" })
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class DonativoCampanhaServiceTest {

	private DonativoCampanha donativoCampanha;
	private Donativo donativo;
	
	@Autowired
	private DonativoCampanhaService donativoCampanhaService;

	@Mock
	private DonativoCampanhaService mockDonativoCampanhaService;

	/**
	 * 
	 * <p>
	 * Configura ações, antes de executar unidades de testes.
	 * </p>
	 */
	@Before
	public void setUp() {
		mockDonativoCampanhaService = mock(DonativoCampanhaService.class);
		getDonativoCampanha();
	}

	/**
	 *<p> Salva um DonativoCampanha</p>
	 * @throws Exception
	 */
	@Test
	public void saveTest() throws Exception {
		mockDonativoCampanhaService.save(donativoCampanha);
		verify(mockDonativoCampanhaService).save(donativoCampanha);
	}

	/**
	 * <p>
	 * Tenta salvar donativoCampanha sem donativo
	 * </p>
	 * @throws Exception
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void saveWithDonativoNull() throws Exception {
		DonativoCampanha donativo = donativoCampanha;
		donativo.setDonativo(null);
		donativoCampanhaService.save(donativo);
	}
	
	/**
	 * <p>
	 * Tenta salvar donativoCampanha sem Campanha
	 * </p>
	 * @throws Exception
	 */
	@Test(expected = NullPointerException.class)
	public void saveWithCampanhaNull() throws Exception {
		DonativoCampanha donativo = donativoCampanha;
		donativo.setCampanha(null);
		donativoCampanhaService.save(donativo);
	}
	
	
	
	/**
	 * 
	 * <p>
	 * Teste para buscar DonativoCampanha pelo id do donativo.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void findByDonativoIdTest() {		
		DonativoCampanha donativo = donativoCampanha;
		when(mockDonativoCampanhaService.findByDonativoId(1l)).thenReturn(donativo);
		assertEquals(mockDonativoCampanhaService.findByDonativoId(1l), donativo);
	}
	
	/**
	 * 
	 * <p>
	 * Teste para buscar DonativoCampanha pelo id da campanha.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void findByCampanhaIdTest() {		
		DonativoCampanha donativo = donativoCampanha;
		when(mockDonativoCampanhaService.findByDonativoId(2l)).thenReturn(donativo);
		assertEquals(mockDonativoCampanhaService.findByDonativoId(2l), donativo);
	}
	
	/**
	 * 
	 * <p>
	 * Teste para buscar donativos doados para um campanha com estado da doação depois de aceito.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void filterDonativoByEstadoAfterAceitoTest() {		
		List<DonativoCampanha> donativoCampanhas = new ArrayList<>();
		donativoCampanhas.add(donativoCampanha);
		
		when(mockDonativoCampanhaService.filterDonativoByEstadoAfterAceito(2l)).thenReturn(donativoCampanhas);
		assertEquals(mockDonativoCampanhaService.filterDonativoByEstadoAfterAceito(2l), donativoCampanhas);
	}

	/**
	 * Cria um donativo qualquer para ser utilizado durante os testes
	 * 
	 * @return mock de donativo
	 */
	private Donativo getDonativo() {
		donativo = new Donativo();

		donativo.setNome("Roupas");
		donativo.setDescricao("Algumas roupas velhas, porém, em bom estado");
		donativo.setQuantidade(10);

		return donativo;

	}

	/**
	 * 
	 * @return
	 */
	private DonativoCampanha getDonativoCampanha() {
		donativo = getDonativo();
		Campanha campanha = new Campanha();
		campanha.setStatus(true);

		InstituicaoCaridade instituicaoCaridade = new InstituicaoCaridade();
		instituicaoCaridade.setDocumento("17532495116");
		instituicaoCaridade.setDescricao("Teste descrição");
		instituicaoCaridade.setNome("Ajudemais");
		instituicaoCaridade.setTelefone("8399800613");

		campanha.setInstituicaoCaridade(instituicaoCaridade);
		campanha.setMetas(new ArrayList<>());

		Categoria categoria = new Categoria();
		categoria.setAtivo(true);
		categoria.setDescricao("Todo tipo de roupa");
		categoria.setNome("Roupas");
		categoria.setInstituicaoCaridade(instituicaoCaridade);

		Meta meta = new Meta();
		meta.setCategoria(categoria);
		meta.setQuantidade(new BigDecimal(400));
		meta.setUnidadeMedida(UnidadeMedida.UNIDADE);

		campanha.getMetas().add(meta);

		donativoCampanha = new DonativoCampanha();
		donativoCampanha.setCampanha(campanha);
		donativoCampanha.setDonativo(donativo);

		return donativoCampanha;
	}
}
