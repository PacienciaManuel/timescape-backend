package com.timescape.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Length;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@JsonClassDescription("perfilUsuario")
@JsonRootName(value = "perfilUsuario", namespace = "perfilUsuarios")
@JsonPropertyOrder({"id","nome","genero","dataNascimento","fotoPerfil","senha"})
@Table(
	name = "usuarios", 
	indexes = @Index(name = "idx_usuario_id", columnList = "usuario_id"),
	uniqueConstraints = @UniqueConstraint(name = "uk_usuario_id", columnNames = "usuario_id")
)
public class PerfilUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "data_criacao", nullable = false)
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	private LocalDate dataCriacao;

	@Column(length = 100)
	@Size(max = 100, message = "{PerfilUsuario.cidadeAtual.size}")
	private String cidadeAtual;
	
	@Column(length = Length.LONG32)
	private String biografia;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "pais_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_perfil_pais"))
	private Pais pais;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilUsuario", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Pronome> pronomes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilUsuario", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<TrabalhoUsuario> trabalhosUsuario;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilUsuario", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<FormacaoEscola> formacoesEscolares;

	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_perfil_usuario"))
	private Usuario usuario;
}
