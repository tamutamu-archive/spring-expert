package brewer.service.exception;

public class CidadeJaExisteNoEstadoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CidadeJaExisteNoEstadoException(String message) {
		super(message);
	}
}
