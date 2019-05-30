package br.com.opet.hospital.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.opet.hospital.model.dao.AdministrativoDAO;

@ManagedBean
@RequestScoped
public class Administrativo extends AdministrativoDAO {

	private double salario;
	private int tipo = 3;
	SimpleDateFormat dat = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public int getTipo() {
		return tipo;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public Administrativo(String nome, String cpf, Date nascimento, String rg, String email, double salario) {
		super(nome, cpf, nascimento, rg, email);
		this.salario = salario;
	}

	public Administrativo() {

	}

	@Override
	public String toString() {
		return "Administrativo [salario=" + salario + ", nome=" + nome + ", cpf=" + cpf + ", nascimento="
				+ dat.format(nascimento) + ", rg=" + rg + ", email=" + email + "]";
	}
	
	public boolean cadastrar() {
		return super.cadastrar(this);
	}

}
