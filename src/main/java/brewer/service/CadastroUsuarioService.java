package brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brewer.model.Usuario;
import brewer.repository.Usuarios;
import brewer.service.exception.EmailJaCadastradoException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	@Transactional
	public void salvar(Usuario usuario) { 
		
		Optional<Usuario> existe = usuarios.findByEmailIgnoreCase(usuario.getEmail());
		if(existe.isPresent()) {
			throw new EmailJaCadastradoException("E-mail já cadastrado");
		}
		
		usuarios.save(usuario);
	}
}
