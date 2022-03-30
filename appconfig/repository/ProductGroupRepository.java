package com.innobiz.orca.appconfig.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innobiz.orca.appconfig.entity.ProductGroup;

public interface ProductGroupRepository extends JpaRepository <ProductGroup, Integer> {
	
	List<ProductGroup> findAll();
	
	Optional<ProductGroup> findByName(String name);
	
	Optional<ProductGroup> findByCode(String code);
	
	Optional<ProductGroup> findById(Integer id);
	
	Optional<ProductGroup> findByNameOrCode(String name, String code);
	

	

}
