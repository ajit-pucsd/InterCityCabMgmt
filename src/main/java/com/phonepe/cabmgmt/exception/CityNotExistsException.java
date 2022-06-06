package com.phonepe.cabmgmt.exception;

public class CityNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CityNotExistsException(String msg) {
		super(msg);
	}
}
