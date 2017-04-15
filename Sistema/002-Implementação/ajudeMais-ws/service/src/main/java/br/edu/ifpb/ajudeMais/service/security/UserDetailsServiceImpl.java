/**
 * 
 */
package br.edu.ifpb.ajudeMais.service.security;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.dao.ContaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ContaRepository usuarioRepository;
	
	/**
	 * 
	 */
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		Optional<Conta> usuarioOptional = usuarioRepository.findByUsername(login);
		Conta usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha inválidos"));
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		List<String> permissoes = usuario.getGrupos();
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		return new UsuarioSistema(usuario, authorities);
	}
	

}
