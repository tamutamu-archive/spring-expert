Brewer = Brewer || {}

Brewer.PesquisaRapidaClientes = (function () {
	
	function PesquisaRapidaClientes() {
		this.pesquisaRapidaClientesModal = $('#pesquisaRapidaClientes');
		this.nomeInput = $('#nomeClienteModal');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-clientes-btn');
		this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaClientes');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-clientes').html();
		this.mensagemErro = $('.js-mensagem-erro');
		
		//Compilando o template html com o Handlbars
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
	}
	
	PesquisaRapidaClientes.prototype.iniciar = function() {
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicado.bind(this));
		this.pesquisaRapidaClientesModal.on('shown.bs.modal', onModalShow.bind(this));
	}
	
	function onModalShow() {
		this.nomeInput.focus();
	}
	
	function onPesquisaRapidaClicado(event) {
		
		event.preventDefault();
		
		$.ajax({
			url: this.pesquisaRapidaClientesModal.find('form').attr('action'),
			method: 'GET',
			contentType: 'application/json',
			data: {
				nome: this.nomeInput.val()
			},
			success: onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this),
		});		
	}
	
	function onErroPesquisa() {
		this.mensagemErro.removeClass('hidden');
	}
	
	function onPesquisaConcluida(resultado) {
		this.mensagemErro.addClass('hidden');

		var html = this.template(resultado)
		this.containerTabelaPesquisa.html(html);

		var tabelaPesquisaRapida = new Brewer.TabelaPesquisaRapida(this.pesquisaRapidaClientesModal);
		tabelaPesquisaRapida.iniciar();	
	}
	
	return PesquisaRapidaClientes;
	
}());

Brewer.TabelaPesquisaRapida = (function() {

	function TabelaPesquisaRapida(modal) {
		this.modalCliente = modal;
		this.cliente = $('.js-cliente-pesquisa-rapida');
	}	
	
	TabelaPesquisaRapida.prototype.iniciar = function() {
		this.cliente.on('click', onClienteSelecionado.bind(this));
	}
	
	function onClienteSelecionado(evento) {
		this.modalCliente.modal('hide');

		var clienteSelecionado = $(evento.currentTarget);
		$('#nomeCliente').val(clienteSelecionado.data('nome'));
		$('#codigoCliente').val(clienteSelecionado.data('codigo'));
	}
	
	return TabelaPesquisaRapida; 
}());


$(function () {
	
	var pesquisaRapidaClientes = new Brewer.PesquisaRapidaClientes(); 
	pesquisaRapidaClientes.iniciar();
	
});