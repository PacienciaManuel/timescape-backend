package com.timescape.model.converter;

import com.timescape.model.TipoPrivacidade;

import jakarta.persistence.AttributeConverter;

public class TipoPrivacidadeConverter implements AttributeConverter<TipoPrivacidade, String> {

	@Override
	public String convertToDatabaseColumn(TipoPrivacidade attribute) {
		return attribute.getDescricao();
	}

	@Override
	public TipoPrivacidade convertToEntityAttribute(String dbData) {
		return TipoPrivacidade.of(dbData);
	}
	
}
