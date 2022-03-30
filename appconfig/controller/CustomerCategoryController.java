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
import com.innobiz.orca.appconfig.dto.CustomerCategoryDTO;
import com.innobiz.orca.appconfig.service.CustomerCategoryService;

@RestController
@RequestMapping(path = "/customerCategory")
public class CustomerCategoryController {
	private final CustomerCategoryService customerCategoryService;
	
	MessageConfiguration messageConfiguration= new MessageConfiguration();
	
	@Autowired
	public CustomerCategoryController(CustomerCategoryService customerCategoryService) {
		this.customerCategoryService = customerCategoryService;
	}
	@PostMapping(path = "/create")
	public ResponseEntity<CustomerCategoryDTO> create(@RequestBody @Valid CustomerCategoryDTO customerCategoryDTO){
		return new ResponseEntity<>(customerCategoryService.create(customerCategoryDTO),OK);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable (value = "id") Integer id) {
		customerCategoryService.delete(id);
		Map<String, String> deleteSuccess = new HashMap<>();
		deleteSuccess.put("id", id.toString());
		deleteSuccess.put("message", "Delete Success");
		return new ResponseEntity<>(deleteSuccess, OK);
	}
	@PutMapping(path = "/update")
	public ResponseEntity<CustomerCategoryDTO> update(@RequestBody @Valid CustomerCategoryDTO customerCategoryDTO) {
		CustomerCategoryDTO customerCategoryDto = customerCategoryService.update(customerCategoryDTO);
		return new ResponseEntity<>(customerCategoryDto, OK);
	}
	@GetMapping(path = "/findAll")
	public ResponseEntity<List<CustomerCategoryDTO>> findAll() {
		return new ResponseEntity<>(customerCategoryService.findAll(), OK);
	}
	@GetMapping(path = "/findById/{id}")
	public ResponseEntity<CustomerCategoryDTO> findById(@PathVariable(value = "id") Integer id) {
		CustomerCategoryDTO customerCategoryDTO = customerCategoryService.findById(id);
		return new ResponseEntity<>(customerCategoryDTO, OK);
	}
	@GetMapping("/findByName/{name}")
	public ResponseEntity<CustomerCategoryDTO> findByName(@PathVariable String name) {
		CustomerCategoryDTO customerCategoryDTO = customerCategoryService.findByName(name);
		return new ResponseEntity<>(customerCategoryDTO, OK);
	}
	@GetMapping("/findByCode/{code}")
	public ResponseEntity<CustomerCategoryDTO> findByCode(@PathVariable String code) {
		CustomerCategoryDTO customerCategoryDTO = customerCategoryService.findByCode(code);
		return new ResponseEntity<>(customerCategoryDTO, OK);
	}
}


