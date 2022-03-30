package com.innobiz.orca.appconfig.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorMessageDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String message;
	private String detail;

}
