package brewer.repository.helper.cliente;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import brewer.model.Cliente;
import brewer.repository.filter.ClienteFilter;

public interface ClientesQueries {
	
	public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable);
	
}
