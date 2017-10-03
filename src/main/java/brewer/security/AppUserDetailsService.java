package brewer.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import brewer.model.Usuario;
import brewer.repository.Usuarios;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private Usuarios usuarios;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> existente = usuarios.porEmailEAtivo(email);
		
		Usuario usuario = existente.orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha incorretos"));
		
		return new UsuarioSistema(usuario, getPermissoes(usuario));
		
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		//Permissões do usuário
		List<String> permissoes = usuarios.permissoes(usuario);
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		
		return authorities;
	}

}
