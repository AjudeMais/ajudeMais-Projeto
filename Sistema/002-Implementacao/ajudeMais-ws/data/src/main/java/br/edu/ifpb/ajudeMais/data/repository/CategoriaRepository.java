/**
 * Ajude Mais - Módulo Web Service
 * 
 * Sistema para potencializar o processo de doação.
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.ajudeMais.domain.entity.Categoria;

/**
 * 
 * <p>
 * {@link CategoriaRepository}
 * </p>
 * 
 * <p>
 * Interface de pesistencia para {@link Categoria}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author Elson
 *
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {


}
