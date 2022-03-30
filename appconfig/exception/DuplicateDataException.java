package com.innobiz.orca.appconfig.exception;

import lombok.Getter;

@Getter
public class DuplicateDataException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateDataException(String message){
        super(message);
    }

}
