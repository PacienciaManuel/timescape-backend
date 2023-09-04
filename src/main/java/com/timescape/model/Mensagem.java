package com.timescape.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.Length;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.OneToOne;
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
@Table(name = "mensagens")
@JsonInclude(Include.NON_NULL)
@DiscriminatorValue("Mensagem")
@JsonClassDescription("mensagem")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonRootName(value = "mensagem", namespace = "mensagens")
@JsonPropertyOrder({"id","texto","tipo","dataMensagem","arquivos","reacoes"})
@DiscriminatorColumn(name = "tipo", length = 20, discriminatorType = DiscriminatorType.STRING)
public class Mensagem {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(length = Length.LONG32)
	private String texto;
	
    @Column(insertable=false, updatable=false)
    private String tipo;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_mensagem", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataMensagem;

	@JoinTable(
		name="arquivos_mensagens",
		indexes = @Index(name = "idx_arquivos_mensagens_mensagem_id", columnList = "mensagem_id"),
		uniqueConstraints = @UniqueConstraint(name = "uk_arquivos_mensagens_mensagem_id_arquivo_id", columnNames = {"mensagem_id","arquivo_id"}),
        joinColumns=@JoinColumn(name="mensagem_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_arquivos_mensagens_mensagem")),
        inverseJoinColumns=@JoinColumn(name="arquivo_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_arquivos_mensagens_arquivo"))
	)
	@OrderBy("dataArquivo ASC")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Arquivo> arquivos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "mensagem", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<ReacaoMensagem> reacoes;

	@JoinTable(
		name="respostas_mensagens",
		indexes = @Index(name = "idx_respostas_mensagens_mensagem_id", columnList = "mensagem_id"),
		uniqueConstraints = @UniqueConstraint(name = "uk_respostas_mensagens_mensagem_id_resposta_mensagem_id", columnNames = {"mensagem_id","resposta_mensagem_id"}),
        joinColumns=@JoinColumn(name="mensagem_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_respostas_mensagens_mensagem")),
        inverseJoinColumns=@JoinColumn(name="resposta_mensagem_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_respostas_mensagens_resposta"))
	)
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Mensagem respostaMensagem;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "publicacao_usuario_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_mensagem_publicacao_usuario"))
	private PublicacaoUsuario publicacaoUsuario;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "publicacao_cronologia_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_mensagem_publicacao_cronologia"))
	private PublicacaoCronologia publicacaoCronologia;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "publicacao_pagina_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_mensagem_publicacao_pagina"))
	private PublicacaoPagina publicacaoPagina;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "publicacao_grupo_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_mensagem_publicacao_grupo"))
	private PublicacaoGrupo publicacaoGrupo;
}
