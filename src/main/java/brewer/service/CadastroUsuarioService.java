package brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import brewer.model.Usuario;
import brewer.repository.Usuarios;
import brewer.service.exception.EmailJaCadastradoException;
import brewer.service.exception.SenhaObrigatoriaNovoUsuarioException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional
	public void salvar(Usuario usuario) { 
		
		Optional<Usuario> existe = usuarios.findByEmailIgnoreCase(usuario.getEmail());
		if(existe.isPresent()) {
			throw new EmailJaCadastradoException("E-mail já cadastrado");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaNovoUsuarioException("Senha é obrigatória para novo usuário");
		}
		
		if(usuario.isNovo()) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}
		
		usuarios.save(usuario);
	}
	
	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario) {
		statusUsuario.executar(codigos, usuarios);
	}
}
