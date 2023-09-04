package com.timescape.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Length;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
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
@Table(name = "comentarios")
@JsonInclude(Include.NON_NULL)
@JsonClassDescription("comentario")
@JsonRootName(value = "comentario", namespace = "comentarios")
@JsonPropertyOrder({"id","texto","dataComentario","totalReacoes","totalRespostas","arquivo","usuario","denuncias","ocultos"})
public class Comentario {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(length = Length.LONG32)
	private String texto;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_comentario", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataComentario;
	
	@Formula("(SELECT COUNT(cp.comentario_id) FROM comentarios c LEFT JOIN reacoes_comentarios rc ON (rc.comentario_id=c.id) WHERE c.id=id GROUP BY c.id)")
	private Long totalReacoes;
	
	@Formula("(SELECT COUNT(r.id) FROM comentarios c LEFT JOIN respostas r ON (r.comentario_id=c.id) WHERE c.id=id GROUP BY c.id)")
	private Long totalRespostas;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "arquivo_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_comentario_arquivo"))
	private Arquivo arquivo;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_comentario_usuario"))
	private Usuario usuario;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "comentario", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ReacaoComentario> reacoes;

	@JoinTable(
		name="denucias_comentarios",
		indexes = {
				@Index(name = "idx_denucias_comentarios_comentarios_id", columnList = "comentarios_id"),
				@Index(name = "idx_denucias_comentarios_denuncia_id", columnList = "denuncia_id")
		},
        joinColumns=@JoinColumn(name="comentarios_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_denucias_comentarios_comentario")),
        inverseJoinColumns=@JoinColumn(name="denuncia_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_denucias_comentarios_denuncia"))
	)
	@OrderBy("dataDenuncia DESC")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Denuncia> denuncias;

	@JoinTable(
		name="comentarios_ocultos",
		indexes = {
				@Index(name = "idx_comentarios_ocultos_comentarios_id", columnList = "comentarios_id"),
				@Index(name = "idx_comentarios_ocultos_usuario_id", columnList = "usuario_id")
		},
		uniqueConstraints = @UniqueConstraint(name = "uk_publicacao_id_usuario_id", columnNames = {"comentarios_id","usuario_id"}),
        joinColumns=@JoinColumn(name="comentarios_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_comentarios_ocultos_comentario")),
        inverseJoinColumns=@JoinColumn(name="usuario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_comentarios_ocultos_usuario"))
	)
	@OrderBy("nome ASC")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Usuario> ocultos;

	@JoinTable(
		name="usuarios_notificacao_comentarios",
		indexes = {
				@Index(name = "idx_usuarios_notificacao_comentarios_comentario_id", columnList = "comentario_id"),
				@Index(name = "idx_usuarios_notificacao_comentarios_usuario_id", columnList = "usuario_id")
		},
		uniqueConstraints = @UniqueConstraint(name = "uk_usuarios_notificacar_comentarios_comentario_id_usuario_id", columnNames = {"comentario_id","usuario_id"}),
        joinColumns=@JoinColumn(name="comentario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_usuarios_notificacao_comentarios_comentario")),
        inverseJoinColumns=@JoinColumn(name="usuario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_usuarios_notificacao_comentarios_usuario"))
	)
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Usuario> usuariosNotificacao;

}
