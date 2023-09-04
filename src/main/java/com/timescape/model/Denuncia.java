package com.timescape.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.StatusDenunciaConverter;
import com.timescape.model.converter.TipoDenunciaConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonClassDescription("denuncia")
@JsonRootName(value = "denuncia", namespace = "denuncias")
@JsonPropertyOrder({"id","anonimo","tipoDenuncia","dataDenuncia","statusDenuncia","tipoMotivo","usuario"})
@Table(name = "denuncias", indexes = @Index(name = "idx_denuncias_usuario_id", columnList = "usuario_id"))
public class Denuncia {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ColumnDefault("true")
	@Column(nullable = false)
	private Boolean anonimo;

	@Convert(converter = TipoDenunciaConverter.class)
	@Column(name = "tipo_denuncia", length = 10, nullable = false)
	private TipoDenuncia tipoDenuncia;
	
	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_denuncia", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataDenuncia;
	
	@Convert(converter = StatusDenunciaConverter.class)
	@Column(name = "status_denuncia", length = 20, nullable = false)
	private StatusDenuncia statusDenuncia;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "tipo_motivo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_denuncia_tipo_motivo"))
	private TipoMotivo tipoMotivo;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_denuncia_usuario"))
	private Usuario usuario;

}
