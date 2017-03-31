/**
 * 
 */
package br.edu.ifpb.ajudeMais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 
 * <p>
 * <b> AjudeMaisApplication </b>
 * </p>
 *
 * <p>
 * Start principal do spring boot.
 * </p>
 * 
 * <pre>
 * Esta classe deve esta no inicio da hierarquia de pacotes.
 * Deve ser a primeira classe a ser executada na execução.
 * 
 * @see <a href="http://docs.spring.io/spring-boot/docs/1.5.2.RELEASE/reference/htmlsingle/#using-boot-using-springbootapplication-annotation>Spring boot starter</a>
 * </pre>
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
@SpringBootApplication
public class AjudeMaisApplication extends SpringBootServletInitializer {

	/**
	 * 
	 */
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AjudeMaisApplication.class);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AjudeMaisApplication.class, args);
	}
}
