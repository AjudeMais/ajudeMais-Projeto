package br.edu.ifpb.ajudeMais.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
public interface ContaRepository extends JpaRepository<Conta, Long>{
	
	/**
	 * 
	 * @param username
	 */
	Optional<Conta> findOneByUsername(String username);
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	Optional<Conta> findOneByEmail(String email);

}
