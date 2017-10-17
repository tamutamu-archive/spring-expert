package brewer.repository.helper.cidade;

import org.springframework.data.domain.Page;


import org.springframework.data.domain.Pageable;

import brewer.model.Cidade;
import brewer.repository.filter.CidadeFilter;

public interface CidadesQueries {
	
	public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pageable);
	public Cidade buscarComEstado(Long codigo);
}
