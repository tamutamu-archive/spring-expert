var Brewer = Brewer || {};

Brewer.MascaraCpfCnpj = (function () {
	
	function MascaraCpfCnpj() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');	//captura por classe css
		this.labelCpfCnpj = $('[for=cpfOuCnpj');	//captura o label do input 
		this.inputCpfCnpj = $('#cpfOuCnpj');		//captura pelo id
	
	}
	
	MascaraCpfCnpj.prototype.iniciar = function() {
		this.radioTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));
	}
	
	function onTipoPessoaAlterado(evento) {
		var tipoPessoaSelecionada = $(evento.currentTarget);
		
		this.labelCpfCnpj.text(tipoPessoaSelecionada.data('documento'));	//Muda o texto da label do input para CPF ou CNPJ
		
		this.inputCpfCnpj.mask(tipoPessoaSelecionada.data('mascara'));
		this.inputCpfCnpj.val('');
		this.inputCpfCnpj.removeAttr('disabled');
		
	}
	
	return MascaraCpfCnpj; 
	
}());


$(function() {
	
	var mascaraCpfCnpj = new Brewer.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
});