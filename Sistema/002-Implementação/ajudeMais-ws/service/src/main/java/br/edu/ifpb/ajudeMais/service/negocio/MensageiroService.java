
package br.edu.ifpb.ajudeMais.service.negocio;

import java.util.List;

import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;

/**
 * 
 * <p>
 * <b> {@link MensageiroService} </b>
 * </p>
 *
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public interface MensageiroService extends Service<Mensageiro, Long>{
	
	/**
	 * Busca todos os mensageiros e seu endereço que é proximo a localidade do endereço passado
	 * @param logradouro
	 * @param bairro
	 * @param localidade
	 * @param uf
	 * @return
	 */
    public List<Object[]> filtersMensageiroCloser(String logradouro, String bairro, String localidade, String uf);


}
