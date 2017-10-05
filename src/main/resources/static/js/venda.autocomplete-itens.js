Brewer = Brewer || {}

Brewer.AutoComplete = (function () {
	
	function AutoComplete() {
		this.inputSkuOuNome = $('.js-sku-nome-cerveja-input');
		var htmlTemplateAutoComplete = $('#template-autocomplete-cerveja').html();
		
		this.template = Handlebars.compile(htmlTemplateAutoComplete)
	}
	
	AutoComplete.prototype.iniciar = function() {
		var options = {
			url: function(skuOuNome) {
				return '/brewer/cervejas?skuOuNome=' +skuOuNome;
			},
			
			getValue: 'nome',
			minCharNumber: 3,
			requestDelay: 300,
			
			ajaxSettings: {
				contentType: 'application/json'
			},	
			
			template: {
				type: 'custom',
				method: function(nome, cerveja) {
					
					cerveja.valorFormatado = Brewer.formatarMoeda(cerveja.valor);
					return this.template(cerveja);
					
				}.bind(this)
			}
		};
		
		this.inputSkuOuNome.easyAutocomplete(options);
	}
	
	return AutoComplete;
}());

$(function() {
	
	var autoComplete = new Brewer.AutoComplete();
	autoComplete.iniciar();
});