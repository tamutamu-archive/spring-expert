package brewer.repository.filter.helper.venda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import brewer.model.Venda;
import brewer.repository.filter.VendaFilter;

public interface VendasQueries {
	
	public Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable);
	public Venda buscarComItens(Long codigo);
}
