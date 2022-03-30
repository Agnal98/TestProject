package com.innobiz.orca.appconfig.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobiz.orca.appconfig.config.MessageConfiguration;
import com.innobiz.orca.appconfig.dto.UOMCategoryDTO;
import com.innobiz.orca.appconfig.service.UOMCategoryService;
//@CrossOrigin("*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/uomCategory") // This means URL's start with /demo (after Application path)

public class UOMCategoryController {
	
	private final UOMCategoryService uomCategoryService;
	
	MessageConfiguration messageConfiguration= new MessageConfiguration();
	
	
	@Autowired
	public UOMCategoryController(UOMCategoryService uomCategoryService) {
		this.uomCategoryService=uomCategoryService;
	}

	@PostMapping(path = "/create") // Map ONLY POST Requests
	public ResponseEntity<UOMCategoryDTO> create(@RequestBody @Valid UOMCategoryDTO uomCategoryDTO) {
		return new ResponseEntity<>(uomCategoryService.create(uomCategoryDTO),OK);
	}

	@PutMapping("/update") // update name
	public ResponseEntity<UOMCategoryDTO> update(@RequestBody @Valid UOMCategoryDTO uomCategoryDTO){
		UOMCategoryDTO uomCategoryDto=uomCategoryService.update(uomCategoryDTO);
		return new ResponseEntity<>(uomCategoryDto,OK);

	}

	@GetMapping(path = "/find-all")
	public ResponseEntity<List<UOMCategoryDTO>> findAll() {

		return new ResponseEntity<> (uomCategoryService.findAll(),OK);

	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<UOMCategoryDTO>findById(@PathVariable (value = "id") Integer id) {
		UOMCategoryDTO uomCategoryDTO=uomCategoryService.findById(id);
		return new ResponseEntity<>(uomCategoryDTO,OK);
	}

	@GetMapping(path = "/findByName/{name}")
	public ResponseEntity<UOMCategoryDTO>findByName(@PathVariable String name) {
		UOMCategoryDTO uomCategoryDTOList = uomCategoryService.findByName(name);
		return new ResponseEntity<>(uomCategoryDTOList,OK);
	}

	@GetMapping(path = "/findByCode/{code}")
	public ResponseEntity<UOMCategoryDTO> findByCode(@PathVariable String code) {
		UOMCategoryDTO uomCategoryDTOList=uomCategoryService.findByCode(code);
		return new ResponseEntity<>(uomCategoryDTOList,OK);

	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String,String>> delete(@PathVariable (value="id") Integer id) {
		uomCategoryService.delete(id);
		Map<String,String> deleteSuccess = new HashMap<>();
		deleteSuccess.put("id",id.toString());
		deleteSuccess.put("message","Delete Success");
		return new ResponseEntity<>(deleteSuccess,OK);
		//return new ResponseEntity<>(this.messageConfiguration.infoMessage(MessageCode.UPS_205),OK);
	}
	


}
