package brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import brewer.model.TipoPessoa;
import brewer.repository.Estados;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private Estados estados;
	
	/* Se for get irá chamar este método */	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estados.findAll());
		
		return mv;
	}
	
}
