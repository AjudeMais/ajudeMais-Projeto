/**
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
import br.edu.ifpb.ajudeMais.service.exceptions.UniqueConstraintAlreadyException;

/**
 * 
 * <p>
 * <b> RestExceptionHandler </b>
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
@ControllerAdvice(basePackageClasses = { DoadorRestService.class })
public class RestExceptionHandler {

	/**
	 * 
	 * @param req
	 * @param manvex
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
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UniqueConstraintAlreadyException.class)
	@ResponseBody
	public ResponseEntity<MessageErrorDTO> handleUniqueConstraintAlreadyException(UniqueConstraintAlreadyException e) {
		return ResponseEntity.badRequest().body(new MessageErrorDTO(e.getMessage()));
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public ResponseEntity<MessageErrorDTO> handleAccessDeniedException(AccessDeniedException e) {
		return new ResponseEntity<MessageErrorDTO>(new MessageErrorDTO(e.getMessage()), HttpStatus.FORBIDDEN);
	}

}