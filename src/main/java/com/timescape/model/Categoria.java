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
import jakarta.persistence.Index;
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
@JsonClassDescription("categoria")
@JsonPropertyOrder({"id","descricao"})
@JsonRootName(value = "categoria", namespace = "categorias")
@Table(
	name = "categorias", 
	indexes = @Index(name = "idx_categoria_descricao", columnList = "descricao"),
	uniqueConstraints = @UniqueConstraint(name = "uk_categoria_descricao", columnNames = "descricao")
)
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(length = 20, nullable = false)
	@NotBlank(message = "{Categoria.descricao.notblank}")
	@Size(max = 100, message = "{Categoria.descricao.size}")
	private String descricao;
}
