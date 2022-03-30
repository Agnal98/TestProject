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
import com.innobiz.orca.appconfig.dto.UOMCategoryDTO;
import com.innobiz.orca.appconfig.dto.UOMDTO;
import com.innobiz.orca.appconfig.entity.UOM;
import com.innobiz.orca.appconfig.entity.UOMCategory;
import com.innobiz.orca.appconfig.exception.DataNotFoundException;
import com.innobiz.orca.appconfig.exception.DuplicateDataException;
import com.innobiz.orca.appconfig.repository.UOMCategoryRepository;
import com.innobiz.orca.appconfig.repository.UOMRepository;
import com.innobiz.orca.appconfig.service.UOMService;

@Service
@Transactional
public class UOMServiceImpl implements UOMService {

	private final UOMRepository uomRepository;
	private final UOMCategoryRepository uomCategoryRepository;
	private final ModelMapper modelMapper;
	MessageConfiguration messageConfiguration;

	@Autowired
	public UOMServiceImpl(UOMRepository uomRepository, ModelMapper modelMapper,
			MessageConfiguration messageConfiguration, UOMCategoryRepository uomCategoryRepository) {
		this.uomRepository = uomRepository;
		this.uomCategoryRepository = uomCategoryRepository;
		this.modelMapper = modelMapper;
		this.messageConfiguration = messageConfiguration;

	}

	public UOMDTO create(UOMDTO uomDTO) {

		if (uomDTO.getId() != null || isDuplicateTask(uomDTO)) {
			throw new DuplicateDataException(messageConfiguration.getError().get("OI-100"));
		}

		return convertEntityToDto(uomRepository.save(convertDtoToEntity(uomDTO)));
	}

	public void delete(Integer id) {
		Optional<UOM> uomOptional = uomRepository.findById(id);
		if (uomOptional.isPresent()) {
			uomOptional.get().setIsDeleted(true);
		} else {
			throw new DataNotFoundException(id, messageConfiguration.getError().get("OI-101"));
		}
	}

	public List<UOMDTO> findAll() {
		return uomRepository.findAll().stream().map(this::convertEntityToDto).toList();
	}

	public UOMDTO findByUomName(String uomName) {

		Optional<UOM> uomOptional = uomRepository.findByUomName(uomName);
		if (uomOptional.isPresent()) {
			return convertEntityToDto(uomOptional.get());
		}
		throw new DataNotFoundException(uomName, messageConfiguration.getError().get("OI-101"));
	}

	public UOMDTO findByUomCode(String uomCode) {

		Optional<UOM> uomOptional = uomRepository.findByUomCode(uomCode);
		if (uomOptional.isPresent()) {
			return convertEntityToDto(uomOptional.get());
		}
		throw new DataNotFoundException(uomCode, messageConfiguration.getError().get("OI-101"));
	}

	public UOMDTO findById(Integer id) {

		Optional<UOM> uomOptional = uomRepository.findById(id);
		if (uomOptional.isPresent()) {
			return convertEntityToDto(uomOptional.get());
		}
		throw new DataNotFoundException(id, messageConfiguration.getError().get("OI-101"));
	}

	public UOMCategoryDTO  findByUomCategoryId(Integer id) {

		Optional<UOMCategory> uomCategoryOptional = uomCategoryRepository.findById(id);
		if (uomCategoryOptional.isPresent()) {
			return convertEntityToDto(uomCategoryOptional.get());
		}
		throw new DataNotFoundException(id, messageConfiguration.getError().get("OI-101"));
	}

	public UOMDTO update(UOMDTO uomDTO) {

		if (uomDTO.getId() != null) {
			if (!isDuplicateUOMForUpdate(uomDTO)) {
				Optional<UOM> uomOptional = uomRepository.findById(uomDTO.getId());
				if (uomOptional.isPresent()) {
					UOM uom = mergeTaskDtoToEntity(uomDTO, uomOptional.get());
					uomRepository.save(uom);
					return convertEntityToDto(uom);
				}
			}
			throw new DuplicateDataException(messageConfiguration.getError().get("OI-100"));
		}
		throw new DataNotFoundException(uomDTO.getId(), messageConfiguration.getError().get("OI-101"));
	}

	private UOM convertDtoToEntity(UOMDTO uomDto) throws MappingException {
		return modelMapper.map(uomDto, UOM.class);
	}

	private UOMDTO convertEntityToDto(UOM uom) throws MappingException {
		return modelMapper.map(uom, UOMDTO.class);
	}

	private UOMCategoryDTO convertEntityToDto(UOMCategory uomCategory) throws MappingException {
		return modelMapper.map(uomCategory, UOMCategoryDTO.class);
	}

	private UOM mergeTaskDtoToEntity(UOMDTO uomDto, UOM uom) throws EntityNotFoundException, MappingException {
		modelMapper.map(uomDto, uom);
		return uom;
	}

	private boolean isDuplicateTask(UOMDTO uomDTO) {
		return uomRepository.findByUomNameOrUomCode(uomDTO.getUomName().trim(), uomDTO.getUomCode().trim()).isPresent();
	}

	private boolean isDuplicateUOMForUpdate(UOMDTO uomDTO) {
		Optional<UOM> uomOptional = uomRepository.findByUomNameOrUomCode(uomDTO.getUomName().trim(),
				uomDTO.getUomCode().trim());
		if (uomOptional.isPresent()) {
			if (uomOptional.get().getId() == uomDTO.getId()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}
