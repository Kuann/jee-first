package me.kuann.jee.first.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class LocalizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public LocalizedException(String msg) {
		super(msg);
	}

}
