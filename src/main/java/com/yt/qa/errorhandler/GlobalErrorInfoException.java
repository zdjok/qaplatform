package com.yt.qa.errorhandler;

public class GlobalErrorInfoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ErrorInfoInterface errorInfoInterface;

	public GlobalErrorInfoException(ErrorInfoInterface errorInfoInterface) {
		this.errorInfoInterface = errorInfoInterface;
	}

	public ErrorInfoInterface getErrorInfoInterface() {
		return errorInfoInterface;
	}

	public void setErrorInfoInterface(ErrorInfoInterface errorInfoInterface) {
		this.errorInfoInterface = errorInfoInterface;
	}

}
