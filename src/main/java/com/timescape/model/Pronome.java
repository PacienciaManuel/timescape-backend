package com.timescape.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
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
@JsonClassDescription("pronome")
@JsonRootName(value = "pronome", namespace = "pronomes")
@JsonPropertyOrder({"id","descricao","tipoPrivacidade"})
@Table(name = "pronomes", uniqueConstraints = @UniqueConstraint(name = "uk_descricao_&_perfil_usuario_id", columnNames = "perfil_usuario_id"))
public class Pronome {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private Long id;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "{Pronome.descricao.notblank}")
	@Size(max = 100, message = "{Pronome.descricao.size}")
	private String descricao;

	@ColumnDefault("Público")
	@Column(nullable = false)
	@Convert(converter = TipoPrivacidadeConverter.class)
	private TipoPrivacidade tipoPrivacidade;

	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "perfil_usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_pronome_perfil_usuario"))
	private PerfilUsuario perfilUsuario;
}
