var Brewer = Brewer || {};

Brewer.MascaraCpfCnpj = (function () {
	
	function MascaraCpfCnpj() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');	//captura por classe css
		this.labelCpfCnpj = $('[for=cpfOuCnpj');	//captura o label do input 
		this.inputCpfCnpj = $('#cpfOuCnpj');		//captura pelo id
	
	}
	
	MascaraCpfCnpj.prototype.iniciar = function() {
		this.radioTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));
		
		var tipoPessoaSelecionada = this.radioTipoPessoa.filter(':checked')[0];
		
		if(tipoPessoaSelecionada) {
			aplicarMascara.call(this, $(tipoPessoaSelecionada));
		}
	}
	
	function onTipoPessoaAlterado(evento) {
		var tipoPessoaSelecionada = $(evento.currentTarget);
		
		aplicarMascara.call(this, tipoPessoaSelecionada);
		this.inputCpfCnpj.val('');
	}
	
	function aplicarMascara(tipoPessoaSelecionada) {
		this.labelCpfCnpj.text(tipoPessoaSelecionada.data('documento'));	//Muda o texto da label do input para CPF ou CNPJ	
		this.inputCpfCnpj.mask(tipoPessoaSelecionada.data('mascara'));
		
		this.inputCpfCnpj.removeAttr('disabled');		
	}
	
	return MascaraCpfCnpj; 
	
}());


$(function() {
	
	var mascaraCpfCnpj = new Brewer.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
});