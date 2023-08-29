package com.timescape.model.converter;

import com.timescape.model.Genero;

import jakarta.persistence.AttributeConverter;

public class GeneroConverter implements AttributeConverter<Genero, String> {

	@Override
	public String convertToDatabaseColumn(Genero genero) {
		return genero.getDescricao();
	}

	@Override
	public Genero convertToEntityAttribute(String descricao) {
		return Genero.of(descricao);
	}	
}
