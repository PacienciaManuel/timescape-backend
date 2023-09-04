package com.timescape.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@EqualsAndHashCode(callSuper = true)
@JsonClassDescription("reacaoComentario")
@JsonPropertyOrder({"id","dataReacao","tipoReacao","usuario"})
@JsonRootName(value = "reacaoComentario", namespace = "reacoesComentarios")
@Table(
	name = "reacoes_comentarios",
	indexes = @Index(name = "idx_reacoes_comentarios_comentario_id", columnList = "comentario_id"),
	uniqueConstraints = @UniqueConstraint(name = "uk_reacoes_comentarios_comentario_id_usuario_id", columnNames = {"comentario_id","usuario_id"})
)
@DiscriminatorValue("Comentário")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_reacoes_comentarios_reacao"))
public class ReacaoComentario extends Reacao {

	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "comentario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_reacao_comentario_comentario"))
	private Comentario comentario;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_reacao_comentario_usuario"))
	private Usuario usuario;

}
