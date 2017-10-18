package brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import brewer.repository.Cervejas;
import brewer.repository.Clientes;
import brewer.repository.Vendas;

@Controller
public class DashboardController {

	@Autowired
	private Vendas vendas;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Cervejas cervejas;
	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		
		mv.addObject("vendasNoAno", vendas.valorTotalNoAno());
		mv.addObject("vendasNoMes", vendas.valorTotalNoMes());
		mv.addObject("clientesTotal", clientes.count());
		mv.addObject("ticketMedio", vendas.valorTicketMedio());
		mv.addObject("valorItensEstoque", cervejas.valorItensEstoque());
		
		return mv;
	}
}
