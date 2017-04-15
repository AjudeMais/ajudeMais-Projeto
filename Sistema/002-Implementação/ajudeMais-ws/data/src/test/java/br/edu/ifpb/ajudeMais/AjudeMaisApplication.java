package br.edu.ifpb.ajudeMais;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */

@SpringBootApplication
public class AjudeMaisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AjudeMaisApplication.class, args);
	}

	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

}
