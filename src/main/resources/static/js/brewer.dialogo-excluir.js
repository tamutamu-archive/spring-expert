Brewer.DialogoExcluir = (function() {
	
	function DialogoExcluir() {
		this.exclusaoBtn = $('.js-exclusao-btn');
	}
	
	DialogoExcluir.prototype.iniciar = function() {
		this.exclusaoBtn.on('click', onExcluirClicado.bind(this));
		
		if(window.location.search.indexOf('excluido') > -1) {
			var brewerGrowl = new Brewer.Growl('info', 'Excluído com sucesso');
			brewerGrowl.exibe();
		}
	}
	
	function onExcluirClicado(evento) {
		evento.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');
		
		swal({
			title: 'Tem certeza?',
			text: 'Excluir "' +objeto+ '" ? Você não poderá recuperar depois.',
			showCancelButton: true, 
			confirmButtonColor: '#DD6855',
			confirmButtonText: 'Sim, exclua agora!',
			closeOnConfirm: true,
			
		}, onExcluirConfirmado.bind(this, url));
	}
	
	function onExcluirConfirmado(url) {
		$.ajax({
			url: url,
			method: 'DELETE',
			success: onExcluidoSucesso.bind(this),
			error: onExcluirError.bind(this),
		})
	}
	
	function onExcluidoSucesso() {
		var urlAtual = window.location.href; 
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novaUrl;
		
	}
	
	function onExcluirError(e) {
		var brewerGrowl = new Brewer.Growl('dangerSimples', e.responseText);
		brewerGrowl.exibe();		
	}
			
	return DialogoExcluir;
	
}());

$(function() {
	
	var dialogo = new Brewer.DialogoExcluir();
	dialogo.iniciar();
	
});