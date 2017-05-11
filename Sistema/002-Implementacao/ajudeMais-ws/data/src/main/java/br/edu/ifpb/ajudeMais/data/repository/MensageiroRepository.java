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
package br.edu.ifpb.ajudeMais.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;

/**
 * 
 * <p>
 * <b> {@link MensageiroRepository} </b>
 * </p>
 *
 * <p>
 * Repositório para acesso a dados de um {@link Mensageiro}
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public interface MensageiroRepository extends JpaRepository<Mensageiro, Long> {

	/**
	 * 
	 * <p>
	 * Filtra um mensageiro por sua localização
	 * </p>
	 * 
	 * @param logradouro
	 * @param bairro
	 * @param localidade
	 * @param uf
	 * @return
	 */
	public List<Object[]> filtersMensageiroCloser(@Param("logradouro") String logradouro,
			@Param("bairro") String bairro, @Param("localidade") String localidade, @Param("uf") String uf);

}
