package br.edu.ifpb.ajudeMais.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;

/**
 * 
 * 
 * <p>
 * <b> {@link DonativoRepository} </b>
 * </p>
 *
 * <p>
 *	Classe utilizada para acesso a dados em BD de um {@link Donativo}
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
public interface DonativoRepository extends JpaRepository<Donativo, Long> {
	
	/**
	 * Busca e retorna uma lista contendo todos os donativoss que tem aquele nome
	 * @param nome
	 * @return
	 */
	List<Donativo> findByNome(String nome);
	
	/**
	 * Busca e retorna uma lista contendo todos os donativos relacionados àquele doador
	 * @param nome
	 * @return
	 */
	List<Donativo> findByDoadorNome(String nomeDoador);

}
