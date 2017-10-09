package brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import brewer.model.Venda;
import brewer.repository.filter.helper.venda.VendasQueries;

public interface Vendas extends JpaRepository<Venda, Long>, VendasQueries{

}
