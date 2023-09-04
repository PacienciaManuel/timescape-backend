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
@JsonClassDescription("reacaoPublicacao")
@JsonPropertyOrder({"id","dataReacao","tipoReacao","usuario"})
@JsonRootName(value = "reacaoPublicacao", namespace = "reacoesPublicacoes")
@Table(
	name = "reacoes_publicacoes",
	indexes = @Index(name = "idx_reacoes_publicacoes_publicacao_id", columnList = "publicacao_id"),
	uniqueConstraints = @UniqueConstraint(name = "uk_reacoes_publicacoes_publicacao_id_usuario_id", columnNames = {"publicacao_id","usuario_id"})
)
@DiscriminatorValue("Publicação")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_reacoes_publicacoes_reacao"))
public class ReacaoPublicacao extends Reacao {

	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "publicacao_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_reacao_publicacao_publicacao"))
	private Publicacao publicacao;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_reacao_publicacao_usuario"))
	private Usuario usuario;

}
