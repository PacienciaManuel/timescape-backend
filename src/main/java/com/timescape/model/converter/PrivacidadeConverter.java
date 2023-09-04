package com.timescape.model.converter;

import com.timescape.model.Privacidade;

import jakarta.persistence.AttributeConverter;

public class PrivacidadeConverter implements AttributeConverter<Privacidade, String> {

	@Override
	public String convertToDatabaseColumn(Privacidade attribute) {
		return attribute.getDescricao();
	}

	@Override
	public Privacidade convertToEntityAttribute(String dbData) {
		return Privacidade.of(dbData);
	}
	
}
