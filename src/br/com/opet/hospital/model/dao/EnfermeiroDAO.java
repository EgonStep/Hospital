package br.com.opet.hospital.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import br.com.opet.hospital.conexao.Conexao;
import br.com.opet.hospital.model.Enfermeiro;
import br.com.opet.hospital.model.Pessoa;

public class EnfermeiroDAO extends Pessoa{
	
	private final String INSERT_ENF = "insert into ENFERMEIRO (IDENF, IDPESSOA, CARGAHORARIA) "
			+ "values (ENFERMEIRO_SEQ.NEXTVAL,?,?)";
	private final String UPDATE = "update ENFERMEIRO set CARGAHORARIA = ? where IDPESSOA = ?";
	private final String SELECT_ALL = "select p.id, p.cpf, p.nome, p.nascimento, p.rg, p.email, e.cargahoraria "
			+ "from PESSOA p, ENFERMEIRO e where e.IDENF = p.ID";
	private final String SELECT = "select p.id, p.cpf, p.nome, p.nascimento, p.rg, p.email, e.cargahoraria "
			+ "from PESSOA p, ENFERMEIRO e where a.IDADM = p.ID where e.IDENF = ?";
	private final String DELETE = "delete from ENFERMEIRO where IDPESSOA = ?";	

	public EnfermeiroDAO() {
	}

	public EnfermeiroDAO(String nome, String cpf, Date nascimento, String rg, String email) {
		super(nome, cpf, nascimento, rg, email);
		
	}

	public boolean cadastrar(Enfermeiro enfermeiro) {
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;

		try {
			conn.setAutoCommit(false);
			
			stmt = conn.prepareStatement(INSERT_ENF);
			stmt.setInt(1, super.cadastrar(conn));
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
