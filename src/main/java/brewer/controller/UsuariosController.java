package brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brewer.model.Usuario;
import brewer.repository.Usuarios;
import brewer.service.CadastroUsuarioService;
import brewer.service.exception.EmailJaCadastradoException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	/* Se for get irá chamar este método */	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		
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
		}
		
		attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
		return new ModelAndView("redirect:/usuarios/novo");		//redirect faz uma nova requisição para uma url		
	}
}
