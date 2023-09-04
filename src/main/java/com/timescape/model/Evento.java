package com.timescape.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.Length;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.PrivacidadeConverter;
import com.timescape.model.converter.TipoPresencaConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Evento")
@JsonInclude(Include.NON_NULL)
@JsonClassDescription("evento")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonRootName(value = "evento", namespace = "eventos")
@Table(name = "eventos", indexes = @Index(name = "idx_eventos_nome", columnList = "nome"))
@DiscriminatorColumn(name = "propritario", length = 20, discriminatorType = DiscriminatorType.STRING)
@JsonPropertyOrder({"id","nome","autoria","tipoPresenca","local","convidadoConvidar","dataInicio","dataFim","privacidade","detalhes"})
public class Evento {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "{Evento.nome.notblank}")
	@Size(max = 100, message = "{Evento.noma.size}")
	private String nome;
	
	@Convert(converter = TipoPresencaConverter.class)
	@Column(name = "tipo_evento", length = 20, nullable = false)
	private TipoPresenca tipoPresenca;
	
    @Column(insertable=false, updatable=false)
    private String propritario;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "{Evento.local.notblank}")
	@Size(max = 100, message = "{Evento.local.size}")
	private String local;

	@ColumnDefault("false")
	@Column(name = "convidado_convidar", nullable = false)
	private Boolean convidadoConvidar;

	@JsonFormat(shape = Shape.STRING)
	@Column(name = "data_inicio", nullable = false)
	private LocalDateTime dataInicio;

	@JsonFormat(shape = Shape.STRING)
	@Column(name = "data_fim", nullable = false)	
	private LocalDateTime dataFim;
	
	@Convert(converter = PrivacidadeConverter.class)
	@Column(name = "privacidade", length = 20, nullable = false)
	private Privacidade privacidade;
	
	@Column(length = Length.LONG32)
	private String detalhes;
}
