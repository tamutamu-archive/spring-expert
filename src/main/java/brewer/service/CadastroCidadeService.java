package brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brewer.model.Cidade;
import brewer.repository.Cidades;
import brewer.service.exception.CidadeJaExisteNoEstadoException;
import brewer.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private Cidades cidades; 

	@Transactional	
	public void salvar(Cidade cidade) {
		
		Optional<Cidade> existente = cidades.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
		
		if(existente.isPresent()) {
			throw new CidadeJaExisteNoEstadoException("Já existe uma cidade chamada " +cidade.getNome()+ " cadastrada neste estado");
		}
				
		cidades.save(cidade);
		
	}
	
	@Transactional
	public void excluir(Cidade cidade) {
		
		try {
			
			cidades.delete(cidade);
			cidades.flush();
			
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir, cidade está relacionada com algum cliente.");
		}		
	}
}
