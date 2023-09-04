package com.timescape.model.converter;

import com.timescape.model.TipoAtividade;

import jakarta.persistence.AttributeConverter;

public class TipoAtividadeConverter implements AttributeConverter<TipoAtividade, String> {

	@Override
	public String convertToDatabaseColumn(TipoAtividade attribute) {
		return attribute.getDescricao();
	}

	@Override
	public TipoAtividade convertToEntityAttribute(String dbData) {
		return TipoAtividade.of(dbData);
	}

}
