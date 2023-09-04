package com.timescape.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.Length;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.PrivacidadeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "albuns")
@JsonInclude(Include.NON_NULL)
@JsonClassDescription("album")
@JsonRootName(value = "album", namespace = "albuns")
@JsonPropertyOrder({"id","nome","dataCricao","privacidade","descricao"})
public class Album {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "{Album.nome.notblank}")
	@Size(max = 100, message = "{Album.noma.size}")
	private String nome;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_criacao", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataCricao;
	
	@Convert(converter = PrivacidadeConverter.class)
	@Column(name = "privacidade", length = 20, nullable = false)
	private Privacidade privacidade;
	
	@Column(length = Length.LONG32)
	private String descricao;

}
