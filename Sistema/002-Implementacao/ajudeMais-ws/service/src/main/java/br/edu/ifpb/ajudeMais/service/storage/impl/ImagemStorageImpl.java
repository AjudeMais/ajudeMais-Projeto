package br.edu.ifpb.ajudeMais.service.storage.impl;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifpb.ajudeMais.service.exceptions.ImageErrorException;
import br.edu.ifpb.ajudeMais.service.storage.ImagemStorage;

/**
 * 
 * <p>
 * {@link ImagemStorageImpl}
 * </p>
 * 
 * <p>
 * Classe utilizada para implementação de acesso e storage de imagem no disco
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class ImagemStorageImpl implements ImagemStorage {

	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ImagemStorageImpl.class);

	/**
	 * 
	 */
	private Path local;

	/**
	 * 
	 */
	private Path localTmp;

	/**
	 * 
	 * <p>
	 * Construtor default.
	 * </p>
	 *
	 */
	public ImagemStorageImpl() {
		this(getDefault().getPath(System.getenv("HOME"), ".ajudeMaisImages"));
	}

	/**
	 * 
	 * <p>
	 * Construtor que recebe path
	 * </p>
	 *
	 * @param path
	 */
	public ImagemStorageImpl(Path path) {
		this.local = path;
		creatFolders();
	}

	/**
	 * Salva foto em diretório temporário
	 */
	@Override
	public String saveTmp(MultipartFile mFile) {
		String fileName = null;
		if (mFile != null) {
			MultipartFile file = mFile;

			fileName = this.generateFileName(file.getOriginalFilename());
			File dir = new File(this.localTmp.toAbsolutePath().toString() + getDefault().getSeparator() + fileName);

			try {
				file.transferTo(dir);
			} catch (IllegalStateException | IOException e) {
				throw new RuntimeException("Erro ao salvar imagem", e);
			}
		}
		return fileName;
	}

	/**
	 * Recupera foto de diretório temporário.
	 * 
	 * @throws ImageErrorException
	 */
	@Override
	public byte[] getTmp(String nome) throws ImageErrorException {
		try {
			return Files.readAllBytes(this.localTmp.resolve(nome));
		} catch (IOException e) {
			throw new ImageErrorException("Ocorreu um erro ao tentar recuperar imagem temporaria.");
		}
	}

	/**
	 * Salva imagem em diretório final.
	 */
	@Override
	public void save(String img) {
		try {
			Files.move(this.localTmp.resolve(img), this.local.resolve(img));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao mover foto para destino final", e);
		}
	}

	/**
	 * recupera imagem de diretório final
	 * 
	 * @throws ImageErrorException
	 */
	@Override
	public byte[] get(String img) throws ImageErrorException {
		try {
			return Files.readAllBytes(this.local.resolve(img));
		} catch (IOException e) {
			throw new ImageErrorException("Ocorreu um erro ao tentar recuperar imagem.");
		}
	}

	/**
	 * 
	 * <p>
	 * Verifica se uma determinada foto existe no sistema de arquivo
	 * </p>
	 * 
	 * @param img
	 * @return
	 */
	public boolean exists(String img) {
		try {
			byte[] image = this.get(img);
			if (image.length > 0) {
				return true;
			} else {
				return false;
			}
		} catch (ImageErrorException e) {
			return false;
		}
	}
	
	/**
	 * Remove uma imagem do disco pelo nome.
	 */
	@Override
	public void remove(String img) {
		try {
			Files.delete(this.local.resolve(img));
		} catch (IOException e) {
			throw new RuntimeException("Erro remover imagem", e);
		}
		
	}
	
	/**
	 * Remove uma imagem de Temp pelo nome.
	 * @throws ImageErrorException 
	 */
	@Override
	public void removeTmp(String img) throws ImageErrorException {
		try {
			Files.delete(this.localTmp.resolve(img));
		} catch (IOException e) {
			throw new ImageErrorException("Erro remover imagem");
		}
		
	}

	/**
	 * Gera um ID aletório e concatena com nome;
	 * 
	 * @param originalName
	 * @return
	 */
	private String generateFileName(String originalName) {
		String newName = UUID.randomUUID().toString() + " - " + originalName;

		return newName;
	}

	/**
	 * 
	 * <p>
	 * Cria estruturas de pastas para armazenamento de imagens
	 * </p>
	 */
	private void creatFolders() {
		try {
			Files.createDirectories(this.local);
			this.localTmp = getDefault().getPath(this.local.toString(), "tmp");
			Files.createDirectories(this.localTmp);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pastas criadas para salvar imagens.");
				LOGGER.debug("Pasta default: " + this.local.toAbsolutePath());
				LOGGER.debug("Pasta temporária: " + this.localTmp.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar imagens", e);
		}
	}
}
