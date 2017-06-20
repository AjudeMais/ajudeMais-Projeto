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
package br.edu.ifpb.ajudeMais.api.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.ifpb.ajudeMais.api.dto.MessageErrorDTO;
import br.edu.ifpb.ajudeMais.api.rest.DoadorRestService;
import br.edu.ifpb.ajudeMais.service.exceptions.ImageErrorException;
import br.edu.ifpb.ajudeMais.service.exceptions.InvalidAttributeException;
import br.edu.ifpb.ajudeMais.service.exceptions.InvalidRemoveException;
import br.edu.ifpb.ajudeMais.service.exceptions.UniqueConstraintAlreadyException;

/**
 * 
 * <p>
 * {@link RestExceptionHandler}
 * </p>
 * 
 * <p>
 * Classe utilizada para controle de exceções não taradas pela API. Utiliza
 * {@link ControllerAdvice}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@ControllerAdvice(basePackageClasses = { DoadorRestService.class })
public class RestExceptionHandler {

	/**
	 * Handler de erro para tratamento de exceções do {@link BeanValidation}
	 * 
	 * @param req
	 * 
	 * @param manvex
	 * 
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<List<String>> handlerErrorValidation(HttpServletRequest req,
			MethodArgumentNotValidException manvex) {

		List<ObjectError> errors = manvex.getBindingResult().getAllErrors();
		Iterator<ObjectError> iterator = errors.iterator();

		List<String> messages = new ArrayList<>();

		while (iterator.hasNext()) {
			messages.add(iterator.next().getDefaultMessage());
		}

		ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(messages, HttpStatus.UNPROCESSABLE_ENTITY);

		return responseEntity;
	}

	/**
	 * 
	 * <p>
	 * Tratammento de erro para exceção {@link UniqueConstraintAlreadyException}
	 * </p>
	 * 
	 * @param e
	 * 
	 * @return
	 */
	@ExceptionHandler(UniqueConstraintAlreadyException.class)
	@ResponseBody
	public ResponseEntity<MessageErrorDTO> handleUniqueConstraintAlreadyException(UniqueConstraintAlreadyException e) {
		return ResponseEntity.badRequest().body(new MessageErrorDTO(e.getMessage()));
	}
	

	/**
	 * 
	 * <p>
	 * Tratammento de erro para exceção {@link InvalidAttributeException}
	 * </p>
	 * 
	 * @param e
	 * 
	 * @return
	 */
	@ExceptionHandler(InvalidAttributeException.class)
	@ResponseBody
	public ResponseEntity<MessageErrorDTO> handleInvalidAttributeException(InvalidAttributeException e) {
		return ResponseEntity.badRequest().body(new MessageErrorDTO(e.getMessage()));
	}

	/**
	 * 
	 * <p>
	 * Tratamento para exceção {@link AccessDeniedException}
	 * </p>
	 * 
	 * @param e
	 * 
	 * @return
	 */
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public ResponseEntity<MessageErrorDTO> handleAccessDeniedException(AccessDeniedException e) {
		return new ResponseEntity<MessageErrorDTO>(new MessageErrorDTO(e.getMessage()), HttpStatus.FORBIDDEN);
	}
	
	/**
	 * 
	 * <p>
	 * Tratamento para exceção {@link ImageErrorException}
	 * </p>
	 * 
	 * @param e
	 * 
	 * @return
	 */
	@ExceptionHandler(ImageErrorException.class)
	@ResponseBody
	public ResponseEntity<MessageErrorDTO> handleImageError(ImageErrorException e) {
		return new ResponseEntity<MessageErrorDTO>(new MessageErrorDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * 
	 * <p>
	 * Tratamento para exceção {@link ImageErrorException}
	 * </p>
	 * 
	 * @param e
	 * 
	 * @return
	 */
	@ExceptionHandler(InvalidRemoveException.class)
	@ResponseBody
	public ResponseEntity<MessageErrorDTO> handleInvalidRemoveExceptionError(InvalidRemoveException e) {
		return new ResponseEntity<MessageErrorDTO>(new MessageErrorDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
	}


}