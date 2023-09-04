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
import com.timescape.model.converter.PrivacidadeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@JsonClassDescription("relacionamento")
@JsonRootName(value = "relacionamento", namespace = "relacionamentos")
@JsonPropertyOrder({"id","estadoCivil","privacidade","usuario","parceiro"})
@Table(
	name = "relacionamentos", 
	indexes = {
		@Index(name = "idx_relacionamentos_usuario_id", columnList = "usuario_id"),
		@Index(name = "idx_relacionamentos_parceiro_id", columnList = "parceiro_id"),
	},
	uniqueConstraints = @UniqueConstraint(name = "uk_relacionamentos_usuario_id", columnNames = "usuario_id")
)
public class Relacionamento {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Convert(converter = PrivacidadeConverter.class)
	@Column(name = "estado_civil", length = 20, nullable = false)
	private EstadoCivil estadoCivil;
	
	@Convert(converter = PrivacidadeConverter.class)
	@Column(name = "privacidade", length = 20, nullable = false)
	private Privacidade privacidade;

	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_relacionamento_usuario"))
	private Usuario usuario;
	
	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "parceiro_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_relacionamento_parceiro"))
	private Usuario parceiro;

}
