package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum Privacidade {
	APENAS_EU("Apenas Eu"), 
	AMIGOS("Amigos"),
	GRUPO("Grupo"),
	PUBLICO("Público"),
	PRIVADO("Privado");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static Privacidade of(String descricao) {
		for (Privacidade tipoPrivacidade : Privacidade.values()) {
			if (tipoPrivacidade.descricao.equalsIgnoreCase(descricao)) {
				return tipoPrivacidade;
			}
		}
		throw new IllegalArgumentException("Tipo de privacidade inválida: " + descricao);
	}
}
