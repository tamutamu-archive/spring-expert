Brewer = Brewer || {};

Brewer.BotaoSubmit = (function () {
	
	function BotaoSubmit() {
		
		this.formulario = $('.js-formulario-principal');
		this.submitBtn = $('.js-submit-btn');
		
	}
	
	BotaoSubmit.prototype.iniciar = function() {
		this.submitBtn.on('click', onSubmitBtn.bind(this));
	}
	
	function onSubmitBtn(evento) {
		evento.preventDefault();
		
		var botaoClicado = $(evento.target);
		var acao = botaoClicado.data('acao');
		
		var acaoInput = $('<input>');
		acaoInput.attr('name', acao);
		
		this.formulario.append(acaoInput);
		this.formulario.submit();
	}
	
	return BotaoSubmit; 
}());

$(function() {

	var botaoSubmit = new Brewer.BotaoSubmit();
	botaoSubmit.iniciar();
});