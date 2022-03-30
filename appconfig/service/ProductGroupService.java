package com.innobiz.orca.appconfig.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.innobiz.orca.appconfig.dto.ProductGroupDTO;

@Service
public interface ProductGroupService {
	
	public ProductGroupDTO create(ProductGroupDTO productGroupDTO); 
	
	public ProductGroupDTO update(ProductGroupDTO productGroupDTO);
	
	public void  delete(Integer id);
	
	public List<ProductGroupDTO> findAll();
	
	ProductGroupDTO findById(Integer id);
	
	ProductGroupDTO findByName(String name);
	
	ProductGroupDTO findByCode(String code);
	

	
		
	}
		


