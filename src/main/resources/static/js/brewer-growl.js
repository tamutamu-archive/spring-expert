var Brewer = Brewer || {}; 

Brewer.Growl = (function() {
	
	function Growl() {}
	
	Growl.prototype.exibe = function(tipo, objeto) {
		
		if(tipo === 'danger') {
			objeto.forEach(function(erro) {
				ExibeGrowl(erro.message, 'danger', 'glyphicon-remove');
			});	
		
		}else if(tipo === 'success') {
			ExibeGrowl(objeto, 'success', 'glyphicon-ok');		
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
