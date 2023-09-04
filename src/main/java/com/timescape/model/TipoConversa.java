package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum TipoConversa {
	GERAL("Geral"),
	DAR_E_RECEBER_APOIO("Dar e receber apoio"),
	PEDIDO_PARA_ADMINISTRADOR("Pedido para administrador"),
	ANIMAR_A_AMALTA("Amimar a malta");

	@Getter
	@JsonValue
	private final String descricao;
	
	public static TipoConversa of(String descricao) {
		for (TipoConversa tipoConversa : TipoConversa.values()) {
			if (tipoConversa.descricao.equalsIgnoreCase(descricao)) {
				return tipoConversa;
			}
		}
		throw new IllegalArgumentException("Tipo de conversa inválido: " + descricao);
	}	
}
