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
package br.edu.ifpb.ajudeMais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 
 * <p>
 * {@link AjudeMaisApplication}
 * </p>
 * 
 * <p>
 * Classe utilizada para obter contexto da aplicação para os testes
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@SpringBootApplication
public class AjudeMaisApplication {

	/**
	 * 
	 * <p>
	 * Main para iniciação da aplicação
	 * </p>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AjudeMaisApplication.class, args);
	}

	/**
	 * 
	 * <p>
	 * Cria uma factory para bean validations
	 * </p>
	 * 
	 * @return
	 */
	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

}
