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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.edu.ifpb.ajudeMais.api.rest.DoadorRestService;

/**
 * 
 * <p>
 * <b> RestExceptionHandler </b>
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
@ControllerAdvice(basePackageClasses = {DoadorRestService.class})
public class RestExceptionHandler {

	/**
	 * 
	 * @param req
	 * @param manvex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public ResponseEntity<List<String>> handlerErrorValidation(HttpServletRequest req, MethodArgumentNotValidException manvex) {

		List<ObjectError> errors = manvex.getBindingResult().getAllErrors();
		Iterator<ObjectError> iterator = errors.iterator();		

		List<String> messages = new ArrayList<>();

		while (iterator.hasNext()) {
			messages.add(iterator.next().getDefaultMessage());
		}

		ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(messages, HttpStatus.UNPROCESSABLE_ENTITY);

		return responseEntity;
	}

}