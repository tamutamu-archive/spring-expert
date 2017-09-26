package brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import brewer.dto.FotoDTO;
import brewer.storage.FotoStorage;
import brewer.storage.FotoStorageRunnable;

@RestController	
@RequestMapping("/fotos")
public class FotosController {
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@PostMapping 
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] arquivos) {	//PostMapping == RequestMapping(method=RequestMethod.POST)
		
		DeferredResult<FotoDTO> resultado = new DeferredResult<>();
				
		Thread thread = new Thread(new FotoStorageRunnable(arquivos, resultado, fotoStorage));
		thread.start();
		
		return resultado;
	}
	
	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperarFotoTemporaria(@PathVariable("nome") String nomeFoto) {
		return fotoStorage.recuperarFotoTemporaria(nomeFoto);
	}

	@GetMapping("/{nome:.*}")
	public byte[] recuperarFoto(@PathVariable String nome) {
		return fotoStorage.recuperar(nome);
	}
	
}
