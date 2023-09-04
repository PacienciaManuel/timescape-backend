package com.timescape.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
@DiscriminatorValue("Usuário")
@EqualsAndHashCode(callSuper = true)
@JsonClassDescription("mensagemUsuario")
@JsonRootName(value = "mensagemUsuario", namespace = "mensagensUsuarios")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_mensagem_usuario_mensagem"))
@JsonPropertyOrder({"id","texto","tipo","dataMensagem","visto","dataVisto","arquivos","usuarioOrigem","usuarioDestino","reacoes"})
@Table(
	name = "mensagens_usuarios", 
	indexes = { 
		@Index(name = "idx_mensagens_usuarios_usuario_origem_id", columnList = "usuario_origem_id"),
		@Index(name = "idx_mensagens_usuarios_usuario_destino_id", columnList = "usuario_destino_id")
	}
)
public class MensagemUsuario extends Mensagem {

	@ColumnDefault("false")
	@Column(nullable = false)
	private Boolean visto;
	
	@Column(name = "data_visto")
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	private LocalDateTime dataVisto;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_origem_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_mensagem_usuario_usuario_origem"))
	private Usuario usuarioOrigem;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_destino_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_mensagem_usuario_usuario_destino"))
	private Usuario usuarioDestino;

}
