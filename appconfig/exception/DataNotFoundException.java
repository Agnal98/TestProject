package com.innobiz.orca.appconfig.exception;

import lombok.Getter;
@Getter

public class DataNotFoundException extends  RuntimeException{
    private Integer uomCategoryId;


    private String name;
    public DataNotFoundException(Integer uomCategoryId, String message){
        super(message);
        this.uomCategoryId = uomCategoryId;
    }
    	

    public DataNotFoundException(String name, String message){
        super(message);
        this.name = name;
    }
}

	
    

    