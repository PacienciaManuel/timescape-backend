package com.timescape.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.GeneroConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
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
@JsonClassDescription("usuario")
@JsonRootName(value = "usuario", namespace = "usuarios")
@JsonPropertyOrder({"id","nome","genero","dataNascimento","email","biografia","cidadeAtual","dataCriacao","bloqueado","fotoPerfil"})
@Table(
	name = "usuarios",
	indexes = {
		@Index(name = "idx_usuarios_nome", columnList = "nome"),
		@Index(name = "idx_usuarios_email", columnList = "email"),
	},
	uniqueConstraints = @UniqueConstraint(name = "uk_usuarios_email", columnNames = "email")
)
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "{Usuario.nome.notblank}")
	@Size(max = 100, message = "{Usuario.noma.size}")
	private String nome;

	@Column(length = 10, nullable = false)
	@Convert(converter = GeneroConverter.class)
	private Genero genero;

	@JsonFormat(shape = Shape.STRING)
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Column(length = 50, nullable = false)
	@NotBlank(message = "{Usuario.email.notblank}")
	@Size(max = 50, message = "{Usuario.email.size}")
	@Email(message = "{Usuario.email}", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	private String email;
	
	@Column(length = Length.LONG32)
	private String biografia;

	@Column(length = 100)
	@Size(max = 100, message = "{PerfilUsuario.cidadeAtual.size}")
	private String cidadeAtual;

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean bloqueado;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_criacao", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataCriacao;

	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "{Usuario.senha.notblank}")
	@Size(min = 8, message = "{Usuario.senha.size}")
	@Column(length = Length.LONG32, nullable = false)
	private String senha;

	@ManyToOne
	@JoinColumn(name = "foto_perfil", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario_publicacao_foto_perfil"))
	private PublicacaoUsuario fotoPerfil;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "foto_capa", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario_publicacao_foto_capa"))
	private PublicacaoUsuario fotoCapa;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "pais_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_usuario_pais"))
	private Pais pais;

	@JoinTable(
		name="telefones_usuarios",
		indexes = @Index(name = "idx_telefones_usuarios_usuario_id", columnList = "usuario_id"),
		uniqueConstraints = @UniqueConstraint(name = "uk_telefones_usuarios_usuario_id_telefone_id", columnNames = {"usuario_id", "telefone_id"}),
        joinColumns=@JoinColumn(name="usuario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_telefones_usuarios_usuario")),
        inverseJoinColumns=@JoinColumn(name="telefone_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_telefones_usuarios_telefone"))
	)
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Telefone> telefones;

	@JoinTable(
		name="passatempos_usuarios",
		indexes = @Index(name = "idx_passatempos_usuarios_usuario_id", columnList = "usuario_id"),
		uniqueConstraints = @UniqueConstraint(name = "uk_passatempos_usuarios_usuario_id_passatempo_id", columnNames = {"usuario_id", "passatempo_id"}),
        joinColumns=@JoinColumn(name="usuario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_passatempos_usuarios_usuario")),
        inverseJoinColumns=@JoinColumn(name="passatempo_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_passatempos_usuarios_passatempo"))
	)
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Passatempo> passatempos;

	@JoinTable(
		name="albuns_usuarios",
		indexes = {
			@Index(name = "idx_albuns_usuarios_album_id", columnList = "album_id"),
			@Index(name = "idx_albuns_usuarios_usuario_id", columnList = "usuario_id"),
		},
		uniqueConstraints = @UniqueConstraint(name = "uk_album_id_usuario_id", columnNames = {"album_id", "usuario_id"}),
        joinColumns=@JoinColumn(name="usuario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_albuns_usuarios_usuario")),
        inverseJoinColumns=@JoinColumn(name="album_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_albuns_usuarios_album"))
	)
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Album> albuns;
}
