package com.timescape.model;

import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.UUID;

import org.hibernate.Length;
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
import com.timescape.model.converter.ObjectivoConverter;
import com.timescape.model.converter.PrivacidadeConverter;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@JsonClassDescription("grupo")
@JsonRootName(value = "grupo", namespace = "grupos")
@Table(name = "grupos", indexes = @Index(name = "idx_grupos_nome", columnList = "nome"))
@JsonPropertyOrder({"id","nome","oculto","autorizacaoPublicacao","dataCriacao","descricao","privacidade","fotoPerfil"})
public class Grupo {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "{Grupo.nome.notblank}")
	@Size(max = 100, message = "{Grupo.noma.size}")
	private String nome;
	
	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean oculto;
	
	@ColumnDefault("false")
	@Column(name = "autorizacao_publicacao", nullable = false)
	private Boolean autorizacaoPublicacao;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_criacao", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataCriacao;

	@Column(length = Length.LONG32)
	private String descricao;
	
	@Convert(converter = PrivacidadeConverter.class)
	@Column(name = "privacidade", length = 20, nullable = false)
	private Privacidade privacidade;

	@CollectionTable(
		name = "objectivos_grupos", 
		indexes = @Index(name = "idx_objectivos_grupos_grupo_id", columnList = "grupo_id"), 
		joinColumns = @JoinColumn(name = "grupo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_objectivos_grupos_grupo"))
	)
	@Convert(converter = ObjectivoConverter.class)
	@Column(name = "descricao", length = 100, nullable = false)
	@ElementCollection(targetClass = Objectivo.class, fetch = FetchType.EAGER)
	private SortedSet<Objectivo> objectivos;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_grupo_usuario"))
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "foto_perfil", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_grupo_foto_perfil"))
	private PublicacaoGrupo fotoPerfil;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "foto_capa", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_grupo_foto_capa"))
	private PublicacaoGrupo fotoCapa;
}
