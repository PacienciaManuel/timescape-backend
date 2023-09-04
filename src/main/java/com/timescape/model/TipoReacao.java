package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum TipoReacao {
	GOSTO("Gosto"), 
	ADORO("Adoro"), 
	CORAGEM("Coragem"), 
	RISO("Riso"), 
	SURPRESA("Surpresa"), 
	TRISTEZA("Tristeza"), 
	IRA("Ira");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static TipoReacao of(String descricao) {
		for (TipoReacao tipoReacao : TipoReacao.values()) {
			if (tipoReacao.descricao.equalsIgnoreCase(descricao)) {
				return tipoReacao;
			}
		}
		throw new IllegalArgumentException("Tipo de reacão inválido: " + descricao);
	}
}
