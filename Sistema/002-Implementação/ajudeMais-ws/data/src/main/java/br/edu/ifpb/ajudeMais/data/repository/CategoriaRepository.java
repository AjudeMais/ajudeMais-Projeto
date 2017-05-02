package br.edu.ifpb.ajudeMais.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.ajudeMais.domain.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
