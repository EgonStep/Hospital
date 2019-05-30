package br.com.opet.hospital.model;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.opet.hospital.model.dao.EspecialidadeDAO;

@ManagedBean
@RequestScoped
public class Especialidade extends EspecialidadeDAO{
	
	private int id;
	private String descricao;
	
	public Especialidade() { }
	
	public Especialidade(int id) {
		this.id = id;
	}
	
	public Especialidade(int id, String descricao) {
		this(id);
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean cadastrar() {
		return super.cadastrar(this);
	}
	
	public boolean deletarEspecialidade() {
		return super.deletarEspecialidade(this.id);
	}

	public boolean consultar() {
		return super.consultar(this.id);	
	}
	
	public ArrayList<Especialidade> listar() {
		return super.listar();
	}
	
	/*public boolean recuperarEspecialidade() {
		Especialidade esp = super.recuperarEspecialidade(this.id);
		if (esp == null)
			return false;
		this.id = esp.getId();
		this.descricao = esp.getDescricao();
		
		return true;
	}*/
}
