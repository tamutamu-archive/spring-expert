package brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brewer.model.Cerveja;
import brewer.repository.Cervejas;
import brewer.service.event.cerveja.CervejaSalvaEvent;
import brewer.service.exception.ImpossivelExcluirEntidadeException;
import brewer.storage.FotoStorage;

@Service
public class CadastroCervejaService {

	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@Transactional
	public void salvar(Cerveja cerveja) {
		cervejas.save(cerveja);
	
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}
	
	@Transactional 
	public void excluir(Cerveja cerveja) {
		
		try { 
			
			String foto = cerveja.getFoto();
			
			cervejas.delete(cerveja);
			cervejas.flush(); 
			
			fotoStorage.excluir(foto);
		
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir cerveja. Já foi utilizada em alguma venda");
		}
	}
	
}
