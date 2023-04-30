package one.digitalinnovation.lppsp.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5482594407568983071L;

	public ResourceNotFoundException(String message) {
		this.message = message;
	}

	public ResourceNotFoundException(String message, String value) {
		this.value = value;
		this.message = message + value;
	}

	private String message;
	private String value;

	public String getMessage() {
		return message;
	}

	public String getValue() {
		return value;
	}
	
}
