package com.timescape.model.converter;

import com.timescape.model.TipoReacao;

import jakarta.persistence.AttributeConverter;

public class TipoReacaoConverter implements AttributeConverter<TipoReacao, String> {

	@Override
	public String convertToDatabaseColumn(TipoReacao attribute) {
		return attribute.getDescricao();
	}

	@Override
	public TipoReacao convertToEntityAttribute(String dbData) {
		return TipoReacao.of(dbData);
	}

}
