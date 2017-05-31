package br.edu.ifpb.ajudeMais.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;

/**
 * 
 * 
 * <p>
 * <b> {@link DonativoRepository}</b>
 * </p>
 *
 * <p>
 *	Interface de persistência de um {@link Donativo}
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
public interface DonativoRepository extends JpaRepository<Donativo, Long> {
	
	/**
	 * 
	 * @param nome
	 * @return
	 */
	List<Donativo> findByDoadorNome(String nome);
	
	/**
	 * 
	 * @param nome
	 * @return
	 */
	List<Donativo> findByNome(String nome);

	
}
