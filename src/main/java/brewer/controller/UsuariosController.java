package brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import brewer.controller.page.PageWrapper;
import brewer.model.Usuario;
import brewer.repository.Grupos;
import brewer.repository.Usuarios;
import brewer.repository.filter.UsuarioFilter;
import brewer.service.CadastroUsuarioService;
import brewer.service.StatusUsuario;
import brewer.service.exception.EmailJaCadastradoException;
import brewer.service.exception.SenhaObrigatoriaNovoUsuarioException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	@Autowired 
	private Grupos grupos;	
	
	@Autowired
	private Usuarios usuarios;
	
	/* Se for get irá chamar este método */	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("grupos", grupos.findAll());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {

		if(result.hasErrors()) {	
			return novo(usuario);	//foward faz o acesso direto para a view
		}	
		
		try {
			
			cadastroUsuarioService.salvar(usuario);
			
		}catch (EmailJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
			
		}catch (SenhaObrigatoriaNovoUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		
		attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
		return new ModelAndView("redirect:/usuarios/novo");		//redirect faz uma nova requisição para uma url		
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result, 
			@PageableDefault(size = 6) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("/usuario/PesquisaUsuario");
		
		PageWrapper<Usuario> pageWrapper = new PageWrapper<>(usuarios.filtrar(usuarioFilter, pageable), httpServletRequest);
		mv.addObject("pagina", pageWrapper);
		mv.addObject("grupos", grupos.findAll());
		
		return mv;
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualziarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario statusUsuario) {
		cadastroUsuarioService.alterarStatus(codigos, statusUsuario);
	}
}
