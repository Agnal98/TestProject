package com.innobiz.orca.appconfig.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.innobiz.orca.appconfig.config.MessageConfiguration;
import com.innobiz.orca.appconfig.dto.UOMCategoryDTO;
import com.innobiz.orca.appconfig.entity.UOMCategory;
import com.innobiz.orca.appconfig.exception.DataNotFoundException;
import com.innobiz.orca.appconfig.exception.DuplicateDataException;
import com.innobiz.orca.appconfig.repository.UOMCategoryRepository;
import com.innobiz.orca.appconfig.service.UOMCategoryService;

@Service
@Transactional

public class UOMCategoryServiceImpl implements UOMCategoryService {

	private final UOMCategoryRepository uomCategoryRepository;
	private final ModelMapper modelMapper;
	MessageConfiguration messageConfiguration;

	@Autowired
	public UOMCategoryServiceImpl(UOMCategoryRepository uomCategoryRepository, ModelMapper modelMapper,
			MessageConfiguration messageConfiguration) {
		this.uomCategoryRepository = uomCategoryRepository;
		this.modelMapper = modelMapper;
		this.messageConfiguration = messageConfiguration;

	}

	public UOMCategoryDTO create(UOMCategoryDTO uomCategoryDTO) {

		if (uomCategoryDTO.getId() != null || isDuplicateTask(uomCategoryDTO)) {
			throw new DuplicateDataException(messageConfiguration.getError().get("OI-100"));
		}

		return convertEntityToDto(uomCategoryRepository.save(convertDtoToEntity(uomCategoryDTO)));
	}

	public void delete(Integer id) {
		Optional<UOMCategory> uomCategoryOptional = uomCategoryRepository.findById(id);
		if (uomCategoryOptional.isPresent()) {
			//uomCategoryOptional.get().setIsDeleted(true);
		} else {
			throw new DataNotFoundException(id, messageConfiguration.getError().get("OI-101"));
		}
	}

	public List<UOMCategoryDTO> findAll() {
		return uomCategoryRepository.findAll().stream().map(this::convertEntityToDto).toList();
	}

	public UOMCategoryDTO findByName(String name) {

		Optional<UOMCategory> uomcategoryOptional = uomCategoryRepository.findByName(name);
		if (uomcategoryOptional.isPresent()) {
			return convertEntityToDto(uomcategoryOptional.get());
		}
		throw new DataNotFoundException(name, messageConfiguration.getError().get("OI-101"));
	}

	public UOMCategoryDTO findByCode(String code) {

		Optional<UOMCategory> uomcategoryOptional = uomCategoryRepository.findByCode(code);
		if (uomcategoryOptional.isPresent()) {
			return convertEntityToDto(uomcategoryOptional.get());
		}
		throw new DataNotFoundException(code, messageConfiguration.getError().get("OI-101"));
	}

	public UOMCategoryDTO findById(Integer id) {

		Optional<UOMCategory> uomcategoryOptional = uomCategoryRepository.findById(id);
		if (uomcategoryOptional.isPresent()) {
			return convertEntityToDto(uomcategoryOptional.get());
		}
		throw new DataNotFoundException(id, messageConfiguration.getError().get("OI-101"));
	}

	public UOMCategoryDTO update(UOMCategoryDTO uomCategoryDTO) {

		if (uomCategoryDTO.getId() != null) {
			if (!isDuplicateTaskForUpdate(uomCategoryDTO)) {
				Optional<UOMCategory> uomCategoryOptional = uomCategoryRepository.findById(uomCategoryDTO.getId());
				if (uomCategoryOptional.isPresent()) {
					UOMCategory uomCategory = mergeTaskDtoToEntity(uomCategoryDTO, uomCategoryOptional.get());
					uomCategoryRepository.save(uomCategory);
					return convertEntityToDto(uomCategory);
				}
			}
			throw new DuplicateDataException(messageConfiguration.getError().get("OI-100"));
		}
		throw new DataNotFoundException(uomCategoryDTO.getId(), messageConfiguration.getError().get("OI-101"));
	}

	private UOMCategory convertDtoToEntity(UOMCategoryDTO uomCategoryDTO) throws MappingException {
		return modelMapper.map(uomCategoryDTO, UOMCategory.class);
	}

	private UOMCategoryDTO convertEntityToDto(UOMCategory uomCategory) throws MappingException {
		return modelMapper.map(uomCategory, UOMCategoryDTO.class);
	}

	private boolean isDuplicateTask(UOMCategoryDTO uomCategoryDTO) {
		return uomCategoryRepository.findByNameOrCode(uomCategoryDTO.getName().trim(), uomCategoryDTO.getCode().trim())
				.isPresent();
	}

	private boolean isDuplicateTaskForUpdate(UOMCategoryDTO uomCategoryDTO) {
		Optional<UOMCategory> uomCategoryOptional = uomCategoryRepository
				.findByNameOrCode(uomCategoryDTO.getName().trim(), uomCategoryDTO.getCode().trim());
		if (uomCategoryOptional.isPresent()) {
			if (uomCategoryOptional.get().getId() == uomCategoryDTO.getId()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private UOMCategory mergeTaskDtoToEntity(UOMCategoryDTO uomCategoryDTO, UOMCategory uomCategory)
			throws EntityNotFoundException, MappingException {
		modelMapper.map(uomCategoryDTO, uomCategory);
		return uomCategory;
	}

}
