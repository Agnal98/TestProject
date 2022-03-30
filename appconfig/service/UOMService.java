package com.innobiz.orca.appconfig.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.innobiz.orca.appconfig.dto.UOMCategoryDTO;
import com.innobiz.orca.appconfig.dto.UOMDTO;
@Service
public interface UOMService  {

	public UOMDTO create(UOMDTO uomDTO);

	public List<UOMDTO> findAll();

	public UOMDTO update(UOMDTO uomDto);

	public void delete(Integer id);

	 UOMDTO findById(Integer id);

	 UOMDTO findByUomName(String uomName);

	 UOMDTO findByUomCode(String uomCode);
	 
	 UOMCategoryDTO findByUomCategoryId(Integer id);
	 


	


}
