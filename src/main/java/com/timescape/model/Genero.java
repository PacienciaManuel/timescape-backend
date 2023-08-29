package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.timescape.exception.GeneroNotFoundException;

import lombok.Getter;
import lombok.ToString;

@ToString
public enum Genero {
	MASCULINO("Masculino"), FEMININO("Feminino");
	
	@Getter
	@JsonValue
	private final String descricao;

	private Genero(String descricao) {
		this.descricao = descricao;
	}
	
	public static Genero of(String descricao) throws GeneroNotFoundException {
		for (Genero genero : Genero.values()) {
			if (genero.descricao.equalsIgnoreCase(descricao)) {
				return genero;
			}
		}
		throw new GeneroNotFoundException(String.format("Gênero inválido: %s", descricao));
	}
}
