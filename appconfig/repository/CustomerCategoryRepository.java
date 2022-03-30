package com.innobiz.orca.appconfig.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innobiz.orca.appconfig.entity.CustomerCategory;
import com.innobiz.orca.appconfig.entity.ProductGroup;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory, Integer>{
	
	List<CustomerCategory> findAll();
	
	Optional<CustomerCategory> findByName(String name);
	
	Optional<CustomerCategory> findByCode(String code);
	
	Optional<CustomerCategory> findById(Integer id);
	
	Optional<CustomerCategory> findByNameOrCode(String name, String code);
	


}
