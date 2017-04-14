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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.edu.ifpb.ajudeMais.api.security.JwtEntryPoint;
import br.edu.ifpb.ajudeMais.api.security.JwtTokenFilter;
import br.edu.ifpb.ajudeMais.domain.enumerations.Grupo;

/**
 * 
 * <p>
 * <b> SecurityConfig </b>
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailService;

    @Autowired
    private JwtEntryPoint unauthorizedHandler;

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
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
    @Bean
    public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtTokenFilter();
    }


	/**
	 * 
	 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
	 *
	 */
	@Configuration
	public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
					.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
				.authorizeRequests()
					.antMatchers("/auth/login", "/auth/valida")
						.permitAll()
					.antMatchers(HttpMethod.POST, "/doador")
						.permitAll()
					.antMatchers(HttpMethod.OPTIONS)
						.permitAll()
					.antMatchers("/doador","/instituicao", "/admin")
						.hasAnyRole(Grupo.SUPER.name())
					.anyRequest().authenticated()
					.and();
			
	        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		}
	}
}
