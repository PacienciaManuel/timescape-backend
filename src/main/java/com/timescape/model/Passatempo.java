package com.timescape.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@JsonInclude(Include.NON_NULL)
@JsonClassDescription("passatempo")
@JsonPropertyOrder({"id","descricao","emoji"})
@JsonRootName(value = "passatempo", namespace = "passatempos")
@Table(name = "passatempos", uniqueConstraints = @UniqueConstraint(name = "uk_passatempos_descricao", columnNames = "descricao"))
public class Passatempo {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "{Passatempo.descricao.notblank}")
	@Size(max = 100, message = "{Passatempo.descricao.size}")
	private String descricao;

	@Column(length = 1, nullable = false)
	@NotBlank(message = "{Passatempo.emoji.notblank}")
	@Size(max = 1, message = "{Passatempo.emoji.size}")
	private String emoji;
	
}
