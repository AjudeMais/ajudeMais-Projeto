/**
 * Ajude Mais - Módulo Web Service
 * 
 * Sistema para potencializar o processo de doação.
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais;



import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.maps.GoogleMapsService;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroAssociadoService;


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

	@Autowired
	private MensageiroAssociadoService googleMapsService;

	
	/**
	 * Passa app para Servelet do Spring boot.
	 * 
	 */
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
        return application.sources(AjudeMaisApplication.class);
        
	}
	
	@PostConstruct
	private void teste(){
		System.out.println("_____________________EXECUTOU____________________________");
		Endereco endereco = new Endereco();
		endereco.setBairro("Centro");
		endereco.setLocalidade("Monteiro");
		endereco.setUf("PB");
		try {
			List<Mensageiro> mensageiros = this.googleMapsService.filterMensageirosCloser(endereco, 6900l);
			System.out.println(mensageiros);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Executa app.
	 * </p>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		SpringApplication.run(AjudeMaisApplication.class, args);
	}
	
	

}