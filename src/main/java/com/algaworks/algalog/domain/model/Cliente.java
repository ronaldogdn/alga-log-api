package com.algaworks.algalog.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.algaworks.algalog.domain.ValidationGroups;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
/**
 * Caso o nome da tabela fosse outro dava pra fazer assim...
 */
//@Table(name = "cliente")
public class Cliente {
	@NotNull(groups = ValidationGroups.ClienteId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//identity é a forma nativa do BD
	private Long id;
	//caso o nome da coluna fosse outro...
	//@Column(name = "nome")
	@NotBlank
	@Size(max = 60)
	private String nome;
	@NotBlank
	@Email
	@Size(max = 255)
	private String email;
	@Size(max = 20)
	private String telefone;
	
}
