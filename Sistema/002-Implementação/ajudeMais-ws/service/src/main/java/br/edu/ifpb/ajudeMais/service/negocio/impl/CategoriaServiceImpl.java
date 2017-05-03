package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.CategoriaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	/**
	 * @throws AjudeMaisException
	 */
	@Override
	@Transactional
	public Categoria save(Categoria categoria) throws AjudeMaisException {
		
		return categoriaRepository.save(categoria);
	}

	/**
	 * @throws AjudeMaisException
	 */
	@Override
	@Transactional
	public Categoria update(Categoria categoria) throws AjudeMaisException {
		
		return categoriaRepository.save(categoria);
	}
	/**
	 * 
	 */
	@Override
	public List<Categoria> findAll() {
		
		return categoriaRepository.findAll();
	}
	/**
	 * 
	 */
	@Override
	public Categoria findById(Long id) {
		return categoriaRepository.findOne(id);
	}
	/**
	 * 
	 */
	@Override
	@Transactional
	public void remover(Categoria categoria) {
		categoriaRepository.delete(categoria);
	}

	
}
