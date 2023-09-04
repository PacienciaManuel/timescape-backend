package com.timescape.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.Checks;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.Column;
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
@JsonClassDescription("amizade")
@JsonRootName(value = "amizade", namespace = "amizades")
@Checks(@Check(constraints = "usuario_bloqueador_id=usuario_solicitante_id OR usuario_bloqueador_id=usuario_solicitado_id"))
@JsonPropertyOrder({"id","dataAmizade","dataSolicitacao","confirmado","usuarioSolicitante","usuarioSolicitado","usuarioBloqueador"})
@Table(
	name = "amizades", 
	indexes = {
		@Index(name = "idx_usuario_solicitante_id", columnList = "usuario_solicitante_id"),
		@Index(name = "idx_usuario_solicitado_id", columnList = "usuario_solicitado_id"),
	},
	uniqueConstraints = @UniqueConstraint(name = "uk_usuario_solicitante_id_usuario_solicitado_id", columnNames = {"usuario_solicitante_id","usuario_solicitado_id"})
)
public class Amizade {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "data_amizade")
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	private LocalDateTime dataAmizade;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_solicitacao", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataSolicitacao;

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean confirmado;

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean bloaqueada;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_solicitante_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_amizade_usuario_solicitante"))
	private Usuario usuarioSolicitante;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_solicitado_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_amizade_usuario_solicitado"))
	private Usuario usuarioSolicitado;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_bloqueador_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_amizade_usuario_bloqueador"))
	private Usuario usuarioBloqueador;
}