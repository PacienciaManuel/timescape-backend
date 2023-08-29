package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TipoPrivacidade {
	APENAS_EU("Apenas Eu"), AMIGOS("Amigos"), PUBLICO("Público");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static TipoPrivacidade of(String descricao) {
		for (TipoPrivacidade tipoPrivacidade : TipoPrivacidade.values()) {
			if (tipoPrivacidade.descricao.equalsIgnoreCase(descricao)) {
				return tipoPrivacidade;
			}
		}
		throw new IllegalArgumentException("Tipo de privacidade inválida: " + descricao);
	}
	
	@Override
	public String toString() {
		return descricao;
	}
}
