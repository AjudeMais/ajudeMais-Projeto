package br.edu.ifpb.ajudeMais.service.security;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.dao.UsuarioRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Usuario;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UsuarioRepository usuarioRepository;

	/**
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha inválido"));

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getGrupos().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		
		return new UsuarioSistema(usuario, authorities);
	}
	
	
}
