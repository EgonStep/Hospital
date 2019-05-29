package br.com.opet.hospital.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Enfermeiro extends Pessoa {

	private int cargaHoraria;
	private int tipo = 2;
	SimpleDateFormat dat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public int getTipo() {
		return tipo;
	}

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Enfermeiro(String nome, String cpf, Date nascimento, String rg, String email, int cargaHoraria) {
		super(nome, cpf, nascimento, rg, email);
		this.cargaHoraria = cargaHoraria;
	}
	
	public Enfermeiro() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Enfermeiro [cargaHoraria=" + cargaHoraria + ", nome=" + nome + ", cpf=" + cpf
				+ ", nascimento=" + dat.format(nascimento) + ", rg=" + rg + ", email=" + email + "]";
	}	
}
