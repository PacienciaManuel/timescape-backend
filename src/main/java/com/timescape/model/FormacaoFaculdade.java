package com.timescape.model;

import java.time.LocalDate;

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
import com.timescape.model.converter.TipoPrivacidadeConverter;

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
@JsonClassDescription("formacaoFaculdade")
@Check( constraints = "dataInicio < dataConclusao" )
@JsonRootName(value = "formacaoFaculdade", namespace = "formacoesFaculdades")
@JsonPropertyOrder({"id","escola","dataInicio","dataConclusao","tipoPrivacidade"})
@Table(
	name = "formacoes_faculdades", 
	uniqueConstraints = @UniqueConstraint(name = "uk_escola_perfil_usuario_id", columnNames = {"nome","perfil_usuario_id",})
)
public class FormacaoFaculdade {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private Long id;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "{TrabalhoUsuario.nome.notblank}")
	@Size(max = 100, message = "{TrabalhoUsuario.noma.size}")
	private String faculdade;

	@Column(length = 100, nullable = false)
	@Size(max = 100, message = "{FormacaoFaculdade.especialidade.size}")
	private String especialidade;

	@Column(nullable = false)
	private Boolean concluiu;

	@Column(name = "data_inicio")
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	private LocalDate dataInicio;
	
	@Column(name = "data_conclusao")
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	private LocalDate dataConclusao;
	
	@Column(length = 20, nullable = false)
	@Convert(converter = TipoPrivacidadeConverter.class)
	private TipoPrivacidade tipoPrivacidade;

	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "perfil_usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_formacao_escola_perfil_usuario"))
	private PerfilUsuario perfilUsuario;
}
