package brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brewer.model.Cerveja;
import brewer.model.Estilo;
import brewer.repository.Estilos;
import brewer.service.exception.ImpossivelExcluirEntidadeException;
import brewer.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {

	@Autowired
	private Estilos estilos;
	
	@Transactional
	public Estilo salvar(Estilo estilo) {
		
		if(estilo.isNovo() && estilos.findByNomeIgnoreCase(estilo.getNome()).isPresent()) {
			throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
		}
			
		return estilos.saveAndFlush(estilo);
	}

	@Transactional 
	public void excluir(Estilo estilo) {
		
		try { 
			
			estilos.delete(estilo);
			estilos.flush();
		
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir, estilo em uso");
		}
	}	
}
