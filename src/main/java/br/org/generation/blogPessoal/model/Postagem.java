package br.org.generation.blogPessoal.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity 
@Table(name = "tb_postagem")
public class Postagem {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 

	@NotNull (message = "O Atributo título é obrigatório")
	@Size(min = 5, max = 100)
	private String titulo; 
	
	@NotNull
	@Size(min = 10, max = 2000)
	private String texto; 
	
	@Temporal(TemporalType.TIMESTAMP) 
	private Date date = new java.sql.Date(System.currentTimeMillis());

	@ManyToOne //  manyToOne muitos para um - varias postagem tem um tema
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne 
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	
	private int curtidas;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(int curtidas) {
		this.curtidas = curtidas;
	}

}
