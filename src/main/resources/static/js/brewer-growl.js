var Brewer = Brewer || {}; 

Brewer.Growl = (function() {
	
	function Growl(tipo, objeto) {
		this.tipo = tipo;
		this.objeto = objeto;
	}
	
	Growl.prototype.exibe = function() {
		
		if(this.tipo === 'danger') {
			this.objeto.forEach(function(erro) {
				ExibeGrowl(erro.message, 'danger', 'glyphicon-remove');
			});	
		
		}else if(this.tipo === 'success') {
			ExibeGrowl(this.objeto, 'success', 'glyphicon-ok');		
		}
	}

	function ExibeGrowl(mensagem, tipo, icone) {
		
		$.notify({
			icon: 'glyphicon ' +icone,
			message: mensagem,

		},{	
			type: tipo,
			allow_dismiss: true,
			delay: 3000,
			offset: {
				x: 20,
				y: 70	
			},
			placement: {
				from: "bottom",
				align: "right"
			},
			animate: {
				enter: 'animated fadeInDown',
				exit: 'animated fadeOutUp'
			}
		});
	}
	
	return Growl;
	
}());
