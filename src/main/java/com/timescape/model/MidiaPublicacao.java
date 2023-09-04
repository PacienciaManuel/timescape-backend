package com.timescape.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.CascadeType;
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
@JsonPropertyOrder({"id","arquivo"})
@JsonClassDescription("midiaPublicacao")
@JsonRootName(value = "midiaPublicacao", namespace = "midiasPublicacoes")
@Table(name = "midias_publicacoes", indexes = @Index(name = "idx_midias_publicacoes_publicacao_id", columnList = "publicacao_id"))
public class MidiaPublicacao {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "arquivo_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_midia_publicacao_arquivo"))
	private Arquivo arquivo;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "publicacao_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_midia_publicacao_publicacao"))
	private Publicacao publicacao;

	@JoinTable(
		name="comentarios_midias_publicacoes",
		indexes = {
				@Index(name = "idx_comentarios_midias_publicacoes_midia_publicacao_id", columnList = "midia_publicacao_id"),
				@Index(name = "idx_comentarios_midias_publicacoes_comentario_id", columnList = "comentario_id")
		},
        joinColumns=@JoinColumn(name="midia_publicacao_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_ccomentarios_midias_publicacoes_midia_publicacao")),
        inverseJoinColumns=@JoinColumn(name="comentario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_comentarios_midias_publicacoes_comentario"))
	)
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Comentario> comentarios;
}
