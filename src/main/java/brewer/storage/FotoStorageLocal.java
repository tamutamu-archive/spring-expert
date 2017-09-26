package brewer.storage;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {

	private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		this.local = getDefault().getPath("/opt/brewer");
		criarPastas();
	}

	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}
	
	@Override
	public String salvarTemporariamente(MultipartFile[] arquivos) {
		
		String novoNome = null;
		
		if(arquivos != null && arquivos.length > 0) {
			MultipartFile arquivo = arquivos[0];
			
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath() + getDefault().getSeparator() +novoNome));
			
			}catch(IOException e) {
				throw new RuntimeException("Erro salvando a foto na pasta temporária");
			}
		}
		return novoNome;
	}

	@Override
	public byte[] recuperarFotoTemporaria(String nomeFoto) {
		try {
			return Files.readAllBytes(localTemporario.resolve(nomeFoto));
		
		}catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto temporaria ", e);
		}
	}
	
	
	@Override
	public byte[] recuperar(String nomeFoto) {
		try {
			return Files.readAllBytes(local.resolve(nomeFoto));
		
		}catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto", e);
		}
	}

	@Override
	public void salvar(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
	
		}catch (IOException e) {
			throw new RuntimeException("Erro movendo a foto para o destino final ", e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);	
		
		}catch (IOException e) {
			throw new RuntimeException("Erro gerando o thumbnail ", e);
		}
	}

	private void criarPastas() {
		try {
			
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			
			Files.createDirectories(this.localTemporario);
			
			if(logger.isDebugEnabled()) {
				logger.debug("Pastas criadas para tirar fotos");
				logger.debug("Pasta default >>> " +this.local.toAbsolutePath());
				logger.debug("Pasta temporária >>> " +this.localTemporario.toAbsolutePath());
			}
			
		}catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto - ", e);
		}
		
	}
	
	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" +nomeOriginal;
		
		if(logger.isDebugEnabled()) {
			logger.debug(String.format("Nome original %s, novo nome %s", nomeOriginal, novoNome));
		}
		
		return novoNome;
	}	
}
