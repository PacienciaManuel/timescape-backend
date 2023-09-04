package com.timescape.model.converter;

import com.timescape.model.TipoPresenca;

import jakarta.persistence.AttributeConverter;

public class TipoPresencaConverter implements AttributeConverter<TipoPresenca, String> {

	@Override
	public String convertToDatabaseColumn(TipoPresenca attribute) {
		return attribute.getDescricao();
	}

	@Override
	public TipoPresenca convertToEntityAttribute(String dbData) {
		return TipoPresenca.of(dbData);
	}

}
