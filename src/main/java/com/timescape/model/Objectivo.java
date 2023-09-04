package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum Objectivo {
	CONHECER_PESSOA("Conhecer pessoas"),
	PLANEAR_E_COODINAR("Planear e coordinar"),
	APRENDER_OU_PARTILHAR_UM_ENTERESSE("Aprender ou partilhar um interesse"),
	DAR_E_RECEBER_APOIO("Dar ou receber apoio"),
	AJUDAR_NUMA_EMERGENCENCIA_OU_CRISE("Ajudar numa emergência ou crise"),
	COMPRA_E_VENDA("Compra e venda"),
	PROMOVER_UM_NEGOCIO("Promover um negócio"),
	OUTRO_OBJECTIVO("Outro objectivo");

	@Getter
	@JsonValue
	private final String descricao;
	
	public static Objectivo of(String descricao) {
		for (Objectivo objectivo : Objectivo.values()) {
			if (objectivo.descricao.equalsIgnoreCase(descricao)) {
				return objectivo;
			}
		}
		throw new IllegalArgumentException("Objectivo inválida: " + descricao);
	}
}
