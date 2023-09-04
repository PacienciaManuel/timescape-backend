package com.timescape.model;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.PrivacidadeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@JsonClassDescription("formacaoEscola")
@Check(constraints = "dataInicio < dataConclusao")
@JsonRootName(value = "formacaoEscola", namespace = "formacoesEscolares")
@JsonPropertyOrder({"id","escola","curso","dataInicio","dataConclusao","privacidade"})
@Table(
	name = "formacoes_escolares", 
	indexes = @Index(name = "idx_formacoes_escolares_usuario_id", columnList = "usuario_id"),
	uniqueConstraints = @UniqueConstraint(name = "uk_formacoes_escolares_escola_curso_usuario_id", columnNames = {"escola","curso","usuario_id",})
)
public class FormacaoEscola {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "{FormacaoEscola.escola.notblank}")
	@Size(max = 100, message = "{FormacaoEscola.escola.size}")
	private String escola;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "{FormacaoEscola.curso.notblank}")
	@Size(max = 100, message = "{FormacaoEscola.curso.size}")
	private String curso;

	@Column(name = "data_inicio", nullable = false)
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	private LocalDate dataInicio;
	
	@Column(name = "data_conclusao")
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	private LocalDate dataConclusao;
	
	@Convert(converter = PrivacidadeConverter.class)
	@Column(name = "privacidade", length = 20, nullable = false)
	private Privacidade privacidade;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_formacao_escola_usuario"))
	private Usuario usuario;
}
