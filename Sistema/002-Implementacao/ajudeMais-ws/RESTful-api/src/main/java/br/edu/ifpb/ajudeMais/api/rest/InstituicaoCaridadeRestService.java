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
package br.edu.ifpb.ajudeMais.api.rest;

import java.util.List;

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

import br.edu.ifpb.ajudeMais.data.repository.CategoriaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.maps.dto.LatLng;
import br.edu.ifpb.ajudeMais.service.negocio.InstituicaoCaridadeService;

/**
 * 
 * <p>
 * {@link InstituicaoCaridadeRestService}
 * </p>
 * 
 * <p>
 * Classe utilizada para disponibilização de serviços relacionados a
 * {@link InstituicaoCaridade}.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@RestController
@RequestMapping("/instituicao")
public class InstituicaoCaridadeRestService {

	/**
	 * 
	 */
	@Autowired
	private InstituicaoCaridadeService instituicaoService;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	/**
	 * <p>
	 * POST /insituicao : Método disponibiliza recurso para salvar uma
	 * insituição. <br>
	 * ROLE: ADMIN
	 * </p>
	 * 
	 * @param instituicaoCaridade
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<InstituicaoCaridade> save(@Valid @RequestBody InstituicaoCaridade instituicaoCaridade)
			throws AjudeMaisException {
		InstituicaoCaridade instituicaoSalva = instituicaoService.save(instituicaoCaridade);
		return new ResponseEntity<>(instituicaoSalva, HttpStatus.CREATED);
	}

	/**
	 * 
	 * <p>
	 * PUT /insituicao : Endpoint para atualizar informações de uma instituição
	 * de caridade. <br>
	 * ROLE: ADMIN
	 * </p>
	 * 
	 * @param instituicaoCaridade
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<InstituicaoCaridade> update(@Valid @RequestBody InstituicaoCaridade instituicaoCaridade)
			throws AjudeMaisException {
		
		List<Categoria> categorias = categoriaRepository.findByInstituicaoCaridade(instituicaoCaridade);
		instituicaoCaridade.setItensDoaveis(categorias);
		InstituicaoCaridade instituicao = instituicaoService.update(instituicaoCaridade);

		return new ResponseEntity<InstituicaoCaridade>(instituicao, HttpStatus.OK);
	}

	/**
	 * <p>
	 * GET /insituicao : Endpoint para buscar todas as instituições de caridade.
	 * <br>
	 * ROLE: ADMIN, DOADOR
	 * </p>
	 * 
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN,  DOADOR')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<InstituicaoCaridade>> findAll() {
		List<InstituicaoCaridade> instituicoes = instituicaoService.findAll();

		return new ResponseEntity<>(instituicoes, HttpStatus.OK);
	}

	/**
	 * <p>
	 * POST /insituicao/filterGeoAddress : Endpoint para filtrar instituições
	 * por uma localidade e estado. <br>
	 * ROLE: ADMIN, DOADOR
	 * </p>
	 * 
	 * @param localidade
	 * @param uf
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN, DOADOR')")
	@RequestMapping(method = RequestMethod.POST, value = "/filterGeoAddress")
	public ResponseEntity<List<InstituicaoCaridade>> filtersInstituicoesForAddress(@RequestBody Endereco endereco) {
		List<InstituicaoCaridade> instituicoes = instituicaoService.filtersInstituicoesForAddress(endereco);

		return new ResponseEntity<>(instituicoes, HttpStatus.OK);
	}

	/**
	 * <p>
	 * POST /insituicao/filterGeoCoordinates : Endpoint para filtrar
	 * instituições pela latitude e longitude passada. <br>
	 * ROLE: ADMIN, DOADOR
	 * </p>
	 * 
	 * @param localidade
	 * @param uf
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN, DOADOR')")
	@RequestMapping(method = RequestMethod.POST, value = "/filterGeoCoordinates")
	public ResponseEntity<List<InstituicaoCaridade>> filtersInstituicoesForLatitudeAndLongitude(
			@RequestBody LatLng latLng) {
		List<InstituicaoCaridade> instituicoes = instituicaoService.filtersInstituicaoCloseForLatAndLng(latLng);

		return new ResponseEntity<>(instituicoes, HttpStatus.OK);
	}

	/**
	 * <p>
	 * GET /insituicao/id : Endpoint para buscar uma instituição de caridade
	 * pelo ID. <br>
	 * ROLE: ADMIN, INSTITUICAO
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN, INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<InstituicaoCaridade> findById(@PathVariable Long id) {
		InstituicaoCaridade instituicao = instituicaoService.findById(id);

		return new ResponseEntity<InstituicaoCaridade>(instituicao, HttpStatus.OK);

	}
}
