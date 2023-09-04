package com.timescape.model.converter;

import com.timescape.model.Objectivo;

import jakarta.persistence.AttributeConverter;

public class ObjectivoConverter implements AttributeConverter<Objectivo, String> {

	@Override
	public String convertToDatabaseColumn(Objectivo attribute) {
		return attribute.getDescricao();
	}

	@Override
	public Objectivo convertToEntityAttribute(String dbData) {
		return Objectivo.of(dbData);
	}

}
