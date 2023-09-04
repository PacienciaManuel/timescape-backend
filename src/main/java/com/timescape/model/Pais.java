package com.timescape.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonClassDescription("paise")
@JsonRootName(value = "pais", namespace = "paises")
@JsonPropertyOrder({"id","nome","codigo","telefone"})
@Table(
	name = "paises",
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_paises_nome", columnNames = "nome"),
		@UniqueConstraint(name = "uk_paises_codigo", columnNames = "codigo"),
		@UniqueConstraint(name = "uk_paises_telefone", columnNames = "telefone")
	}
)
public class Pais {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(length = 50, nullable = false)
	@NotBlank(message = "{Pais.nome.notblank}")
	@Size(max = 50, message = "{Pais.nome.size}")
	private String nome;
	
	@Column(length = 5, nullable = false)
	@NotBlank(message = "{Pais.sigla.notblank}")
	@Size(max = 5, message = "{Pais.codigo.size}")
	private String codigo;
	
	@Column(length = 10, nullable = false)
	@NotBlank(message = "{Pais.sigla.notblank}")
	@Size(max = 10, message = "{Pais.telefone.size}")
	private String telefone;
}
