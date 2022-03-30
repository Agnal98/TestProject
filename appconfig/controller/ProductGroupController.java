package com.innobiz.orca.appconfig.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobiz.orca.appconfig.config.MessageConfiguration;
import com.innobiz.orca.appconfig.dto.ProductGroupDTO;
import com.innobiz.orca.appconfig.service.ProductGroupService;

@RestController
@RequestMapping(path = "/productGroup")
public class ProductGroupController {
	private final ProductGroupService productGroupService;
	
	MessageConfiguration messageConfiguration= new MessageConfiguration();
	
	@Autowired
	public ProductGroupController(ProductGroupService productGroupService) {
		this.productGroupService = productGroupService;
		
	}
	@PostMapping(path = "/create")
	public ResponseEntity<ProductGroupDTO> create(@RequestBody @Valid ProductGroupDTO productGroupDTO){
		return new ResponseEntity<>(productGroupService.create(productGroupDTO),OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable (value = "id") Integer id) {
		productGroupService.delete(id);
		Map<String, String> deleteSuccess = new HashMap<>();
		deleteSuccess.put("id", id.toString());
		deleteSuccess.put("message", "Delete Success");
		return new ResponseEntity<>(deleteSuccess, OK);
	}
	@PutMapping(path = "/update")
	public ResponseEntity<ProductGroupDTO> update(@RequestBody @Valid ProductGroupDTO productGroupDTO) {
		ProductGroupDTO productGroupDto = productGroupService.update(productGroupDTO);
		return new ResponseEntity<>(productGroupDto, OK);
	}
	@GetMapping(path = "/findAll")
	public ResponseEntity<List<ProductGroupDTO>> findAll() {
		return new ResponseEntity<>(productGroupService.findAll(), OK);
	}
	@GetMapping(path = "/findById/{id}")
	public ResponseEntity<ProductGroupDTO> findById(@PathVariable(value = "id") Integer id) {
		ProductGroupDTO productGroupDTO = productGroupService.findById(id);
		return new ResponseEntity<>(productGroupDTO, OK);
	}
	@GetMapping("/findByName/{name}")
	public ResponseEntity<ProductGroupDTO> findByName(@PathVariable String name) {
		ProductGroupDTO productGroupDTO = productGroupService.findByName(name);
		return new ResponseEntity<>(productGroupDTO, OK);
	}
	@GetMapping("/findByCode/{code}")
	public ResponseEntity<ProductGroupDTO> findByCode(@PathVariable String code) {
		ProductGroupDTO productGroupDTO = productGroupService.findByCode(code);
		return new ResponseEntity<>(productGroupDTO, OK);
	}
	
}
