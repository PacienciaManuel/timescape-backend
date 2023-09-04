package com.timescape.model;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@DiscriminatorValue("Grupo")
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@JsonClassDescription("publicacaoGrupo")
@JsonRootName(value = "publicacaoGrupo", namespace = "publicacoesGrupos")
@Table(name = "publicacoes_grupos", indexes = @Index(name = "idx_publicacoes_grupos_grupo_id", columnList = "grupo_id"))
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_publicacoes_grupos_publicacao"))
@JsonPropertyOrder({
	"id","texto","autorizada","fixa","localizacao",
	"sentimento","dataPublicacao","privacidade","arquivo",
	"grupo","usuario","identificados","denuncias","ocultas",	
})
public class PublicacaoGrupo extends Publicacao {

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean fixa;

	@ColumnDefault("true")
	@Column(name = "autorizada", nullable = false)
	private Boolean autorizada;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "grupo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_publicacao_grupo_grupo"))
	private Grupo grupo;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_publicacao_grupo_usuario"))
	private Usuario usuario;

	@JoinTable(
		name="membros_grupos_identificados",
		indexes = {
			@Index(name = "idx_membros_grupos_identificados_publicacao_id", columnList = "publicacao_id"),
			@Index(name = "idx_membros_grupos_identificados_membro_grupo_id", columnList = "membro_grupo_id"),
		},
		uniqueConstraints = @UniqueConstraint(name = "uk_membros_grupos_identificados_publicacao_id_membro_grupo_id", columnNames = {"publicacao_id", "membro_grupo_id"}),
        joinColumns=@JoinColumn(name="publicacao_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_membros_grupos_identificados_publicacao")),
        inverseJoinColumns=@JoinColumn(name="membro_grupo_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_membros_grupos_identificados_membro_grupo"))
	)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<MembroGrupo> identificados;

}
