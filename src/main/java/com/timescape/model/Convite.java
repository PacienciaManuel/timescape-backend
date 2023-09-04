package com.timescape.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "convites")
@JsonInclude(Include.NON_NULL)
@DiscriminatorValue("Convite")
@JsonClassDescription("convite")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonRootName(value = "convite", namespace = "convites")
@JsonPropertyOrder({"id","tipo","confirmado","dataConvite","dataConfirmacao"})
@DiscriminatorColumn(name = "tipo", length = 50, discriminatorType = DiscriminatorType.STRING)
public abstract class Convite {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
    @Column(insertable=false, updatable=false)
    private String tipo;

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean confirmado;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_convite", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataConvite;

	@Column(name = "data_confirmacao")
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	private LocalDateTime dataConfirmacao;

}
