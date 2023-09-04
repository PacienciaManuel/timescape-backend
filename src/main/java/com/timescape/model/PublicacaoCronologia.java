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
@DiscriminatorValue("Cronologia")
@EqualsAndHashCode(callSuper = true)
@JsonClassDescription("publicacaoCronologia")
@JsonRootName(value = "publicacaoCronologia", namespace = "publicacoesCronologias")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_publicacoes_cronologias_publicacao"))
@Table(name = "publicacoes_cronologias", indexes = @Index(name = "idx_publicacoes_cronologias_usuario_cronologia_id", columnList = "usuario_cronologia_id"))
@JsonPropertyOrder({
	"id","texto","fixa","localizacao","sentimento",
	"dataPublicacao","privacidade","album","arquivo",
	"usuario","usuarioCronologia","denuncias","ocultas",
})
public class PublicacaoCronologia extends Publicacao {

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_publicacao_cronologia_usuario"))
	private Usuario usuario;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_cronologia_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_publicacao_cronologia_usuario_cronologia"))
	private Usuario usuarioCronologia;

}
