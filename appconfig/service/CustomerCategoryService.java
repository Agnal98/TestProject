package com.innobiz.orca.appconfig.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.innobiz.orca.appconfig.dto.CustomerCategoryDTO;
@Service
public interface CustomerCategoryService {
	
	public CustomerCategoryDTO create(CustomerCategoryDTO customerCategoryDTO); 
	
	public CustomerCategoryDTO update(CustomerCategoryDTO customerCategoryDTO);
	
	public void  delete(Integer id);
	
	public List<CustomerCategoryDTO> findAll();
	
	CustomerCategoryDTO findById(Integer id);
	
	CustomerCategoryDTO findByName(String name);
	
	CustomerCategoryDTO findByCode(String code);
	

}
