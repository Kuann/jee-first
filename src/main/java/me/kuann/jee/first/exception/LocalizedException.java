package me.kuann.jee.first.exception;

public class LocalizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public LocalizedException(String msg) {
		super(msg);
	}

}
