package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum DiaSemana {
	DOMINGO("Domingo"),
	SEGUNDA_FEIRA("Segunda-Feira"),
	TERCA_FEIRA("Terça-Feira"),
	QUARTA_FEIRA("Quarta-Feira"),
	QUINTA_FEIRA("Quinta-Feira"),
	SEXTA_FEIRA("Sexta-Feira"),
	SABADO("Sábado");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static DiaSemana of(String descricao) {
		for (DiaSemana diaSemana : DiaSemana.values()) {
			if (diaSemana.descricao.equalsIgnoreCase(descricao)) {
				return diaSemana;
			}
		}
		throw new IllegalArgumentException("Dia de semana inválido: " + descricao);
	}
}
