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
package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.CampanhaRepository;
import br.edu.ifpb.ajudeMais.data.repository.CategoriaRepository;
import br.edu.ifpb.ajudeMais.data.repository.DonativoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.exceptions.InvalidRemoveException;
import br.edu.ifpb.ajudeMais.service.negocio.CategoriaService;

/**
 * 
 * <p>
 * {@link CategoriaServiceImpl}
 * </p>
 * 
 * <p>
 * Classe utilizada para serviços de {@link Categoria}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

	/**
	 * 
	 */
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private CampanhaRepository campanhaRepository;
	
	@Autowired
	private DonativoRepository donativoRepository;
	

	/**
	 * salva uma categoria no BD
	 * 
	 * @throws AjudeMaisException
	 */
	@Override
	@Transactional
	public Categoria save(Categoria categoria) throws AjudeMaisException {

		return categoriaRepository.save(categoria);
	}

	/**
	 * atualiza uma categoria previamente salva
	 * 
	 * 
	 * @throws AjudeMaisException
	 */
	@Override
	@Transactional
	public Categoria update(Categoria categoria) throws AjudeMaisException {

		return categoriaRepository.save(categoria);
	}

	/**
	 * 
	 * busca e retorna todas as categorias salvas
	 * 
	 */
	@Override
	public List<Categoria> findAll() {

		return categoriaRepository.findAll();
	}

	/**
	 * 
	 * busca uma categoria especifica pelo ID
	 * 
	 */
	@Override
	public Categoria findById(Long id) {
		return categoriaRepository.findOne(id);
	}

	/**
	 * 
	 * remove uma categoria previamente cadastrada
	 * @throws InvalidRemoveException 
	 * 
	 */
	@Override
	@Transactional
	public void remover(Categoria categoria) throws InvalidRemoveException {
		Long countDoacoes = donativoRepository.countByCategoriaAndCategoriaInstituicaoCaridadeId(categoria,categoria.getInstituicaoCaridade().getId());
		Long countCampanhas = campanhaRepository.filterCountCampanhasMetaCategoriaId(categoria.getId(),categoria.getInstituicaoCaridade().getId());
		
		if (countDoacoes<1 && countCampanhas<1) {
			categoriaRepository.delete(categoria);
		}else{
			throw new InvalidRemoveException("O item não pode ser removido, ele está sendo utilizado.");
		}
	}

	/**
	 * 
	 * busca e retorna as categorias cadastradas por uma instituicao de caridade
	 * em especifico
	 * 
	 */
	@Override
	public List<Categoria> findByInstituicaoCaridade(InstituicaoCaridade instituicaoCaridade) {
		return categoriaRepository.findByInstituicaoCaridade(instituicaoCaridade);
	}

	/**
	 * Buscar todas as categorias ativas ou inativas da instituição com ID
	 * passado.
	 */
	@Override
	public List<Categoria> findByAtivoAndInstituicaoCaridadeId(Boolean ativo, Long id) {
		return categoriaRepository.findByAtivoAndInstituicaoCaridadeId(ativo, id);
	}

	/**
	 * Busca categorias de instituição, filtrando pelo nome da categoria.
	 */
	@Override
	public List<Categoria> findByInstituicaoCaridadeAndNome(InstituicaoCaridade instituicaoCaridade, String nome) {
		return categoriaRepository.findByInstituicaoCaridadeAndNomeIgnoreCaseContaining(instituicaoCaridade, nome);
	}
	

}
