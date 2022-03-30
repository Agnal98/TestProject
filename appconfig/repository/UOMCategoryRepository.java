package com.innobiz.orca.appconfig.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innobiz.orca.appconfig.entity.UOMCategory;

public interface UOMCategoryRepository extends JpaRepository <UOMCategory, Integer>  {

	Optional<UOMCategory> findByName(String name);

	
	Optional<UOMCategory> findById(Integer id);

	List<UOMCategory> findAll();
	
	Optional<UOMCategory> findByNameOrCode(String name, String code);

	Optional<UOMCategory> findByCode(String code);

	

	
}
	
	

	

	
	
	

	
	 


