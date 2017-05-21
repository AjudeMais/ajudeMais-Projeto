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
package br.edu.ifpb.ajudeMais.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.edu.ifpb.ajudeMais.api.security.JwtEntryPoint;
import br.edu.ifpb.ajudeMais.api.security.JwtTokenFilter;

/**
 * 
 * <p>
 * {@link SecurityConfig}
 * </p>
 * 
 * <p>
 * Classe utilizada para configurações da parte de segurança da aplicação
 * utilizando o Spring Security Framework.
 * </p>
 *
 * <pre>
 * 
 * @see https://projects.spring.io/spring-security/ </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

	/**
	 * 
	 */
	@Autowired
	private UserDetailsService userDetailService;


	/**
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	/**
	 * {@link @Bean} para configuração de encriptação usando o {@link BCrypt}
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Bean para recuparar filtro do JWT implementado {@link JwtTokenFilter}
	 * 
	 * @return filtro
	 * 
	 * @throws Exception
	 */
	@Bean
	public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtTokenFilter();
	}
	
	/**
	 * 
	 * <p>
	 * Bean para exceção de autenticação.
	 * </p>
	 * @return
	 */
    @Bean
    public JwtEntryPoint unauthorizedHandler() {
        return new JwtEntryPoint();
    }

	/**
	 * 
	 * <p>
	 * {@link ApiWebSecurityConfigurationAdapter}
	 * </p>
	 * 
	 * <p>
	 * Classe utilizada para configurações de permições
	 * </p>
	 *
	 * <pre>
	 * </pre
	 *
	 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
	 *
	 */
	@Configuration
	public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable()
				.exceptionHandling()
					.authenticationEntryPoint(unauthorizedHandler())
					.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
				.authorizeRequests()
					.antMatchers("/auth/login", "/auth/valida")
					.permitAll()
				.antMatchers(HttpMethod.POST, "/doador", "/conta", "/mensageiro")
					.permitAll()
				.anyRequest()
					.authenticated();

			http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		}
	}
}
