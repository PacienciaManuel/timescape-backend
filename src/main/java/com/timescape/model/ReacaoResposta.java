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
@JsonClassDescription("reacaoResposta")
@JsonPropertyOrder({"id","dataReacao","tipoReacao","usuario"})
@JsonRootName(value = "reacaoResposta", namespace = "reacoesRespostas")
@Table(
	name = "reacoes_respostas",
	indexes = @Index(name = "idx_reacoes_respostas_resposta_id", columnList = "resposta_id"),
	uniqueConstraints = @UniqueConstraint(name = "uk_reacoes_respostas_resposta_id_usuario_id", columnNames = {"resposta_id","usuario_id"})
)
@DiscriminatorValue("Resposta")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_reacoes_respostas_reacao"))
public class ReacaoResposta extends Reacao {

	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "resposta_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_reacao_resposta_resposta"))
	private Resposta resposta;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_reacao_resposta_usuario"))
	private Usuario usuario;

}
