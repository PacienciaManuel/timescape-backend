package com.timescape.model;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.NivelIdiomaConverter;
import com.timescape.model.converter.PrivacidadeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
//@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonClassDescription("idiomaUsuario")
@JsonRootName(value = "idiomaUsuario", namespace = "idiomasUsuarios")
@JsonPropertyOrder({"id","nome","nivelIdioma","privacidade","usuario"})
@Table(
	name = "idiomas_usuarios", 
	indexes = @Index(name = "idx_usuario_id", columnList = "usuario_id"),
	uniqueConstraints = @UniqueConstraint(name = "uk_nome_usuario_id", columnNames = {"nome","usuario_id"})
)
public class IdiomaUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 50, nullable = false)
	@NotBlank(message = "{IdiomaUsuario.nome.notblank}")
	@Size(max = 50, message = "{IdiomaUsuario.nome.size}")
	private String nome;
	
	@Convert(converter = NivelIdiomaConverter.class)
	@Column(name = "nivel_idioma", length = 20, nullable = false)
	private NivelIdioma nivelIdioma;
	
	@Convert(converter = PrivacidadeConverter.class)
	@Column(name = "privacidade", nullable = false, columnDefinition = "varchar(20) default 'Público'")
	private Privacidade privacidade;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_idioma_usuario"))
	private Usuario usuario;
	
}
