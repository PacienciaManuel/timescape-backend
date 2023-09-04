package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum TipoDenuncia {
	PUBLICACAO("Publicação"),
	COMENTARIO("Comentário"),
	RESPOSTA("Resposta");

	@Getter
	@JsonValue
	private final String descricao;
	
	public static TipoDenuncia of(String descricao) {
		for (TipoDenuncia tipoDenuncia : TipoDenuncia.values()) {
			if (tipoDenuncia.descricao.equalsIgnoreCase(descricao)) {
				return tipoDenuncia;
			}
		}
		throw new IllegalArgumentException("Tipo de denúncia inválida: " + descricao);
	}
}
