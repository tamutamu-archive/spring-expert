package brewer.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import brewer.model.Usuario;
import brewer.repository.helper.usuario.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {

	public Optional<Usuario> findByEmailIgnoreCase(String email);
}
