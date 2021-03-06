package org.generation.blogPessoal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotBlank(message = "O atributo nome é obrigatório!")
	@Size(min = 2, max = 100, message = "O atributo nome tem de ter no mínimo 02" + " e no máximo 100 caracteres.")
	private String nome;

	@ApiModelProperty(example = "email@email.com.br")
	@NotBlank(message = "O atributo usuário é obrigatório!")
	@Size(min = 3, max = 100, message = "O atributo usuario tem de ter no mínimo 05" + " e no máximo 100 caracteres.")
	@Email(message = "O atributo usuário deve ser um email!")
	private String usuario;

	@NotBlank(message = "O atributo senha é obrigatório!")
	@Size(min = 5, max = 100, message = "O atributo senha tem de ter no mínimo 05" + " e no máximo 100 caracteres.")
	private String senha;

	@Size(min = 5, max = 200, message = "O atributo foto recebe link onde uma imagem em um banco de dados existe.")
	private String foto;
	
	@Size(min = 5, max = 100, message = "O atributo tipo tem de ter no mínimo 05" + " e no máximo 100 caracteres.")
	private String tipo;

	@Column(name = "dt_nascimento")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;

	@OneToMany(mappedBy = "criador", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({ "criador" })
	private List<Postagem> minhasPostagens = new ArrayList<>();

	public Usuario(long id,String nome,String usuario,String senha, String foto, String tipo,LocalDate dataNascimento) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.foto = foto;
		this.tipo = tipo;
		this.dataNascimento = dataNascimento;
	}

	public Usuario() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Postagem> getMinhasPostagens() {
		return minhasPostagens;
	}

	public void setMinhasPostagens(List<Postagem> minhasPostagens) {
		this.minhasPostagens = minhasPostagens;
	}
}