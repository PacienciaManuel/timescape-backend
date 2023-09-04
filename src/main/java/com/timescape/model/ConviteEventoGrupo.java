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
@JsonClassDescription("conviteEventoGrupo")
@JsonRootName(value = "conviteEventoGrupo", namespace = "convitesEventosGrupos")
@JsonPropertyOrder({"id","tipo","confirmado","dataConvite","dataConfirmacao","organizacao","membroGrupo","eventoGrupo"})
@Table(
	name = "convites_eventos_grupos", 
	indexes = {
		@Index(name = "uk_convites_eventos_grupos_evento_grupo_id", columnList = "evento_grupo_id"),
		@Index(name = "uk_convites_eventos_grupos_membro_grupo_id", columnList = "membro_grupo_id"),
	},
	uniqueConstraints = @UniqueConstraint(name = "uk_convites_eventos_grupos_evento_grupo_id_membro_grupo_id", columnNames = {"evento_grupo_id","membro_grupo_id"})
)
@DiscriminatorValue("Evento do grupo")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_convites_eventos_grupos_convite"))
public class ConviteEventoGrupo extends Convite {

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean organizacao;
	
	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "evento_grupo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_convites_eventos_grupos_evento_grupo"))
	private EventoGrupo eventoGrupo;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "membro_grupo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_convites_eventos_grupos_membro_grupo"))
	private MembroGrupo membroGrupo;

}
