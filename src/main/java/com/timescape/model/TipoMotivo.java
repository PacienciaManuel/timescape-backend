package com.timescape.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.Length;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "tipos_motivos")
@JsonClassDescription("tipoMotivo")
@JsonPropertyOrder({"id","descricao","motivo","detalhes"})
@JsonRootName(value = "tipoMotivo", namespace = "tipoMotivos")
public class TipoMotivo {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = Length.LONG32, nullable = false)
	@NotNull(message = "{TipoMotivo.descricao.notnull}")
	@NotBlank(message = "{TipoMotivo.descricao.notblank}")
	private String descricao;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "motivo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_tipos_motivos_motivo"))
	private Motivo motivo;
	
	@Column(name = "descricao", length = Length.LONG32, nullable = false)
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "detalhes_tipos_motivos", joinColumns = @JoinColumn(name = "tipo_motivo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_detalhes_tipos_motivos_tipo_motivo")))
	private List<String> detalhes;
}
