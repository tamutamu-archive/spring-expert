package brewer.service.event.cerveja;

import org.springframework.util.StringUtils;

import brewer.model.Cerveja;

public class CervejaSalvaEvent {

	private Cerveja cerveja;

	public CervejaSalvaEvent(Cerveja cerveja) {
		super();
		this.cerveja = cerveja;
	}

	public CervejaSalvaEvent() {
	}

	public Cerveja getCerveja() {
		return cerveja;
	}

	public void setCerveja(Cerveja cerveja) {
		this.cerveja = cerveja;
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(cerveja.getFoto());
	}
	
	public boolean isNovaFoto() {
		return cerveja.isNovaFoto();
	}
}
