package br.com.opet.hospital.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.opet.hospital.conexao.Conexao;
import br.com.opet.hospital.model.Enfermeiro;
import br.com.opet.hospital.model.Pessoa;

public class EnfermeiroDAO {

	public boolean cadastrarEnfermeiro(Pessoa pessoa) {
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		Enfermeiro enfermeiro = (Enfermeiro) pessoa;

		try {
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement("insert into enfermeiro (CPFPESSOA, CARGAHORARIA) " + "VALUES(?,?)");

			stmt.setInt(1, Integer.parseInt(pessoa.getCpf()));
			stmt.setInt(2, enfermeiro.getCargaHoraria());

			int linhasAtualizadas = stmt.executeUpdate();
			if (linhasAtualizadas == 1) {
				conn.commit();
			} else {
				conn.rollback();
				return false;
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Enfermeiro não salvo");
				return false;
			}
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("Enfermeiro não salvo");
				return false;
			}
		}
		return true;
	}

	public ArrayList<Enfermeiro> listarEnfermeiros() {
		ArrayList<Enfermeiro> enfermeiros = new ArrayList<Enfermeiro>();

		Connection con = Conexao.getConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con
					.prepareStatement("select p.cpf, p.nome, p.nascimento, p.rg, p.email, e.CARGAHORARIA from PESSOA p "
							+ "join ENFERMEIRO e on p.CPF = e.CPFPESSOA");
			rs = stmt.executeQuery();

			while (rs.next()) {
				Enfermeiro enfermeiro = new Enfermeiro(rs.getString("nome"), rs.getString("cpf"),
						rs.getDate("nascimento"), rs.getString("rg"), rs.getString("email"), rs.getInt("cargahoraria"));

				enfermeiros.add(enfermeiro);
			}
		} catch (SQLException e) {
			return enfermeiros;
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("Erro ao encerrar conexao com o Banco!");
			}
		}
		return enfermeiros;
	}

	public boolean consultarEnfermeiro(String cpf) {
		Connection con = Conexao.getConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con
					.prepareStatement("select p.cpf, p.nome, p.nascimento, p.rg, p.email, e.CARGAHORARIA from PESSOA p "
							+ "join ENFERMEIRO e on p.CPF = e.CPFPESSOA where p.CPF = ?");
			stmt.setString(1, cpf);
			rs = stmt.executeQuery();

			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			return false;
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("Erro ao encerrar conexao com o Banco!");
			}
		}
		return false;
	}

	public boolean deletarEnfermeiro(String cpf) {
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement("delete from ENFERMEIRO where CPFPESSOA = ?");
			stmt.setString(1, cpf);

			if (stmt.executeUpdate() == 1) {
				conn.commit();
			} else {
				conn.rollback();
				return false;
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				return false;
			}
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				return false;
			}
		}
		return true;
	}
}
