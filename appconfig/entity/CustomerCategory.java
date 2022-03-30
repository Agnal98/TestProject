package com.innobiz.orca.appconfig.entity;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted = 'false'")
public class CustomerCategory extends Auditable {
	
private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	
	private String name;
	private String code;
	@ManyToOne
	@JoinColumn(name="customerCategory",referencedColumnName = "id")
	private CustomerCategory customerCategoryParent;
//	@ManyToOne
//	@JoinColumn(name="customerCategory",referencedColumnName = "name")
//	private CustomerCategory customerCategoryParentName;
//	@ManyToOne
//	@JoinColumn(name="customerCategory",referencedColumnName = "name")
//	private CustomerCategory customerCategoryParentCode;


	
}
