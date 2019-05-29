package br.com.opet.hospital.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.opet.hospital.model.dao.MedicoDAO;

@ManagedBean
@RequestScoped
public class Medico extends MedicoDAO {

	private Especialidade especialidade;
	private int tipo = 1;
	SimpleDateFormat dat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public int getTipo() {
		return tipo;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}
	
	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public Medico(String nome, String cpf, Date nascimento, String rg, String email, Especialidade especialidade) {
		super(nome, cpf, nascimento, rg, email);
		this.especialidade = especialidade;
	}
	
	public Medico() {
	}

	@Override
	public String toString() {
		return "Medico [especialidade=" + especialidade.getDescricao() + ", nome=" + nome + ", cpf=" + cpf
				+ ", nascimento=" + dat.format(nascimento) + ", rg=" + rg + ", email=" + email + "]";
	}
	
	public boolean cadastrar() {
		return super.cadastrar(this);
	}
	
	public boolean atualizar() {
		return super.atualizar(this);
	}
	
	public boolean excluir() {
		return super.excluir(this.cpf);
	}
	
	public boolean complementar() {
		Medico mTMP = super.recuperar(this.cpf);
		
		if(mTMP == null)
			return false;
		this.especialidade = mTMP.getEspecialidade();
		
		return true;
	}
	
	public ArrayList<Pessoa> listar(){
		return super.listar();
	}
}