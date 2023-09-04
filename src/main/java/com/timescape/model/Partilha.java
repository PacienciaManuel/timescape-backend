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
@DiscriminatorValue("Partilha")
@JsonClassDescription("partilha")
@EqualsAndHashCode(callSuper = true)
@JsonRootName(value = "partilha", namespace = "partilhas")
@Table(
	name = "partilhas", 
	indexes = {
		@Index(name = "idx_partilhas_publicacao_usuario_id", columnList = "publicacao_usuario_id"),
		@Index(name = "idx_partilhas_publicacao_cronologia_id", columnList = "publicacao_cronologia_id"),
		@Index(name = "idx_partilhas_publicacao_pagina_id", columnList = "publicacao_pagina_id"),
		@Index(name = "idx_partilhas_publicacao_grupo_id", columnList = "publicacao_grupo_id"),
	}
)
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_partilhas_publicacao"))
@JsonPropertyOrder({
	"id","texto","fixa","localizacao",
	"sentimento","dataPublicacao","privacidade",
	"album","arquivo","usuario","denuncias","ocultas",
})
public class Partilha extends Publicacao {

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_partilha_usuario"))
	private Usuario usuario;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "publicacao_usuario_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_partilha_publicacao_usuario"))
	private PublicacaoUsuario publicacaoUsuario;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "publicacao_cronologia_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_partilha_publicacao_cronologia"))
	private PublicacaoCronologia publicacaoCronologia;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "publicacao_pagina_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_partilha_publicacao_pagina"))
	private PublicacaoPagina publicacaoPagina;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "publicacao_grupo_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_partilha_publicacao_grupo"))
	private PublicacaoGrupo publicacaoGrupo;
}
