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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.ajudeMais.domain.entity.Imagem;

/**
 * 
 * <p>
 * {@link ImagemRepository}
 * </p>
 * 
 * <p>
 * Classe utilizada para repositório de imagem. Uma imagem representa as
 * informações, como nome e content type de um upload. É necessária para
 * permitir a recuperação de uma imagem do sistema de arquivos.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {

}
