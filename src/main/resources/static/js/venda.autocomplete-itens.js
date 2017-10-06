Brewer = Brewer || {}

Brewer.AutoComplete = (function () {
	
	function AutoComplete() {
		this.inputSkuOuNome = $('.js-sku-nome-cerveja-input');
		var htmlTemplateAutoComplete = $('#template-autocomplete-cerveja').html();
		
		this.template = Handlebars.compile(htmlTemplateAutoComplete);
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	AutoComplete.prototype.iniciar = function() {
		var options = {
			url: function(skuOuNome) {
				return  this.inputSkuOuNome.data('url') + '?skuOuNome=' +skuOuNome;
			}.bind(this),
			
			getValue: 'nome',
			minCharNumber: 3,
			requestDelay: 300,
			
			ajaxSettings: {
				contentType: 'application/json'
			},	
			
			template: {
				type: 'custom',
				method: template.bind(this)
			},
			list: {
				onChooseEvent: onItemSelecionado.bind(this)
			}
		};
		
		this.inputSkuOuNome.easyAutocomplete(options);
	}
	
	function onItemSelecionado(evento, item) {
		/* Criando um evento 'item-selecionado' */
		this.emitter.trigger('item-selecionado', this.inputSkuOuNome.getSelectedItemData());
		this.inputSkuOuNome.val('');
		this.inputSkuOuNome.focus();
	}
	
	function template(nome, cerveja) {
			
		cerveja.valorFormatado = Brewer.formatarMoeda(cerveja.valor);
		return this.template(cerveja);	
	}
	
	return AutoComplete;
}());

$(function() {
	
	var autoComplete = new Brewer.AutoComplete();
	autoComplete.iniciar();
});