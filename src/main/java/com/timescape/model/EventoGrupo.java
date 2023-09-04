package com.timescape.model;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@DiscriminatorValue("Grupo")
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@JsonClassDescription("eventoGrupo")
@JsonRootName(value = "eventoGrupo", namespace = "eventosGrupos")
@Table(name = "eventos_grupos", indexes = @Index(name = "idx_eventos_grupos_grupo_id", columnList = "grupo_id"))
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_eventos_grupos_evento"))
@JsonPropertyOrder({"id","nome","tipoEvento","autoria","local","convidadoConvidar","dataInicio","dataFim","privacidade","totalConvidados"})
public class EventoGrupo extends Evento {

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "grupo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_eventos_grupos_grupo"))
	private Grupo grupo;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "membro_grupo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_eventos_grupos_membro_grupo"))
	private MembroGrupo membroGrupo;
	
	@Formula("(SELECT COUNT(cep.evento_id) FROM eventos e LEFT JOIN convidados_eventos_paginas cep ON (cep.evento_grupo_id=e.id) WHERE e.id=id GROUP BY e.id)")
	private String totalConvidados;

}
