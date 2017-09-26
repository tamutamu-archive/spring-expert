package brewer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import brewer.repository.Cidades;

import brewer.model.Cidade;

@Controller
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private Cidades cidades;
	
	@RequestMapping("cidades/nova")
	public String novo() {
		return "cidade/CadastroCidade";
	}
	
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(@RequestParam(name="estado", defaultValue="-1") Long codigoEstado) {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cidades.findByEstadoCodigo(codigoEstado);
	}
}
