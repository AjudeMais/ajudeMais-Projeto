package br.edu.ifpb.ajudeMais.service.negocio;

import java.util.List;

import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;

/**
 * 
 * <p>{@link CategoriaService} </p>
 * 
 * <p>
 * Classe utilizada para serviços de categoria
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface CategoriaService extends Service<Categoria, Long>{
	
	
	/**
	 * 
	 * <p>
	 * </p>
	 * @param instituicaoCaridade
	 * @return
	 */
	List<Categoria> findByInstituicaoCaridade(InstituicaoCaridade instituicaoCaridade);

}