package brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import brewer.model.StatusVenda;
import brewer.model.Venda;
import brewer.repository.Vendas;
import brewer.service.event.venda.VendaEvent;

@Service
public class CadastroVendaService {
	
	@Autowired
	private Vendas vendas;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public Venda salvar(Venda venda) {
		
		if(venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		
		}else {
			Venda vendaExistente = vendas.findOne(venda.getCodigo());
			venda.setDataCriacao(vendaExistente.getDataCriacao());
		}
		
		if(venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), 
				venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON));
		}
		
		vendas.saveAndFlush(venda);
		
		return venda;
	}
	
	@PreAuthorize("#venda.vendedor == principal.usuario or hasRole('CANCELAR_VENDA')")
	@Transactional
	public void cancelar(Venda venda) {
		Venda vendaExistente = vendas.findOne(venda.getCodigo());
		vendaExistente.setStatus(StatusVenda.CANCELADA);
		vendas.save(vendaExistente);
	}

	@Transactional
	public Venda emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		
		/* Evento para atualizar a quantidade de estoque */ 
		publisher.publishEvent(new VendaEvent(venda));
		
		return salvar(venda);
	}
}
