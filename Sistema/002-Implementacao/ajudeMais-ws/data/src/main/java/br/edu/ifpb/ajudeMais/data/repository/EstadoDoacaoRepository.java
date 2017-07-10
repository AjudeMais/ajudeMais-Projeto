package br.edu.ifpb.ajudeMais.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.ajudeMais.domain.entity.EstadoDoacao;

/**
 * 
 * <p>
 * {@link EstadoDoacao}
 * </p>
 * 
 * <p>
 * Classe utilizada para definição de operações de persistência de um estado de
 * doação.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface EstadoDoacaoRepository extends JpaRepository<EstadoDoacao, Long> {

}
