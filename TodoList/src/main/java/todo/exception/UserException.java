package todo.exception;

public class UserException extends Exception {

	public UserException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

	public UserException(String errorMessage) {
		super(errorMessage);
	}
}
