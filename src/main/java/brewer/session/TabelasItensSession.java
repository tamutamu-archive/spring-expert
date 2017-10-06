package brewer.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import brewer.model.Cerveja;
import brewer.model.ItemVenda;

@SessionScope
@Component
public class TabelasItensSession {

	private Set<TabelaItensVenda> tabelas = new HashSet<>();

	public void adicionarItem(String uuid, Cerveja cerveja, int quantidade) {
		TabelaItensVenda tabela = buscarTabelPorUid(uuid);
		
		tabela.adicionarItem(cerveja, quantidade);
		tabelas.add(tabela);
		
	}


	public void alterarQuantidadeItens(String uuid, Cerveja cerveja, Integer quantidade) {
		TabelaItensVenda tabela = buscarTabelPorUid(uuid);
		tabela.alterarQuantidadeItens(cerveja, quantidade);
	}

	public void excluirItem(String uuid, Cerveja cerveja) {
		TabelaItensVenda tabela = buscarTabelPorUid(uuid);
		tabela.excluirItem(cerveja);
	}

	public List<ItemVenda> getItens(String uuid) {
		TabelaItensVenda tabela = buscarTabelPorUid(uuid);
		return tabela.getItens();
	}

	public Object getValorTotal(String uuid) {
		return buscarTabelPorUid(uuid).getValorTotal();
	}
	
	private TabelaItensVenda buscarTabelPorUid(String uuid) {
		TabelaItensVenda tabela = tabelas.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny().orElse(new TabelaItensVenda(uuid));
		return tabela;
	}


}
