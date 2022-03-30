package com.innobiz.orca.appconfig.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.innobiz.orca.appconfig.entity.UOMCategory;
import com.innobiz.orca.appconfig.entity.UOMType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class UOMDTO  implements Serializable{

	/**
	 * 
	 */
	private Integer id;
	private Integer roundPrescition;
	@Enumerated
	private UOMType uomType;
	private UOMCategory uomCategory;
	

	@NotBlank
	@Pattern(regexp = "([A-Za-z0-9]+_?)*", message = "Only Alphanumeric Characters, underscore and space are allowed.")
	@Size(min = 3, max = 3, message = "Task code should be 3 characters length.")
	private String uomCode;

	@NotBlank
	@Pattern(regexp = "([A-Za-z0-9]+\s?_?)*", message="Only Alphanumeric Characters, underscore and space are allowed.")
	@Size(min = 3, max = 100, message = "Task name should be of minimum 3 and maximum 100 characters length.")
	private String uomName;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UOMDTO uomDTO = (UOMDTO) o;
		return Objects.equals(id, uomDTO.id) && Objects.equals(uomCode, uomDTO.uomCode) && Objects.equals(uomName, uomDTO.uomName)&& Objects.equals(roundPrescition, uomDTO.roundPrescition)&& Objects.equals(uomCategory, uomDTO.uomCategory)&& Objects.equals(uomType, uomDTO.uomType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, uomCode, uomName,roundPrescition,uomCategory,uomType);
	}
	

	
}
	