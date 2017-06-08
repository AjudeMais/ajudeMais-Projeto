/**
 * <p>
 * Ajude Mais - Módulo Web Service
 * </p>
 * 
 * <p>
 * Sistema para potencializar o processo de doação.
 * </p>
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudemais.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.edu.ifpb.ajudeMais.AjudeMaisApplication;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.exceptions.ImageErrorException;
import br.edu.ifpb.ajudeMais.service.storage.ImagemStorage;
import br.edu.ifpb.ajudeMais.service.storage.impl.ImagemStorageImpl;

/**
 * 
 * <p>
 * {@link ImagemStoreServiceTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes referentes a {@link ImagemStorageImpl}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@SpringBootTest(classes = AjudeMaisApplication.class)
@ActiveProfiles(profiles = { "test" })
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ImagemStoreServiceTest {

	/**
	 * 
	 */
	@Mock
	private ImagemStorage mockImagemStorage;

	/**
	 * 
	 */
	MockMultipartFile file;

	/**
	 * 
	 * <p>
	 * Configura ações, antes de executar unidades de testes.
	 * </p>
	 */
	@Before
	public void setUp() {
		mockImagemStorage = mock(ImagemStorage.class);
		file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
	}

	/**
	 * 
	 * <p>
	 * Teste para salvar um novo arquivo em tmp;
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void saveInTmpOk() throws AjudeMaisException {
		mockImagemStorage.saveTmp(file);
		verify(mockImagemStorage).saveTmp(file);

	}

	/**
	 * 
	 * <p>
	 * Teste para mover imagem de tmp para final folder;
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void parseFolderFinalOk() throws AjudeMaisException {
		mockImagemStorage.save(file.getOriginalFilename());
		verify(mockImagemStorage).save(file.getOriginalFilename());
	}

	/**
	 * 
	 * <p>
	 * Teste para remoção de uma imagem.
	 * </p>
	 */
	@Test
	public void removeImageOk() {
		mockImagemStorage.remove(file.getOriginalFilename());
		verify(mockImagemStorage).remove(file.getOriginalFilename());
	}

	
	/**
	 * 
	 * <p>
	 * Teste para remoção de uma imagem em Temp.
	 * </p>
	 * @throws ImageErrorException 
	 */
	@Test
	public void removeImageTmpOk() throws ImageErrorException {
		mockImagemStorage.removeTmp(file.getOriginalFilename());
		verify(mockImagemStorage).removeTmp(file.getOriginalFilename());
	}	
	
	/**
	 * 
	 * <p>
	 * Teste para getImage em diretório final.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 * @throws IOException
	 */
	@Test
	public void getImageOk() throws AjudeMaisException, IOException {
		when(mockImagemStorage.get(file.getOriginalFilename())).thenReturn(file.getBytes());
		assertEquals(mockImagemStorage.get(file.getOriginalFilename()), file.getBytes());
	}

	/**
	 * 
	 * <p>
	 * Teste para getImage em diretório tmp.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 * @throws IOException
	 */
	@Test
	public void getImageTmpOk() throws AjudeMaisException, IOException {
		when(mockImagemStorage.getTmp(file.getOriginalFilename())).thenReturn(file.getBytes());
		assertEquals(mockImagemStorage.getTmp(file.getOriginalFilename()), file.getBytes());
	}
}
