package brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import brewer.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable {

	private MultipartFile[] arquivos;
	private DeferredResult<FotoDTO> resultado;
	private FotoStorage fotoStorage;
	
	public FotoStorageRunnable(MultipartFile[] arquivos, DeferredResult<FotoDTO> resultado, FotoStorage fotoStorage) {
		this.arquivos = arquivos;
		this.resultado = resultado;
		this.fotoStorage = fotoStorage;
	};
	
	@Override
	public void run() {
		
		String nomeFoto = this.fotoStorage.salvarTemporariamente(arquivos);
		String contentType = arquivos[0].getContentType();
		
		resultado.setResult(new FotoDTO(nomeFoto, contentType));
	}

}
