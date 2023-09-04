package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum NivelIdioma {
	INICIANTE("Inciante"), 
	INTERMEDIARO("Intermediário"), 
	AVANCADO("Avançado"), 
	FLUENTE("Fluente");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static NivelIdioma of(String descricao) {
		for (NivelIdioma nivelIdioma : NivelIdioma.values()) {
			if (nivelIdioma.descricao.equalsIgnoreCase(descricao)) {
				return nivelIdioma;
			}
		}
		throw new IllegalArgumentException("Nível de idioma inválido: " + descricao);
	}	
}
