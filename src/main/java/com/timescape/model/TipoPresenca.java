package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum TipoPresenca {
	PESSOALMENTE("Pessoalmente"), VIRTUAL("Virtual");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static TipoPresenca of(String descricao) {
		for (TipoPresenca tipoEvento : TipoPresenca.values()) {
			if (tipoEvento.descricao.equalsIgnoreCase(descricao)) {
				return tipoEvento;
			}
		}
		throw new IllegalArgumentException("Tipo de evento inválido: " + descricao);
	}
}
