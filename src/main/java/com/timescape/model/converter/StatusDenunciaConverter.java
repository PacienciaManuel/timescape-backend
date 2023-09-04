package com.timescape.model.converter;

import com.timescape.model.StatusDenuncia;

import jakarta.persistence.AttributeConverter;

public class StatusDenunciaConverter implements AttributeConverter<StatusDenuncia, String> {

	@Override
	public String convertToDatabaseColumn(StatusDenuncia attribute) {
		return attribute.getDescricao();
	}

	@Override
	public StatusDenuncia convertToEntityAttribute(String dbData) {
		return StatusDenuncia.of(dbData);
	}

}
