package br.edu.ifpb.ajudeMais.service.negocio;

import java.util.List;

import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;

/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface InstituicaoCaridadeService extends Service<InstituicaoCaridade, Long>{
	
	/**
	 * Busca todos as instituições e seu endereço que é proximo a localidade do endereço passado
	 * @param logradouro
	 * @param bairro
	 * @param localidade
	 * @param uf
	 * @return
	 */
    public List<InstituicaoCaridade> filtersInstituicaoCaridadeClose(String localidade, String uf);


}
