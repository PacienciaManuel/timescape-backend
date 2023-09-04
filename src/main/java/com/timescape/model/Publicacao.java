package com.timescape.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Length;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.PrivacidadeConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@DiscriminatorValue("Publicação")
@JsonClassDescription("publicacao")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonRootName(value = "usuario", namespace = "usuarios")
@JsonPropertyOrder({
	"id","texto","tipo","localizacao","dataPublicacao","privacidade",
	"totalComentario","totalReacoes","sentimento","midias","denuncias","ocultas"
})
@Table(name = "publicacoes", indexes = @Index(name = "idx_publicacoes_texto", columnList = "texto"))
@DiscriminatorColumn(name = "proprietario", length = 20, discriminatorType = DiscriminatorType.STRING)
public class Publicacao {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(length = Length.LONG32)
	private String texto;
	
    @Column(insertable=false, updatable=false)
    private String proprietario;
	
	private String localizacao;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_publicacao", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataPublicacao;
	
	@Convert(converter = PrivacidadeConverter.class)
	@Column(name = "privacidade", length = 20, nullable = false)
	private Privacidade privacidade;
	
	@Formula("(SELECT COUNT(cp.comentario_id) FROM publicacoes p LEFT JOIN comentarios_publicacoes cp ON (cp.publicacao_id=p.id) WHERE p.id=id GROUP BY p.id)")
	private Long totalComentario;
	
	@Formula("(SELECT COUNT(cp.comentario_id) FROM publicacoes p LEFT JOIN reacoes_publicacoes rp ON (rp.publicacao_id=p.id) WHERE p.id=id GROUP BY p.id)")
	private Long totalReacoes;

	@ManyToOne
	@JoinColumn(name = "sentimento_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_publicao_sentimento"))
	private Sentimento sentimento;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "album_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_publicao_album"))
	private Album album;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "publicacao", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<MidiaPublicacao> midias;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "publicacao", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ReacaoPublicacao> reacoes;

	@JoinTable(
		name="denucias_publicacoes",
		indexes = {
				@Index(name = "idx_denucias_publicacoes_publicacao_id", columnList = "publicacao_id"),
				@Index(name = "idx_denucias_publicacoes_denuncia_id", columnList = "denuncia_id")
		},
        joinColumns=@JoinColumn(name="publicacao_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_denucias_publicacoes_publicacao")),
        inverseJoinColumns=@JoinColumn(name="denuncia_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_denucias_publicacoes_denuncia"))
	)
	@OrderBy("dataDenuncia DESC")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Denuncia> denuncias;

	@JoinTable(
		name="publicacoes_ocultas",
		indexes = {
				@Index(name = "idx_publicacoes_ocultas_publicacao_id", columnList = "publicacao_id"),
				@Index(name = "idx_publicacoes_ocultas_usuario_id", columnList = "usuario_id")
		},
		uniqueConstraints = @UniqueConstraint(name = "uk_publicacoes_ocultas_publicacao_id_usuario_id", columnNames = {"publicacao_id","usuario_id"}),
        joinColumns=@JoinColumn(name="publicacao_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_publicacoes_ocultas_publicacao")),
        inverseJoinColumns=@JoinColumn(name="usuario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_publicacoes_ocultas_usuario"))
	)
	@OrderBy("nome ASC")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Usuario> ocultas;

	@JoinTable(
		name="comentarios_publicacoes",
		indexes = {
				@Index(name = "idx_comentarios_publicacoes_publicacao_id", columnList = "publicacao_id"),
				@Index(name = "idx_comentarios_publicacoes_comentario_id", columnList = "comentario_id")
		},
        joinColumns=@JoinColumn(name="publicacao_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_comentarios_publicacoes_publicacao")),
        inverseJoinColumns=@JoinColumn(name="comentario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_comentarios_publicacoes_comentario"))
	)
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Comentario> comentarios;

	@JoinTable(
		name="usuarios_notificacao_publicacoes",
		indexes = {
				@Index(name = "idx_usuarios_notificacao_publicacoes_publicacao_id", columnList = "publicacao_id"),
				@Index(name = "idx_usuarios_notificacao_publicacoes_usuario_id", columnList = "usuario_id")
		},
		uniqueConstraints = @UniqueConstraint(name = "uk_usuarios_notificacao_publicacoes_publicacao_id_usuario_id", columnNames = {"publicacao_id","usuario_id"}),
        joinColumns=@JoinColumn(name="publicacao_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_usuarios_notificacao_publicacoes_publicacao")),
        inverseJoinColumns=@JoinColumn(name="usuario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_usuarios_notificacao_publicacoes_usuario"))
	)
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Usuario> usuariosNotificacao;
	
}
