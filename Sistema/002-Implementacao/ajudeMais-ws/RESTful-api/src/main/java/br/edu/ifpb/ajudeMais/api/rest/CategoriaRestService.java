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
package br.edu.ifpb.ajudeMais.api.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.data.repository.InstituicaoCaridadeRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.negocio.CategoriaService;

/**
 * 
 * <p>
 * {@link CategoriaRestService}
 * </p>
 * 
 * <p>
 * Classe utilizada para
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author Elson
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@RestController
@RequestMapping("/categoria")
public class CategoriaRestService {
	/**
	 * 
	 */
	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private AuthService authService;

	@Autowired
	private InstituicaoCaridadeRepository instituicaoRepository;
	
	/**
	 * end point criado para cadastro de uma categoria no sistema
	 * 
	 * @param categoria
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Categoria> save(@Valid @RequestBody Categoria categoria, HttpServletRequest request)
			throws AjudeMaisException {

		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoRepository.findOneByConta(conta);

		if (instituicaoOp.isPresent()) {
			categoria.setInstituicaoCaridade(instituicaoOp.get());
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Categoria categoriaSalva = categoriaService.save(categoria);
		return new ResponseEntity<>(categoriaSalva, HttpStatus.CREATED);
	}

	/**
	 * 
	 * end point criado para atualização dos dados de uma categoria
	 * 
	 * @param categoria
	 * @return
	 * @throws AjudeMaisException
	 */

	@PreAuthorize("hasRole ('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Categoria> update(@Valid @RequestBody Categoria categoria) throws AjudeMaisException {
		Categoria categorias = categoriaService.update(categoria);
		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}

	/**
	 * end point criado para recuperar todas as categorias cadastradas
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole ('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Categoria>> findAll() {
		List<Categoria> categorias = categoriaService.findAll();

		return new ResponseEntity<>(categorias, HttpStatus.OK);

	}

	/**
	 * busca categorias por seu identificador
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole ('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Long id) {

		Categoria categoria = categoriaService.findById(id);

		return new ResponseEntity<>(categoria, HttpStatus.OK);
	}

	/**
	 * busca categorias por insituição
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole ('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/instituicao")
	public ResponseEntity<List<Categoria>> findByInstituicao() {

		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoRepository.findOneByConta(conta);

		List<Categoria> categorias = new ArrayList<>();
		if (instituicaoOp.isPresent()) {
			categorias = categoriaService.findByInstituicaoCaridade(instituicaoOp.get());
		}

		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}

	/**
	 * exclui uma categoria pesquisada pelo identificador
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole ('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Categoria> delete(@PathVariable Long id) {

		Categoria categoriaEncontrada = categoriaService.findById(id);

		if (categoriaEncontrada == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		categoriaService.remover(categoriaEncontrada);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
