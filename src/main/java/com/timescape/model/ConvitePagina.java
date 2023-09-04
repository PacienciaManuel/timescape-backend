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
@JsonClassDescription("convitePagina")
@JsonRootName(value = "convitePagina", namespace = "convitesPaginas")
@JsonPropertyOrder({"id","tipo","confirmado","dataConvite","dataConfirmacao","pagina","usuario"})
@Table(
	name = "convites_paginas", 
	indexes = {
		@Index(name = "idx_convites_paginas_pagina_id", columnList = "pagina_id"),
		@Index(name = "idx_convites_paginas_usuario_id", columnList = "usuario_id"),
	},
	uniqueConstraints = @UniqueConstraint(name = "uk_convites_paginas_pagina_id_usuario_id", columnNames = {"pagina_id","usuario_id"})
)
@DiscriminatorValue("Seguir página")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_convites_paginas_convite"))
public class ConvitePagina extends Convite {

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "pagina_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_convite_pagina_pagina"))
	private Pagina pagina;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_convite_pagina_usuario"))
	private Usuario usuario;

}
