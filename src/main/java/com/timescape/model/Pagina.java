package com.timescape.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.Length;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;

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
@JsonClassDescription("pagina")
@JsonRootName(value = "pagina", namespace = "paginas")
@Table(name = "paginas", indexes = @Index(name = "idx_pagina_nome", columnList = "nome"))
@JsonPropertyOrder({"id","nome","biografia","site","email","morada","codigoPostal","dataCriacao","fotoPerfil"})
public class Pagina {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(length = 100, nullable = false)
	@NotBlank(message = "{Pagina.nome.notblank}")
	@Size(max = 100, message = "{Pagina.noma.size}")
	private String nome;

	@Column(length = Length.LONG32)
	private String biografia;
	
	@URL(port = 80, message = "{Pagina.site.url}")
	private String site;

	@Email(message = "{Pagina.email}", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	private String email;

	private String morada;

	@Column(name = "codigo_postal")
	private String codigoPostal;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_criacao", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataCriacao;

	@ManyToOne
	@JoinColumn(name = "foto_perfil", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_pagina_foto_perfil"))
	private PublicacaoPagina fotoPerfil;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "foto_capa", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_pagina_foto_capa"))
	private PublicacaoPagina fotoCapa;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name="telefone_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "fk_pagina_telefone"))
	private Telefone telefone;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name="usuario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_pagina_usuario"))
	private Usuario usuario;

	@JoinTable(
		name="categorias_paginas",
		indexes = @Index(name = "idx_categorias_paginas_pagina_id", columnList = "pagina_id"),
		uniqueConstraints = @UniqueConstraint(name = "uk_categorias_paginas_pagina_id_categoria_id", columnNames = {"pagina_id", "categoria_id"}),
        joinColumns=@JoinColumn(name="pagina_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_categorias_paginas_pagina")),
        inverseJoinColumns=@JoinColumn(name="categoria_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_categorias_paginas_categoria"))
	)
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Categoria> categorias;

}
