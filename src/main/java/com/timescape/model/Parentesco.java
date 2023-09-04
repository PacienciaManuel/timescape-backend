package com.timescape.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum Parentesco {
	PAI("Pai"), MAE("Mãe"),
	FILHO("Filho"), FILHA("Filha"),
	IRMAO("Irmão"), IRMA("Irmã"),
	AVO_MASCULINO("Avô"), AVO_FEMININO("Avó"),
	NETO("Neto"), NETA("Neta"),
	TIO("Tio"), TIA("Tia"),
	SOBRINHO("Sobrinho"), SOBRINHA("Sobrinha"),
	BISAVO_MASCULINO("Bisavô"), BISAVO_FEMININO("Bisavó"),
	BISNETO("Bisneto"), BISNETA("Bisneta"),
	PRIMO("Primo"), PRIMA("Prima"),
	TRISAVO_MASCULINO("Trisavô"), TRISAVO_FEMININO("Trisavó"),
	TRINETO("Trineto"), TRINETA("Trineta"),
	SOGRO("Sogro"), SOGRA("Sogra"), 
	GENRO("Genro"), NORA("Nora"),
	CUNHADO("Cunhado"), CUNHADA("Cunhada"),
	PADRASTO("Padrasto"), MADRASTA("Madrasta"), 
	ENTEADO("Enteado"), ENTEADA("Enteada");

	@Getter
	@JsonValue
	private final String descricao;
	
	public static Parentesco of(String descricao) {
		for (Parentesco parentesco : Parentesco.values()) {
			if (parentesco.descricao.equalsIgnoreCase(descricao)) {
				return parentesco;
			}
		}
		throw new IllegalArgumentException("Parentesco inválida: " + descricao);
	}
}
