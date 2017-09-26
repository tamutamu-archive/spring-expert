package brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import brewer.model.Cerveja;

@Controller
public class UsuariosController {

	/* Se for get irá chamar este método */	
	@RequestMapping("/usuarios/novo")
	public String novo(Cerveja cerveja) {
		
		return "usuario/CadastroUsuario";
	}
}
