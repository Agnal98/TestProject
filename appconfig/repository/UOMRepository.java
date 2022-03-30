package com.innobiz.orca.appconfig.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innobiz.orca.appconfig.entity.UOM;

public interface UOMRepository extends JpaRepository <UOM, Integer> {

	Optional<UOM> findByUomName(String uomName);
	
	Optional<UOM> findById (Integer id);
	
	Optional<UOM> findByUomCode (String uomCode);
	
	List<UOM> findAll();

	Optional<UOM> findByUomNameOrUomCode(String uomName, String uomCode);
	
	
	
	

}
