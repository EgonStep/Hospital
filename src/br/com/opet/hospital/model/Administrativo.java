package br.com.opet.hospital.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Administrativo extends Pessoa {

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

	public void setSalario(int salario) {
		this.salario = salario;
	}

	public Administrativo(String nome, String cpf, Date nascimento, String rg, String email, double salario) {
		super(nome, cpf, nascimento, rg, email);
		this.salario = salario;
	}

	public Administrativo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Administrativo [salario=" + salario + ", nome=" + nome + ", cpf=" + cpf + ", nascimento="
				+ dat.format(nascimento) + ", rg=" + rg + ", email=" + email + "]";
	}

}
