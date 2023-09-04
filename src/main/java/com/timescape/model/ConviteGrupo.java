package com.timescape.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

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
@JsonClassDescription("convitegrupo")
@JsonRootName(value = "convitegrupo", namespace = "convitesGrupos")
@JsonPropertyOrder({"id","tipo","confirmado","dataConvite","dataConfirmacao","grupo","usuario"})
@Table(
	name = "convites_grupos", 
	indexes = {
		@Index(name = "uk_convites_grupos_grupo_id", columnList = "grupo_id"),
		@Index(name = "uk_convites_grupos_usuario_id", columnList = "usuario_id"),
	},
	uniqueConstraints = @UniqueConstraint(name = "uk_convites_grupos_grupo_id_usuario_id", columnNames = {"grupo_id","usuario_id"})
)
@DiscriminatorValue("Aderir grupo")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_convites_grupos_convite"))
public class ConviteGrupo extends Convite {

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "grupo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_convite_grupo_grupo"))
	private Grupo grupo;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_convite_grupo_usuario"))
	private Usuario usuario;

}
