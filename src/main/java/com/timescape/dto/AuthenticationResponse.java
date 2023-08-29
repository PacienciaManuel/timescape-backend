package com.timescape.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.timescape.model.Usuario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"tokenAccesso","tokenAtualizacao","usuario"})
public class AuthenticationResponse {
	private Usuario usuario;
	private String tokenAccesso;
	private String tokenAtualizacao;
}
