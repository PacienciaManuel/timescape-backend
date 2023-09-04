package com.timescape.model.converter;

import com.timescape.model.NivelIdioma;

import jakarta.persistence.AttributeConverter;

public class NivelIdiomaConverter implements AttributeConverter<NivelIdioma, String> {

	@Override
	public String convertToDatabaseColumn(NivelIdioma attribute) {
		return attribute.getDescricao();
	}

	@Override
	public NivelIdioma convertToEntityAttribute(String dbData) {
		return NivelIdioma.of(dbData);
	}

}
