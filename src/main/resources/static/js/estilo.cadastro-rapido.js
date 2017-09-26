var Brewer = Brewer || {}; 

Brewer.EstiloCadastroRapido = (function() {
	
	function EstiloCadastroRapido() {
		this.modal = $('#modalCadastroRapidoEstilo');
		this.form = this.modal.find('form');
		this.botaoSalvar = this.modal.find('.js-cadastro-rapido-estilo-btn-salvar');
		this.inputNomeEstilo = $('#nomeEstilo');
		this.url = this.form.attr('action');
		this.containerMensagemErro = $('.js-mensagem-cadastro-rapido-estilo');
	}
	
	EstiloCadastroRapido.prototype.iniciar = function() {
		this.form.on('submit', function(event) { event.preventDefault(); });
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modal.on('hide.bs.modal', onModalClose.bind(this));	//Limpando o input após fechar o modal
		this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this));
	}

	function onModalShow() {
		this.inputNomeEstilo.focus();
	}

	function onModalClose() {
		this.inputNomeEstilo.val('');
		this.form.find('.form-group').removeClass('has-error');
		this.containerMensagemErro.addClass('hidden');
	}

	function onBotaoSalvarClick() {
		var nomeEstilo = this.inputNomeEstilo.val().trim();
		$.ajax({
			url: this.url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({nome: nomeEstilo}),
			error: onErrorSalvandoEstilo.bind(this),
			success: onEstiloSalvo.bind(this)
		});
	}
	
	function onErrorSalvandoEstilo(obj) {
		var mensagemErro = obj.responseText;
		this.containerMensagemErro.removeClass('hidden');
		this.containerMensagemErro.html('<span>' +mensagemErro+ '</span>');
		this.form.find('.form-group').addClass('has-error');
	}
	
	function onEstiloSalvo(estilo) {
		var comboEstilo = $("#estilo");
		comboEstilo.append('<option value=' +estilo.codigo+ '>' +estilo.nome+ '</option>');
		comboEstilo.val(estilo.codigo);
		this.modal.modal('hide');
		
		var brewerGrowl = new Brewer.Growl();
		brewerGrowl.exibe('success', 'Estilo salvo com sucesso');
	}	
	
	return EstiloCadastroRapido;
	
}());

$(function(){
	
	var estiloCadastroRapido = new Brewer.EstiloCadastroRapido();
	estiloCadastroRapido.iniciar();
	
});