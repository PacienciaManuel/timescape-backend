package com.timescape.model;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@DiscriminatorValue("Usuário")
@EqualsAndHashCode(callSuper = true)
@JsonClassDescription("publicacaoUsuario")
@JsonRootName(value = "publicacaoUsuario", namespace = "publicacoesUsuarios")
@Table(name = "publicacoes_usuarios", indexes = @Index(name = "idx_publicacoes_usuarios_usuario_id", columnList = "usuario_id"))
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_publicacoes_usuarios_publicacao"))
@JsonPropertyOrder({
	"id","texto","localizacao","sentimento",
	"dataPublicacao","privacidade","album","arquivo",
	"usuario","amizadesIdentificadas","denuncias","ocultas",
})
public class PublicacaoUsuario extends Publicacao {

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_publicacao_usuario_usuario"))
	private Usuario usuario;

	@JoinTable(
		name="amigos_identificados",
		indexes = {
			@Index(name = "idx_amigos_identificados_publicacao_id", columnList = "publicacao_id"),
			@Index(name = "idx_amigos_identificados_amizade_id", columnList = "amizade_id"),
		},
		uniqueConstraints = @UniqueConstraint(name = "uk_amigos_identificados_publicacao_id_amizade_id", columnNames = {"publicacao_id", "amizade_id"}),
        joinColumns=@JoinColumn(name="publicacao_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_amigos_identificados_publicacao")),
        inverseJoinColumns=@JoinColumn(name="amizade_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_amigos_identificados_amizade"))
	)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Amizade> identificados;

}
