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

import br.edu.ifpb.ajudeMais.data.repository.ContaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;

/**
 * 
 * <p>
 * {@link UserDetailsServiceImpl}
 * </p>
 * 
 * <p>
 * Classe utilizada para implementção de autenticação customizada com spring
 * security
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * Repositório para salvamento de conta
	 */
	@Autowired
	private ContaRepository contaRepository;

	/**
	 * Método responsável por carregar os detalhes de um usuário tendo como
	 * parametro o seu login / username
	 * 
	 * @param login
	 *            O username do usuario a ser carregado
	 * 
	 * @return Os demais dados do usuário, caso o mesmo exista no banco
	 */
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		Optional<Conta> usuarioOptional = contaRepository.findOneByUsernameAndAtivo(login, true);
		Conta usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha inválidos"));
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		List<String> permissoes = usuario.getGrupos();
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		return new UsuarioSistema(usuario, authorities);
	}

}
