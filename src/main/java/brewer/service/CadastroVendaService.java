package brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brewer.model.StatusVenda;
import brewer.model.Venda;
import brewer.repository.Vendas;

@Service
public class CadastroVendaService {
	
	@Autowired
	private Vendas vendas;
	
	@Transactional
	public Venda salvar(Venda venda) {
		
		if(venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		}
		
		if(venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), 
				venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON));
		}
		
		vendas.saveAndFlush(venda);
		
		return venda;
	}

	@Transactional
	public Venda emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		
		return salvar(venda);
	}
}
