package brewer.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import brewer.storage.FotoStorage;

@Component
public class CervejaListener {

	@Autowired
	private FotoStorage fotoStorage;
	
	@EventListener(condition = "#evento.temFoto()")			//So vai executar este método se a cerveja tiver foto
	public void cervejaSalva(CervejaSalvaEvent evento) {
		System.out.println("Cerveja sendo salva...");
		
		fotoStorage.salvar(evento.getCerveja().getFoto());
	}
	
}
