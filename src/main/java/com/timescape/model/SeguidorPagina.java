package com.timescape.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonClassDescription("seguidorPagina")
@JsonPropertyOrder({"id","dataSeguidor","usuario"})
@JsonRootName(value = "seguidorPagina", namespace = "seguidoresPaginas")
@Table(
	name = "seguidores_paginas", 
	indexes = {
		@Index(name = "idx_seguidores_paginas_pagina_id", columnList = "pagina_id"),
		@Index(name = "idx_seguidores_paginas_usuario_id", columnList = "usuario_id")
	},
	uniqueConstraints = @UniqueConstraint(name = "uk_seguidores_paginas_pagina_id_usuario_id", columnNames = {"pagina_id","usuario_id"})
)
public class SeguidorPagina {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_seguidor", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataSeguidor;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "pagina_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_seguidor_pagina_pagina"))
	private Pagina pagina;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_seguidor_pagina_usuario"))
	private Usuario usuario;

}
