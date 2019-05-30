package br.com.opet.hospital.controller;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.opet.hospital.model.Administrativo;
import br.com.opet.hospital.model.Enfermeiro;
import br.com.opet.hospital.model.Especialidade;
import br.com.opet.hospital.model.Medico;
import br.com.opet.hospital.model.Pessoa;

@ManagedBean
@SessionScoped
public class GerenciadorController {
	
	public String cadastrarEspecialidade(Especialidade especialidade) {
		especialidade.cadastrar();
		return "/index.xhtml";
	}
	
	public ArrayList<Especialidade> listarEspecialidades() {
		Especialidade especialidade = new Especialidade();
		return especialidade.listar();
	}
	
	public String cadastrarPessoa(Pessoa pessoa) {
		pessoa.cadastrar();
		return "/index.xhtml";
	}
	
	public ArrayList<Pessoa> listarPessoas() {
		Medico medico = new Medico();
		Enfermeiro enfermeiro = new Enfermeiro();
		Administrativo administrativo = new Administrativo();
		ArrayList<Pessoa> pessoas = medico.listar();
		pessoas.addAll(enfermeiro.listar());
		pessoas.addAll(administrativo.listar());
		return pessoas;
	}
}
