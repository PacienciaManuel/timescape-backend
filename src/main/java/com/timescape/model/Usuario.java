package com.timescape.model;

import java.time.LocalDate;

import org.hibernate.Length;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.GeneroConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Where(clause = "bloqueado = false")
@JsonRootName(value = "usuario", namespace = "usuarios")
@JsonPropertyOrder({"id","nome","genero","dataNascimento","fotoPerfil","senha"})
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(name = "uk_usuario_email", columnNames = "email"))
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false)
	@NotBlank(message = "{Usuario.nome.notblank}")
	@Size(max = 50, message = "{Usuario.noma.size}")
	private String nome;

	@Column(length = 50)
	@Size(max = 50, message = "{Usuario.apelido.size}")
	private String apelido;

	@Column(length = 10, nullable = false)
	@Convert(converter = GeneroConverter.class)
	private Genero genero;

	@JsonFormat(shape = Shape.STRING)
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Column(length = 50, nullable = false)
	@NotBlank(message = "{Usuario.email.notblank}")
	@Size(max = 50, message = "{Usuario.email.size}")
	@Email(message = "{Usuario.email.pattern}", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	private String email;
	
	@Column(length = Length.LONG32)
	private String fotoPerfil;
	
	@Column(length = Length.LONG32)
	private String fotoCapa;

	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "{Usuario.senha.notblank}")
	@Size(min = 8, message = "{Usuario.senha.size}")
	@Column(length = Length.LONG32, nullable = false)
	private String senha;

	@JsonGetter("dataNascimento")
	public String dataNascimentoStr() {
		return dataNascimento == null ? null : dataNascimento.toString();
	}
}
