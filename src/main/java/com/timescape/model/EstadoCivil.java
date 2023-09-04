package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum EstadoCivil {
	SOLTEIRO("Solteiro"),
	NUMA_RELACAO("Numa Relação"),
	NOIVO("Noivo/a"),
	CASADO("Casado/a"),
	NUMA_UNIAO_CIVIL("Casado/a"),
	NUMA_UNIAO_DE_FACTO("Numa união de facto"),
	NUMA_RELACAO_ABERTA("Numa relação aberta"),
	E_COMPLICADO("É complicado"),
	SEPARADO("Separado/a"),
	DIVORCIADO("Divorciado/a"),
	VIUVO("Viúvo/a");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static EstadoCivil of(String descricao) {
		for (EstadoCivil estadoCivil : EstadoCivil.values()) {
			if (estadoCivil.descricao.equalsIgnoreCase(descricao)) {
				return estadoCivil;
			}
		}
		throw new IllegalArgumentException("Estado civil inválido: " + descricao);
	}
}
