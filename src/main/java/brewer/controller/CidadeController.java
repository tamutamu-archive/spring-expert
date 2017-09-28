package brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brewer.repository.Cidades;
import brewer.repository.Estados;
import brewer.repository.filter.CidadeFilter;
import brewer.repository.filter.ClienteFilter;
import brewer.service.CadastroCidadeService;
import brewer.service.exception.CidadeJaExisteNoEstadoException;
import brewer.controller.page.PageWrapper;
import brewer.model.Cidade;
import brewer.model.Cliente;

@Controller
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private Cidades cidades;
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@RequestMapping("/nova")
	public ModelAndView novo(Cidade cidade) {
		
		ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
		
		mv.addObject("estados", estados.findAll());
		
		return mv;
	}
	
	/* 
	 * allEntries = true invalida todas a entradas no cache cidades 
	 * key = #cidade.estado.codigo invalida apenas o cache da respectiva chave
	 * CacheEvict = remove o objeto do cache
	 * 
	 */
	
	@PostMapping("/nova")
	@CacheEvict(value = "cidades", key = "#cidade.estado.codigo", condition = "#cidade.temEstado()") 
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {				
			return novo(cidade);				
		}										 
			
		try {
			cadastroCidadeService.salvar(cidade);
			
		} catch(CidadeJaExisteNoEstadoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(cidade);
		}
		
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
		return new ModelAndView("redirect:/cidades/nova");
	}

	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter, BindingResult result, 
			@PageableDefault(size = 6) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("cidade/PesquisaCidade");

		PageWrapper<Cidade> paginaWrapper = new PageWrapper<>(cidades.filtrar(cidadeFilter, pageable), httpServletRequest);
		mv.addObject("estados", estados.findAll());
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}	
	
	/*
	 * Cacheable - coloca os objetos em cache com o nome de cidades 
	 * key = é a chave de cada objeto em cache da lista cidades
	 */
	
	@Cacheable(value = "cidades", key = "#codigoEstado")
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(@RequestParam(name="estado", defaultValue="-1") Long codigoEstado) {
		
		try {
			Thread.sleep(1000);	//Simula um atraso no servidor enquanto ele realiza a consulta para que o gif de carregando seja exibido
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return cidades.findByEstadoCodigo(codigoEstado);
	}
	
}
