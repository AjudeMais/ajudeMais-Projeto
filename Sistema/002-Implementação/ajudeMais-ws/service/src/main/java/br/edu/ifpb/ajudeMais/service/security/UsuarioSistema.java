package br.edu.ifpb.ajudeMais.service.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.edu.ifpb.ajudeMais.domain.entity.Usuario;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
public class UsuarioSistema extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;

	/**
	 * 
	 * @param usuario
	 * @param authorities
	 */
	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getUsername(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	/**
	 * 
	 * @return
	 */
	public Usuario getUsuario() {
		return usuario;
	}

}
