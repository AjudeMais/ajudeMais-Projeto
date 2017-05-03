/**
 * 
 */
package br.edu.ifpb.ajudeMais.service.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
public class UsuarioSistema implements UserDetails {

	private static final long serialVersionUID = 4127773916346149593L;

	private Conta conta;
	private final Collection<? extends GrantedAuthority> authorities;

	public UsuarioSistema(Conta conta, Collection<? extends GrantedAuthority> authorities) {
		this.conta = conta;
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return conta.getUsername();
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return conta.getSenha();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @return the conta
	 */
	public Conta getConta() {
		return conta;
	}

	/**
	 * @param conta
	 *            the conta to set
	 */
	public void setConta(Conta conta) {
		this.conta = conta;
	}

}
