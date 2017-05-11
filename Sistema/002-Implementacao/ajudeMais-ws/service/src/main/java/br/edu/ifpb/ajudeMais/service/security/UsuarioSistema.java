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
 * <p>
 * {@link UsuarioSistema}
 * </p>
 * 
 * <p>
 * Classe Pojo para recuperação de conta na autenticação
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class UsuarioSistema implements UserDetails {

	private static final long serialVersionUID = 4127773916346149593L;

	/**
	 * 
	 */
	private Conta conta;
	private final Collection<? extends GrantedAuthority> authorities;

	/**
	 * 
	 * @param conta
	 *            a conta de um usuário
	 * 
	 * @param authorities
	 *            permissoes concedidas
	 * 
	 */
	public UsuarioSistema(Conta conta, Collection<? extends GrantedAuthority> authorities) {
		this.conta = conta;
		this.authorities = authorities;
	}

	/**
	 * pega e retorna o username
	 * 
	 */
	@Override
	public String getUsername() {
		return conta.getUsername();
	}

	/**
	 * 
	 * verifica se a conta não expirou
	 * 
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 
	 * verifica se a conta não está bloqueada
	 * 
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 
	 * 
	 * verifica se as credenciais estao válidas ou não
	 * 
	 */
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 
	 * Pega e retorna a senha
	 * 
	 */
	@JsonIgnore
	@Override
	public String getPassword() {
		return conta.getSenha();
	}

	/**
	 * 
	 * pega e retorna as permissões concedidas
	 * 
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * 
	 * verifica se o usuario está ativo
	 * 
	 */
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
