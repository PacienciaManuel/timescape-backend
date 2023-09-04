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
@DiscriminatorValue("Página")
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@JsonClassDescription("publicacaoPagina")
@JsonRootName(value = "publicacaoPagina", namespace = "publicacoesPaginas")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_publicacoes_paginas_publicacao"))
@JsonPropertyOrder({
	"id","texto","fixa","localizacao","sentimento",
	"dataPublicacao","privacidade","album","arquivo",
	"pagina","denuncias","ocultas",
})
@Table(name = "publicacoes_paginas", indexes = @Index(name = "idx_publicacoes_paginas_pagina_id", columnList = "pagina_id"))
public class PublicacaoPagina extends Publicacao {

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean fixa;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "pagina_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_publicacao_pagina_pagina"))
	private Pagina pagina;

}
