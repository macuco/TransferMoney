package com.trasnfermoney.exception;

/**
 * This class is used to report exception in the business logic
 *
 */
public class BussinesLogicException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4070663335340054398L;

	public BussinesLogicException() {
		super();
	}
	
	public BussinesLogicException(String error) {
		super(error);
	}

}
