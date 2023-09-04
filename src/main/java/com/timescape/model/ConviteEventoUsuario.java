package com.timescape.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@JsonClassDescription("conviteEventoUsuario")
@JsonPropertyOrder({"id","tipo","confirmado","dataConvite","dataConfirmacao","organizacao","usuario","eventoUsuario"})
@JsonRootName(value = "conviteEventoUsuario", namespace = "convitesEventosUsuarios")
@Table(
	name = "convites_eventos_usuarios", 
	indexes = {
		@Index(name = "uk_convites_eventos_usuarios_evento_usuario_id", columnList = "evento_usuario_id"),
		@Index(name = "uk_convites_eventos_usuarios_usuario_id", columnList = "usuario_id"),
	},
	uniqueConstraints = @UniqueConstraint(name = "uk_convites_eventos_usuarios_evento_usuario_id_usuario_id", columnNames = {"evento_usuario_id","usuario_id"})
)
@DiscriminatorValue("Evento de usuário")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_convites_eventos_usuarios_convite"))
public class ConviteEventoUsuario extends Convite {
	
	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean organizacao;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "evento_usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_convites_eventos_usuarios_evento_usuario"))
	private EventoUsuario eventoUsuario;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_convites_eventos_usuarios_usuario"))
	private Usuario usuario;

}
