package brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import brewer.model.Cliente;
import brewer.repository.helper.cliente.ClientesQueries;

public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {

	public Optional<Cliente> findByCpfOuCnpj(String cpfouCnpj); 
}
