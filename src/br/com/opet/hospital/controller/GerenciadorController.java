package br.com.opet.hospital.controller;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.opet.hospital.model.Especialidade;
import br.com.opet.hospital.model.Pessoa;

@ManagedBean
@SessionScoped
public class GerenciadorController {
	
	public String cadastrarEspecialidade(Especialidade especialidade) {
		return (especialidade.cadastrar()) ? "/index.xhtml" : "Falha";
	}
	
	public ArrayList<Especialidade> listarEspecialidades() {
		Especialidade especialidade = new Especialidade();
		return especialidade.listar();
	}
	
	public String cadastrarPessoa(Pessoa pessoa) {
		pessoa.cadastrar();
		return "/index.xhtml";
	}
}
