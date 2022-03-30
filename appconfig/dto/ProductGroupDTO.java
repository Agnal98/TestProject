package com.innobiz.orca.appconfig.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.innobiz.orca.appconfig.entity.ProductGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ProductGroupDTO implements Serializable {

	/**
	 * 
	 */
	
	private Integer id;
	private ProductGroup productGroupParent;
	

	@NotBlank
	@Pattern(regexp = "([A-Za-z0-9]+_?)*", message = "Only Alphanumeric Characters, underscore and space are allowed.")
	@Size(min = 3, max = 3, message = "Task code should be 3 characters length.")
	private String code;
	
	@NotBlank
	@Pattern(regexp = "([A-Za-z0-9]+\s?_?)*", message="Only Alphanumeric Characters, underscore and space are allowed.")
	@Size(min = 3, max = 100, message = "Task name should be of minimum 3 and maximum 100 characters length.")
	private String name;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductGroupDTO productGroupDTO = (ProductGroupDTO) o;
		return Objects.equals(id, productGroupDTO.id) && Objects.equals(code, productGroupDTO.code) && Objects.equals(name, productGroupDTO.name) && Objects.equals(productGroupParent, productGroupDTO.productGroupParent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, code, name,productGroupParent);
	}
}
