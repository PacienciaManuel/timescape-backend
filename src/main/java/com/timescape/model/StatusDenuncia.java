package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum StatusDenuncia {
	EM_PRECESSO("Em processo"),
	NOGADO("Negado"), 
	RESOLVIDO("Resolvido");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static StatusDenuncia of(String descricao) {
		for (StatusDenuncia diaSemana : StatusDenuncia.values()) {
			if (diaSemana.descricao.equalsIgnoreCase(descricao)) {
				return diaSemana;
			}
		}
		throw new IllegalArgumentException("Status da denúncia inválida: " + descricao);
	}
}
