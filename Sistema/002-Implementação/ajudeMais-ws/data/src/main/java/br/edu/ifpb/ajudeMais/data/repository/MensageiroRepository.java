
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
 * 
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public interface MensageiroRepository extends JpaRepository<Mensageiro, Long>{

	
    public List<Object[]> filtrarMensageirosPorEndereco(@Param("logradouro") String logradouro, @Param("bairro") String bairro, @Param("localidade") String localidade, @Param("uf") String uf);

}
