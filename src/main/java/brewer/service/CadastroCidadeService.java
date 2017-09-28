package brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brewer.model.Cidade;
import brewer.repository.Cidades;
import brewer.service.exception.CidadeJaExisteNoEstadoException;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private Cidades cidades; 

	@Transactional	
	public void salvar(Cidade cidade) {
		
		Optional existente = cidades.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
		
		if(existente.isPresent()) {
			throw new CidadeJaExisteNoEstadoException("Já existe uma cidade chamada " +cidade.getNome()+ " cadastrada neste estado");
		}
		
		cidades.save(cidade);
		
	}
}
