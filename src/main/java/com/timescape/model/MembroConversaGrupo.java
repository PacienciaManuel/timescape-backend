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
@JsonClassDescription("membroConversaGrupo")
@JsonRootName(value = "membroConversaGrupo", namespace = "membrosConversasGrupos")
@JsonPropertyOrder({"id","administrador","dataMembro","usuario","usuarioAdicionador"})
@Table(
	name = "membros_conversas_grupos", 
	indexes = @Index(name = "idx_membros_conversas_grupos_conversa_grupo_id", columnList = "conversa_grupo_id"),
	uniqueConstraints = @UniqueConstraint(name = "uk_membros_conversas_grupos_conversa_grupo_id_usuario_id", columnNames = {"conversa_grupo_id","usuario_id",})
)
public class MembroConversaGrupo {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean administrador;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_membro", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataMembro;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "conversa_grupo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_membro_conversa_grupo_conversa_grupo"))
	private ConversaGrupo conversaGrupo;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_membro_conversa_usuario_usuario"))
	private Usuario usuario;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_adicionador_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_membro_conversa_usuario_adicionador"))
	private Usuario usuarioAdicionador;
}
