package br.edu.ifpb.ajudeMais.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.ajudeMais.domain.entity.Doador;

/**
 * 
 * <p>
 * <b> DoadorRepository </b>
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
public interface DoadorRepository extends JpaRepository<Doador, Long> {

	/**
	 * Monta uma query filtrando um doador pelo nome
	 * 
	 * @param nome
	 * @return
	 */
	List<Doador> findByNome(String nome);
}
