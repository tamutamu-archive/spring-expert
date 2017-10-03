package brewer.service.exception;

public class SenhaObrigatoriaNovoUsuarioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SenhaObrigatoriaNovoUsuarioException(String message) {
		super(message);
	}
}
