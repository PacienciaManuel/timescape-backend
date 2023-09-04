package com.timescape.model.converter;

import com.timescape.model.TipoDenuncia;

import jakarta.persistence.AttributeConverter;

public class TipoDenunciaConverter implements AttributeConverter<TipoDenuncia, String> {

	@Override
	public String convertToDatabaseColumn(TipoDenuncia attribute) {
		return attribute.getDescricao();
	}

	@Override
	public TipoDenuncia convertToEntityAttribute(String dbData) {
		return TipoDenuncia.of(dbData);
	}

}
