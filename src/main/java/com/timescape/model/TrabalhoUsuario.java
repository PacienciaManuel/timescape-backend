package com.timescape.model;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.Length;
import org.hibernate.annotations.ColumnDefault;
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
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@JsonClassDescription("trabalhoUsuario")
@JsonRootName(value = "trabalhoUsuario", namespace = "trabalhosUsuario")
@JsonPropertyOrder({"id","nome","cargo","localidade","trabalhando","dataInicio","dataFim","privacidade"})
@Table(name = "trabalhos_usuarios", uniqueConstraints = @UniqueConstraint(name = "uk_trabalhos_usuarios_nome_usuario_id", columnNames = {"nome","usuario_id",}))
public class TrabalhoUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "{TrabalhoUsuario.nome.notblank}")
	@Size(max = 100, message = "{TrabalhoUsuario.noma.size}")
	private String nome;

	@Column(length = 100)
	@Size(max = 100, message = "{TrabalhoUsuario.cargo.size}")
	private String cargo;

	@Column(length = 100)
	@Size(max = 100, message = "{TrabalhoUsuario.localidade.size}")
	private String localidade;

	@Column(length = Length.LONG32)
	private String descricao;

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean trabalhando;

	@Column(name = "data_inicio", nullable = false)
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	private LocalDate dataInicio;

	@Column(name = "data_fim")
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	private LocalDate dataFim;
	
	@Convert(converter = PrivacidadeConverter.class)
	@Column(name = "privacidade", length = 20, nullable = false)
	private Privacidade privacidade;

	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_trabalho_usuario_usuario"))
	private Usuario usuario;
}
