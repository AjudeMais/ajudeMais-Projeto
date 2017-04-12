package br.edu.ifpb.ajudeMais.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
public interface UsuarioRepository extends JpaRepository<Conta, Long>{
	
	/**
	 * 
	 * @param username
	 */
	Optional<Conta> findByUsername(String username);

}
