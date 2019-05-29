package br.com.opet.hospital.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.opet.hospital.model.dao.PessoaDAO;

@ManagedBean
@RequestScoped
public class Pessoa extends PessoaDAO {

	protected int id;
	protected String nome;
	protected String cpf;
	protected Date nascimento;
	protected String rg;
	protected String email;
	protected int tipo = 0;

	public int getTipo() {
		return tipo;
	}
	
	public Pessoa() { }

	public Pessoa(String nome, String cpf, Date nascimento, String rg, String email) {
		this.nome = nome;
		this.cpf = cpf;
		this.nascimento = nascimento;
		this.rg = rg;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean cadastrar() {
		return false;
	}
	
	protected int cadastrar(Connection conn) {
		id = super.cadastrar(conn, this);
		return (id == -1) ? -1 : id;
	}
	
	public Pessoa recuperar() {
		Pessoa pessoa = super.recuperar(this.cpf);
		pessoa.complementar();
		return pessoa;
	}
	
	public boolean complementar() {
		System.err.println("Erro acesso invalido!");
		return false;
	}
	
	public boolean excluir() {
		return false;
	}
	
	public boolean excluir(Connection conn)  throws SQLException {
		return super.excluir(conn, this.cpf);
	}
	
	// TODO fazer atualizar e listar todas os valores
	
	public boolean atualizar() {
		return false;
	}
	
	public boolean atualizar(Connection conn) {
		return super.atualizar(conn, this);
	}
	
	public ArrayList<Pessoa> listar(){
		return null;
	}
}
