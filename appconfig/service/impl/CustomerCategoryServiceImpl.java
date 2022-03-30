package com.innobiz.orca.appconfig.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innobiz.orca.appconfig.config.MessageConfiguration;
import com.innobiz.orca.appconfig.dto.CustomerCategoryDTO;
import com.innobiz.orca.appconfig.dto.ProductGroupDTO;
import com.innobiz.orca.appconfig.entity.CustomerCategory;
import com.innobiz.orca.appconfig.exception.DataNotFoundException;
import com.innobiz.orca.appconfig.exception.DuplicateDataException;
import com.innobiz.orca.appconfig.repository.CustomerCategoryRepository;
import com.innobiz.orca.appconfig.service.CustomerCategoryService;

@Service
@Transactional
public class CustomerCategoryServiceImpl implements CustomerCategoryService {

	private final CustomerCategoryRepository customerCategoryRepository;
	private final ModelMapper modelmapper;
	MessageConfiguration messageConfiguration;

	@Autowired
	public CustomerCategoryServiceImpl(CustomerCategoryRepository customerCategoryRepository, ModelMapper modelMapper,
			MessageConfiguration messageConfiguration) {
		this.customerCategoryRepository = customerCategoryRepository;
		this.modelmapper = modelMapper;
		this.messageConfiguration = messageConfiguration;
	}

	public CustomerCategoryDTO create(CustomerCategoryDTO customerCategoryDTO) {

		if (customerCategoryDTO.getId() != null || isDuplicateCustomerCategory(customerCategoryDTO)) {
			throw new DuplicateDataException(messageConfiguration.getError().get("OI-100"));
		}

		return convertEntityToDto(customerCategoryRepository.save(convertDtoToEntity(customerCategoryDTO)));
	}

	public void delete(Integer id) {
		Optional<CustomerCategory> customerCategoryOptional = customerCategoryRepository.findById(id);
		if (customerCategoryOptional.isPresent()) {
			customerCategoryOptional.get().setIsDeleted(true);
		} else {
			throw new DataNotFoundException(id, messageConfiguration.getError().get("OI-101"));
		}
	}

	public CustomerCategoryDTO update(CustomerCategoryDTO customerCategoryDTO) {

		if (customerCategoryDTO.getId() != null) {
			if (!isDuplicateCustomerCategoryForUpdate(customerCategoryDTO)) {
				Optional<CustomerCategory> customerCategoryOptional = customerCategoryRepository.findById(customerCategoryDTO.getId());
				if (customerCategoryOptional.isPresent()) {
					CustomerCategory customerCategory = mergeCustomerCategoryDtoToEntity(customerCategoryDTO,
							customerCategoryOptional.get());
					customerCategoryRepository.save(customerCategory);
					return convertEntityToDto(customerCategory);
				}
			}
			throw new DuplicateDataException(messageConfiguration.getError().get("OI-100"));
		}
		throw new DataNotFoundException(customerCategoryDTO.getId(), messageConfiguration.getError().get("OI-101"));
	}

	public List<CustomerCategoryDTO> findAll() {
		return customerCategoryRepository.findAll().stream().map(this::convertEntityToDto).toList();
	}

	public CustomerCategoryDTO findByName(String name) {

		Optional<CustomerCategory> customerCategoryOptional = customerCategoryRepository.findByName(name);
		if (customerCategoryOptional.isPresent()) {
			return convertEntityToDto(customerCategoryOptional.get());
		}
		throw new DataNotFoundException(name, messageConfiguration.getError().get("OI-101"));
	}
	public CustomerCategoryDTO findByCode(String code) {

		Optional<CustomerCategory> customerCategoryOptional = customerCategoryRepository.findByCode(code);
		if (customerCategoryOptional.isPresent()) {
			return convertEntityToDto(customerCategoryOptional.get());
		}
		throw new DataNotFoundException(code, messageConfiguration.getError().get("OI-101"));
	}

	public CustomerCategoryDTO findById(Integer id) {

		Optional<CustomerCategory> customerCategoryOptional = customerCategoryRepository.findById(id);
		if (customerCategoryOptional.isPresent()) {
			return convertEntityToDto(customerCategoryOptional.get());
		}
		throw new DataNotFoundException(id, messageConfiguration.getError().get("OI-101"));
	}
	private CustomerCategory convertDtoToEntity(CustomerCategoryDTO customerCategoryDTO) throws MappingException {
		CustomerCategory customerCategoryParent = modelmapper.map(customerCategoryDTO, CustomerCategory.class);
		customerCategoryParent.setCustomerCategoryParent(customerCategoryRepository.findById(customerCategoryDTO.getCustomerCategoryParent().getId()).get());
		return customerCategoryParent;
	}
//	private CustomerCategory convertDtoToEntity(CustomerCategoryDTO customerCategoryDTO) throws MappingException {
//	
//		CustomerCategory customerCategoryParentName = modelmapper.map(customerCategoryDTO, CustomerCategory.class);
//		customerCategoryParentName.setCustomerCategoryParentName(customerCategoryRepository.findByName(customerCategoryDTO.getCustomerCategoryParentName().getName()).get());
//		return customerCategoryParentName;
//	}
//	private CustomerCategory convertDtoToEntity(CustomerCategoryDTO customerCategoryDTO) throws MappingException {
//		CustomerCategory customerCategoryParentCode = modelmapper.map(customerCategoryDTO, CustomerCategory.class);
//		customerCategoryParentCode.setCustomerCategoryParentCode(customerCategoryRepository.findByCode(customerCategoryDTO.getCustomerCategoryParentCode().getCode()).get());
//		return customerCategoryParentCode;
//	}
	private CustomerCategoryDTO convertEntityToDto(CustomerCategory customerCategory) throws MappingException {
		return modelmapper.map(customerCategory, CustomerCategoryDTO.class);
	}

	private CustomerCategory mergeCustomerCategoryDtoToEntity(CustomerCategoryDTO customerCategoryDTO, CustomerCategory customerCategory)
			throws EntityNotFoundException, MappingException {
		modelmapper.map(customerCategoryDTO, customerCategory);
		return customerCategory;
	}

	private boolean isDuplicateCustomerCategory(CustomerCategoryDTO customerCategoryDTO) {
		return this.customerCategoryRepository
				.findByNameOrCode(customerCategoryDTO.getName().trim(), customerCategoryDTO.getCode().trim()).isPresent();
	}

	private boolean isDuplicateCustomerCategoryForUpdate(CustomerCategoryDTO customerCategoryDTO) {
		Optional<CustomerCategory> customerCategoryOptional = customerCategoryRepository
				.findByNameOrCode(customerCategoryDTO.getName().trim(), customerCategoryDTO.getCode().trim());
		if (customerCategoryOptional.isPresent()) {
			if (customerCategoryOptional.get().getId() == customerCategoryDTO.getId()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

}
