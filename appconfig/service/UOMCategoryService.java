package com.innobiz.orca.appconfig.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.innobiz.orca.appconfig.dto.UOMCategoryDTO;

@Service
public interface UOMCategoryService {

	public UOMCategoryDTO create(UOMCategoryDTO uomCategoryDTO);
	
	public List<UOMCategoryDTO> findAll();

	UOMCategoryDTO findByName(String name);

	UOMCategoryDTO findById(Integer id);

	UOMCategoryDTO findByCode(String code);
	
	public UOMCategoryDTO update(UOMCategoryDTO uomCategoryDTO);

	public void delete(Integer id);
	

	
	

	

	
}