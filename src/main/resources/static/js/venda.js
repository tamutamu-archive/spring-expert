Brewer.Venda = (function () {
	
	function Venda(tabelaItens) {
		this.tabelaItens = tabelaItens;
		this.valorTotalBox = $('.js-valor-total-box');
		this.inputValorFrete = $('#valorFrete');
		this.inputValorDesconto = $('#valorDesconto');
		this.valorTotalBoxContainer = $('.js-valor-total-box-container');
		
		this.valorTotalItens = this.tabelaItens.valorTotal();
		this.valorFrete = this.inputValorFrete.data('valor');
		this.valorDesconto = this.inputValorDesconto.data('valor');
	}
	
	Venda.prototype.iniciar = function() {
		this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtualizada.bind(this));
		this.inputValorFrete.on('keyup', onFreteAtualizado.bind(this));
		this.inputValorDesconto.on('keyup', onDescontoAtualizado.bind(this));
		
		this.tabelaItens.on('tabela-itens-atualizada', onValoresAlterados.bind(this));
		this.inputValorFrete.on('keyup', onValoresAlterados.bind(this));
		this.inputValorDesconto.on('keyup',onValoresAlterados.bind(this));
		
		onValoresAlterados.call(this);
	}
	
	function onTabelaItensAtualizada(evento, valorTotalItens) {
		this.valorTotalItens = valorTotalItens == null ? 0 : valorTotalItens;
	}
	
	function onFreteAtualizado(evento) {
		this.valorFrete = Brewer.recuperarValor($(evento.target).val());
	}
	
	function onDescontoAtualizado(evento) {
		this.valorDesconto = Brewer.recuperarValor($(evento.target).val());
	}	
	
	function onValoresAlterados() {
		var valorTotal = numeral(this.valorTotalItens) + numeral(this.valorFrete) - numeral(this.valorDesconto);
		this.valorTotalBox.html(Brewer.formatarMoeda(valorTotal));
		this.valorTotalBoxContainer.toggleClass('negativo', valorTotal < 0);
	}
	
	return Venda;
	
}());

$(function() {
	
	var autoComplete = new Brewer.AutoComplete();
	autoComplete.iniciar();
	
	var tabelaItens = new Brewer.TabelaItens(autoComplete);
	tabelaItens.iniciar();
	
	var venda = new Brewer.Venda(tabelaItens);
	venda.iniciar();
});
