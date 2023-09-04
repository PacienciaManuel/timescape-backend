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
@JsonClassDescription("sentimento")
@JsonPropertyOrder({"id","descricao","emoji"})
@JsonRootName(value = "sentimento", namespace = "sentimentos")
@Table(name = "sentimentos", uniqueConstraints = @UniqueConstraint(name = "uk_sentimentos_descricao", columnNames = "descricao"))
public class Sentimento {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "{Sentimento.descricao.notblank}")
	@Size(max = 100, message = "{Sentimento.descricao.size}")
	private String descricao;

	@Column(length = 1, nullable = false)
	@NotBlank(message = "{Sentimento.emoji.notblank}")
	@Size(max = 1, message = "{Sentimento.emoji.size}")
	private String emoji;

}
