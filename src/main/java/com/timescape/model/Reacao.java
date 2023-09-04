package com.timescape.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.TipoReacaoConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@Table(name = "reacoes")
@JsonClassDescription("reacao")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonRootName(value = "reacao", namespace = "reacoes")
@JsonPropertyOrder({"id","dataReacao","tipoReacao","usuario"})
@DiscriminatorValue("Reação")
@DiscriminatorColumn(name = "propritario", length = 20, discriminatorType = DiscriminatorType.STRING)
public class Reacao {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
    @Column(insertable=false, updatable=false)
    private String propritario;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_reacao", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataReacao;
	
	@Convert(converter = TipoReacaoConverter.class)
	@Column(name = "tipo_reacao", length = 20, nullable = false)
	private TipoReacao tipoReacao;

}
