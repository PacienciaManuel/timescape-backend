package com.timescape.model.converter;

import com.timescape.model.DiaSemana;

import jakarta.persistence.AttributeConverter;

public class DiaSemanaConverter implements AttributeConverter<DiaSemana, String> {

	@Override
	public String convertToDatabaseColumn(DiaSemana attribute) {
		return attribute.getDescricao();
	}

	@Override
	public DiaSemana convertToEntityAttribute(String dbData) {
		return DiaSemana.of(dbData);
	}

}
