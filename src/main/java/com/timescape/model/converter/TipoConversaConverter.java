package com.timescape.model.converter;

import com.timescape.model.TipoConversa;

import jakarta.persistence.AttributeConverter;

public class TipoConversaConverter implements AttributeConverter<TipoConversa, String> {

	@Override
	public String convertToDatabaseColumn(TipoConversa attribute) {
		return attribute.getDescricao();
	}

	@Override
	public TipoConversa convertToEntityAttribute(String dbData) {
		return TipoConversa.of(dbData);
	}

}
