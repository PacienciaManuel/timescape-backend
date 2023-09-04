package com.timescape.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
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
@JsonClassDescription("arquivo")
@JsonRootName(value = "arquivo", namespace = "arquivos")
@JsonPropertyOrder({"id","nome","nomeOriginal","dataArquivo","duracao","tipoConteudo"})
@Table(name = "arquivos", uniqueConstraints = @UniqueConstraint(name = "uk_arquivos_nome", columnNames = "nome"))
public class Arquivo {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String nomeOriginal;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_arquivo", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataArquivo;

	@JsonFormat(shape = Shape.STRING)
	private Duration duracao;

	@Column(name = "tipo_conteudo", length = 20, nullable = false)
	private String tipoConteudo;

}
