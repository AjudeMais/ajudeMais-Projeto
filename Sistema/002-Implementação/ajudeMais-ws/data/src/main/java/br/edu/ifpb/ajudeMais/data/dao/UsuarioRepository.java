package br.edu.ifpb.ajudeMais.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.ajudeMais.domain.entity.Usuario;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	/**
	 * 
	 * @param username
	 */
	Optional<Usuario> findByUsername(String username);

}
