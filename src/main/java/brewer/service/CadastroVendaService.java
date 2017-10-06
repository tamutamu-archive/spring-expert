package brewer.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brewer.model.ItemVenda;
import brewer.model.Venda;
import brewer.repository.Vendas;

@Service
public class CadastroVendaService {
	
	@Autowired
	private Vendas vendas;
	
	@Transactional
	public void salvar(Venda venda) {
		
		if(venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		}
		
		BigDecimal valorTotalItens = venda.getItensVenda().stream()
				.map(ItemVenda::getValorTotal)
				.reduce(BigDecimal::add)
				.get();
		
		BigDecimal valorTotal = calcularValorTotal(valorTotalItens, venda.getValorFrete(), venda.getValorDesconto());
		
		if(venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHorarioEntrega()));
		}
		
		venda.setValorTotal(valorTotal);
		
		vendas.save(venda);
	}

	private BigDecimal calcularValorTotal(BigDecimal valorTotalItens, BigDecimal valorFrete, BigDecimal valorDesconto) {
		BigDecimal valorTotal = valorTotalItens
				.add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
				.subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
		
		return valorTotal;
	}
}
