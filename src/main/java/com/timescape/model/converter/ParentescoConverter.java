package com.timescape.model.converter;

import com.timescape.model.Parentesco;

import jakarta.persistence.AttributeConverter;

public class ParentescoConverter implements AttributeConverter<Parentesco, String> {

	@Override
	public String convertToDatabaseColumn(Parentesco attribute) {
		return attribute.getDescricao();
	}

	@Override
	public Parentesco convertToEntityAttribute(String dbData) {
		return Parentesco.of(dbData);
	}

}
