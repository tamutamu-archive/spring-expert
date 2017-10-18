package brewer.repository.filter.helper.venda;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import brewer.model.Venda;
import brewer.repository.filter.VendaFilter;
import brewer.dto.VendaMes;
import brewer.dto.VendaOrigem;

public interface VendasQueries {
	
	public Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable);
	public Venda buscarComItens(Long codigo);
	public BigDecimal valorTotalNoAno();
	public BigDecimal valorTotalNoMes();
	public BigDecimal valorTicketMedio();
	public List<VendaMes> totalPorMes();
	public List<VendaOrigem> totalPorOrigem();
}
