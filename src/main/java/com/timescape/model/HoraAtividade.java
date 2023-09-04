package com.timescape.model;

import java.time.LocalTime;
import java.util.UUID;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.DiaSemanaConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "horas_atividades")
@JsonClassDescription("horaAtividade")
@JsonPropertyOrder({"id","diaSemana","horaInicio","horaFim"})
@JsonRootName(value = "horaAtividade", namespace = "horasAtividades")
@Check(name = "ck_horas_atividades_hora_inicio", constraints = "hora_inicio < hora_fim")
public class HoraAtividade {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 20, nullable = false)
	@Convert(converter = DiaSemanaConverter.class)
	private DiaSemana diaSemana;

	@JsonFormat(shape = Shape.STRING)
	@Column(name = "hora_inicio", nullable = false)
	private LocalTime horaInicio;

	@JsonFormat(shape = Shape.STRING)
	@Column(name = "hora_fim", nullable = false)	
	private LocalTime horaFim;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "pagina_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_hora_atividade_pagina"))
	private Pagina pagina;
}
