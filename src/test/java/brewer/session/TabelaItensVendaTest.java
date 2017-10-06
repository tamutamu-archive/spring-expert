package brewer.session;

import static org.junit.Assert.*;


import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import brewer.model.Cerveja;
import brewer.session.TabelaItensVenda;

public class TabelaItensVendaTest {

	private TabelaItensVenda tabelaItensVenda;
	
	@Before
	public void setUp() {
		this.tabelaItensVenda = new TabelaItensVenda("1");
	}
	
	@Test
	public void deveCalcularValorTotalSemItens() throws Exception {
		assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComUmItem() throws Exception {
		Cerveja cerveja = new Cerveja();
		BigDecimal valor = new BigDecimal("8.90");
		cerveja.setValor(valor);
		
		tabelaItensVenda.adicionarItem(cerveja, 1);
		
		assertEquals(valor, tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComVariosItens() throws Exception {

		Cerveja cerveja1 = new Cerveja();
		cerveja1.setCodigo(1L);
		BigDecimal valor1 = new BigDecimal("7.00");
		cerveja1.setValor(valor1);

		Cerveja cerveja2 = new Cerveja();
		cerveja2.setCodigo(2L);
		BigDecimal valor2 = new BigDecimal("8.00");
		cerveja2.setValor(valor2);

		Cerveja cerveja3 = new Cerveja();
		cerveja3.setCodigo(3L);
		BigDecimal valor3 = new BigDecimal("5.50");
		cerveja3.setValor(valor3);
		
		tabelaItensVenda.adicionarItem(cerveja1, 1);
		tabelaItensVenda.adicionarItem(cerveja2, 2);
		tabelaItensVenda.adicionarItem(cerveja3, 3);
		
		assertEquals(new BigDecimal("39.50"), tabelaItensVenda.getValorTotal());
		
	}
	
	@Test
	public void deveManterTamanhoDaListaParaMesmasCervejas() throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setCodigo(1L);
		c1.setValor(new BigDecimal("4.50"));
		
		tabelaItensVenda.adicionarItem(c1, 1);
		tabelaItensVenda.adicionarItem(c1, 1);
		
		assertEquals(1, tabelaItensVenda.getTotal());
		assertEquals(new BigDecimal("9.00"), tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveAlterarQuantidadeDoItem() throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setCodigo(1L);
		c1.setValor(new BigDecimal("4.50"));
		
		tabelaItensVenda.adicionarItem(c1, 1);
		tabelaItensVenda.alterarQuantidadeItens(c1, 3);
		
		assertEquals(1, tabelaItensVenda.getTotal());
		assertEquals(new BigDecimal("13.50"), tabelaItensVenda.getValorTotal());
	}
	@Test
	public void deveExcluirItem() throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setCodigo(1L);
		c1.setValor(new BigDecimal("8.90"));
		
		Cerveja c2 = new Cerveja();
		c2.setCodigo(2L);
		c2.setValor(new BigDecimal("4.99"));
		
		Cerveja c3 = new Cerveja();
		c3.setCodigo(3L);
		c3.setValor(new BigDecimal("2.00"));
		
		tabelaItensVenda.adicionarItem(c1, 1);
		tabelaItensVenda.adicionarItem(c2, 2);
		tabelaItensVenda.adicionarItem(c3, 1);
		
		tabelaItensVenda.excluirItem(c2);
		
		assertEquals(2, tabelaItensVenda.getTotal());
		assertEquals(new BigDecimal("10.90"), tabelaItensVenda.getValorTotal());
	}
		
}
