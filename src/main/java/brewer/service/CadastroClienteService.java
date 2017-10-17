package brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brewer.model.Cliente;
import brewer.repository.Clientes;
import brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import brewer.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroClienteService {

	@Autowired
	private Clientes clientes;
	
	@Transactional
	public void salvar(Cliente cliente) {
		
		Optional<Cliente> existente = clientes.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		
		if(cliente.isNovo() && existente.isPresent()) {
			throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado");
		}
		
		if(existente.isPresent() && existente.get().getCodigo() != cliente.getCodigo()) {
			throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado");
		}
		
		clientes.save(cliente);
	}

	@Transactional
	public void excluir(Cliente cliente) {
		
		try {
			
			clientes.delete(cliente);
			clientes.flush();
			
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir, cliente efetuou compras");
		}		
	}
}
