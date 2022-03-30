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
import com.innobiz.orca.appconfig.dto.ProductGroupDTO;
import com.innobiz.orca.appconfig.entity.ProductGroup;
import com.innobiz.orca.appconfig.exception.DataNotFoundException;
import com.innobiz.orca.appconfig.exception.DuplicateDataException;
import com.innobiz.orca.appconfig.repository.ProductGroupRepository;
import com.innobiz.orca.appconfig.service.ProductGroupService;

@Service
@Transactional
public class ProductGroupServiceImpl implements ProductGroupService {

	private final ProductGroupRepository productGroupRepository;
	private final ModelMapper modelmapper;
	MessageConfiguration messageConfiguration;

	@Autowired
	public ProductGroupServiceImpl(ProductGroupRepository productGroupRepository, ModelMapper modelMapper,
			MessageConfiguration messageConfiguration) {
		this.productGroupRepository = productGroupRepository;
		this.modelmapper = modelMapper;
		this.messageConfiguration = messageConfiguration;
	}

	public ProductGroupDTO create(ProductGroupDTO productGroupDTO) {

		if (productGroupDTO.getId() != null || isDuplicateProducGroup(productGroupDTO)) {
			throw new DuplicateDataException(messageConfiguration.getError().get("OI-100"));
		}

		return convertEntityToDto(productGroupRepository.save(convertDtoToEntity(productGroupDTO)));
	}

	public void delete(Integer id) {
		Optional<ProductGroup> productGroupOptional = productGroupRepository.findById(id);
		if (productGroupOptional.isPresent()) {
			productGroupOptional.get().setIsDeleted(true);
		} else {
			throw new DataNotFoundException(id, messageConfiguration.getError().get("OI-101"));
		}
	}

	public ProductGroupDTO update(ProductGroupDTO productGroupDTO) {

		if (productGroupDTO.getId() != null) {
			if (!isDuplicateProductGroupForUpdate(productGroupDTO)) {
				Optional<ProductGroup> productGroupOptional = productGroupRepository.findById(productGroupDTO.getId());
				if (productGroupOptional.isPresent()) {
					ProductGroup productGroup = mergeProductGroupkDtoToEntity(productGroupDTO,
							productGroupOptional.get());
					productGroupRepository.save(productGroup);
					return convertEntityToDto(productGroup);
				}
			}
			throw new DuplicateDataException(messageConfiguration.getError().get("OI-100"));
		}
		throw new DataNotFoundException(productGroupDTO.getId(), messageConfiguration.getError().get("OI-101"));
	}

	public List<ProductGroupDTO> findAll() {
		return productGroupRepository.findAll().stream().map(this::convertEntityToDto).toList();
	}

	public ProductGroupDTO findByName(String name) {

		Optional<ProductGroup> productGroupOptional = productGroupRepository.findByName(name);
		if (productGroupOptional.isPresent()) {
			return convertEntityToDto(productGroupOptional.get());
		}
		throw new DataNotFoundException(name, messageConfiguration.getError().get("OI-101"));
	}

	public ProductGroupDTO findByCode(String code) {

		Optional<ProductGroup> productGroupOptional = productGroupRepository.findByCode(code);
		if (productGroupOptional.isPresent()) {
			return convertEntityToDto(productGroupOptional.get());
		}
		throw new DataNotFoundException(code, messageConfiguration.getError().get("OI-101"));
	}

	public ProductGroupDTO findById(Integer id) {

		Optional<ProductGroup> productGroupOptional = productGroupRepository.findById(id);
		if (productGroupOptional.isPresent()) {
			return convertEntityToDto(productGroupOptional.get());
		}
		throw new DataNotFoundException(id, messageConfiguration.getError().get("OI-101"));
	}

	private ProductGroup convertDtoToEntity(ProductGroupDTO productGroupDTO) throws MappingException {
		ProductGroup productGroupParent = modelmapper.map(productGroupDTO, ProductGroup.class);
		productGroupParent.setProductGroupParent(productGroupRepository.findById(productGroupDTO.getProductGroupParent().getId()).get());
		
		return productGroupParent;
	}

	private ProductGroupDTO convertEntityToDto(ProductGroup productGroup) throws MappingException {
		return modelmapper.map(productGroup, ProductGroupDTO.class);
	}

	private ProductGroup mergeProductGroupkDtoToEntity(ProductGroupDTO productGroupDTO, ProductGroup productGroup)
			throws EntityNotFoundException, MappingException {
		modelmapper.map(productGroupDTO, productGroup);
		return productGroup;
	}

	private boolean isDuplicateProducGroup(ProductGroupDTO productGroupDTO) {
		return this.productGroupRepository
				.findByNameOrCode(productGroupDTO.getName().trim(), productGroupDTO.getCode().trim()).isPresent();
	}

	private boolean isDuplicateProductGroupForUpdate(ProductGroupDTO productGroupDTO) {
		Optional<ProductGroup> productGroupOptional = productGroupRepository
				.findByNameOrCode(productGroupDTO.getName().trim(), productGroupDTO.getCode().trim());
		if (productGroupOptional.isPresent()) {
			if (productGroupOptional.get().getId() == productGroupDTO.getId()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

}
