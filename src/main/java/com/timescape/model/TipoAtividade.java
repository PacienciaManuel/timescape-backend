package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum TipoAtividade {
	A_CELEBRAR("A celebrar"),
	A_VER("A ver"),
	A_COMER("A comer"),
	A_BEBER("A beber"),
	A_FREQUENTAR("A frequentar"),
	A_VIAJAR_PARA("A viajar"),
	A_OUVIR("A ouvir"),
	A_LER("A ler"),
	A_JOGAR("A jogar"),
	A_APOJAR("A apoiar");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static TipoAtividade of(String descricao) {
		for (TipoAtividade estadoCivil : TipoAtividade.values()) {
			if (estadoCivil.descricao.equalsIgnoreCase(descricao)) {
				return estadoCivil;
			}
		}
		throw new IllegalArgumentException("Estado civil inválido: " + descricao);
	}

}
