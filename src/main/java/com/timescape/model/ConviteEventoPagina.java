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
@JsonClassDescription("conviteEventoPagina")
@JsonRootName(value = "conviteEventoPagina", namespace = "convitesEventosPaginas")
@JsonPropertyOrder({"id","tipo","confirmado","dataConvite","dataConfirmacao","organizacao","seguidorPagina","eventoPagina"})
@Table(
	name = "convites_eventos_paginas", 
	indexes = {
		@Index(name = "uk_convites_eventos_paginas_evento_pagina_id", columnList = "evento_pagina_id"),
		@Index(name = "uk_convites_eventos_paginas_seguidor_pagina_id", columnList = "seguidor_pagina_id"),
	},
	uniqueConstraints = @UniqueConstraint(name = "uk_convites_eventos_paginas_evento_pagina_id_seguidor_pagina_id", columnNames = {"evento_pagina_id","seguidor_pagina_id"})
)
@DiscriminatorValue("Evento da página")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_convites_eventos_paginas_convite"))
public class ConviteEventoPagina extends Convite {

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean organizacao;
	
	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "evento_pagina_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_convites_eventos_paginas_evento_pagina"))
	private EventoPagina eventoPagina;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "seguidor_pagina_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_convites_eventos_paginas_seguidor_pagina"))
	private SeguidorPagina seguidorPagina;

}
