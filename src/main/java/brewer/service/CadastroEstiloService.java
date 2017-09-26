package brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brewer.model.Estilo;
import brewer.repository.Estilos;
import brewer.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {

	@Autowired
	private Estilos estilos;
	
	@Transactional
	public Estilo salvar(Estilo estilo) {
		
		if(estilos.findByNomeIgnoreCase(estilo.getNome()).isPresent()) {
			throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
		
		}
			
		return estilos.saveAndFlush(estilo);
	}
}
