package com.timescape.model;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.ParentescoConverter;
import com.timescape.model.converter.PrivacidadeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@JsonClassDescription("membroFamilia")
@JsonRootName(value = "membroFamilia", namespace = "membrosFamiliares")
@JsonPropertyOrder({"id","parentesco","privacidade","usuario","parente"})
@Table(
	name = "membros_familiares", 
	uniqueConstraints = @UniqueConstraint(name = "uk_membros_familiares_usuario_id_parente_id", columnNames = {"usuario_id","parente_id"})
)
public class MembroFamilia {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Convert(converter = ParentescoConverter.class)
	@Column(name = "parentesco", length = 30, nullable = false)
	private Parentesco parentesco;
		
	@Convert(converter = PrivacidadeConverter.class)
	@Column(name = "privacidade", length = 20, nullable = false)
	private Privacidade privacidade;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_membro_familia_usuario"))
	private Usuario usuario;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "parente_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_membro_familia_usuario_parente"))
	private Usuario parente;
}
