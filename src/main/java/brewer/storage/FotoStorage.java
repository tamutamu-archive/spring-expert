package brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	public String salvarTemporariamente(MultipartFile[] arquivos);

	public byte[] recuperarFotoTemporaria(String nomeFoto);

	public void salvar(String foto);

	public byte[] recuperar(String nome);
	
	public byte[] recuperarThumbnail(String fotoCerveja);

	public void excluir(String foto);
}
