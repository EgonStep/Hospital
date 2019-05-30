package br.com.opet.hospital.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import br.com.opet.hospital.conexao.Conexao;
import br.com.opet.hospital.model.Administrativo;
import br.com.opet.hospital.model.Pessoa;

public class AdministrativoDAO extends Pessoa {
	
	private final String INSERT_ADM = "insert into ADMINISTRATIVO (IDADM, IDPESSOA, SALARIO) "
			+ "values (ADMINISTRATIVO_SEQ.NEXTVAL,?,?)";
	private final String UPDATE = "update ADMINISTRATIVO set SALARIO = ? where IDPESSOA = ?";
	private final String SELECT_ALL = "select p.id, p.cpf, p.nome, p.nascimento, p.rg, p.email, a.salario "
			+ "from PESSOA p, ADMINISTRATIVO a where a.IDADM = p.ID";
	private final String SELECT = "select p.id, p.cpf, p.nome, p.nascimento, p.rg, p.email, a.salario "
			+ "from PESSOA p, ADMINISTRATIVO a where a.IDADM = p.ID where a.IDADM = ?";
	private final String DELETE = "delete from ADMINISTRATIVO where IDPESSOA = ?";

	public AdministrativoDAO() {
	}

	public AdministrativoDAO(String nome, String cpf, Date nascimento, String rg, String email) {
		super(nome, cpf, nascimento, rg, email);
	}

	public boolean cadastrar(Administrativo administrativo) {
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		
		try {
			conn.setAutoCommit(false);
			
			stmt = conn.prepareStatement(INSERT_ADM);
			stmt.setInt(1, super.cadastrar(conn));
			stmt.setDouble(2, administrativo.getSalario());

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

	public ArrayList<Administrativo> listarAdministrativos() {
		ArrayList<Administrativo> administrativos = new ArrayList<Administrativo>();

		Connection con = Conexao.getConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement("select p.cpf, p.nome, p.nascimento, p.rg, p.email, d.SALARIO from PESSOA p "
					+ "join ADMINISTRATIVO d on p.CPF = d.CPFPESSOA");
			rs = stmt.executeQuery();

			while (rs.next()) {
				Administrativo adm = new Administrativo(rs.getString("nome"), rs.getString("cpf"),
						rs.getDate("nascimento"), rs.getString("rg"), rs.getString("email"), rs.getInt("salario"));

				administrativos.add(adm);
			}
		} catch (SQLException e) {
			return administrativos;
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("Erro ao encerrar conexao com o Banco!");
			}
		}
		return administrativos;
	}

	public boolean consultarAdministrativo(String cpf) {
		Connection con = Conexao.getConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement(SELECT);
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

	public boolean deletarAdministrativo(String cpf) {
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(DELETE);
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
