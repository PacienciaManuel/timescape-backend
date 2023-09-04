package com.timescape.model;

import java.time.LocalDateTime;
import java.util.List;
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
@JsonClassDescription("resposta")
@JsonRootName(value = "resposta", namespace = "respostas")
@JsonPropertyOrder({"id","texto","dataResposta","arquivo","totalReacoes","usuario","denuncias"})
@Table(name = "respostas", indexes = @Index(name = "idx_respostas_comentario_id", columnList = "comentario_id"))
public class Resposta {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(length = Length.LONG32)
	private String texto;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_resposta", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataResposta;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "arquivo_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_resposta_arquivo"))
	private Arquivo arquivo;
	
	@Formula("(SELECT COUNT(rr.resposta_id) FROM respostas r LEFT JOIN reacoes_respostas rr ON (rr.resposta_id=r.id) WHERE r.id=id GROUP BY r.id)")
	private Long totalReacoes;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "comentario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_resposta_comentario"))
	private Comentario comentario;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_resposta_usuario"))
	private Usuario usuario;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "resposta", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ReacaoResposta> reacoes;

	@JoinTable(
		name="denucias_respostas",
		indexes = {
				@Index(name = "idx_denucias_respostas_resposta_id", columnList = "resposta_id"),
				@Index(name = "idx_denucias_respostas_denuncia_id", columnList = "denuncia_id")
		},
        joinColumns=@JoinColumn(name="resposta_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_denucias_respostas_resposta")),
        inverseJoinColumns=@JoinColumn(name="denuncia_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_denucias_respostas_denuncia"))
	)
	@OrderBy("dataDenuncia DESC")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Denuncia> denuncias;

}
