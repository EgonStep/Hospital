package br.com.opet.hospital.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.opet.hospital.conexao.Conexao;
import br.com.opet.hospital.model.Especialidade;

public class EspecialidadeDAO {
	
	private final String INSERT = "insert into ESPECIALIDADE (IDESPECIALIDADE, DESCRICAO) "
			+ "values (ESPECIALIDADE_SEQ.NEXTVAL,?)";
	private final String UPDATE = "update ESPECIALIDADE set DESCRICAO = ? where IDESPECIALIDADE = ?";
	private final String SELECT = "select IDESPECIALIDADE, DESCRICAO from ESPECIALIDADE order by IDESPECIALIDADE";
	private final String DELETE = "delete from ESPECIALIDADE where IDESPECIALIDADE = ?";

	protected boolean cadastrar(Especialidade esp) {
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		
		try {
			conn.setAutoCommit(false);
			
			stmt = conn.prepareStatement(INSERT);
			stmt.setString(1, esp.getDescricao());
			
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
				System.out.println("Especialidade não salva");
				return false;
			}
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("Especialidade não salva");
				return false;
			}
		}
		return true;
	}

	public ArrayList<Especialidade> listar() {
		ArrayList<Especialidade> especialidades = new ArrayList<>();
		Connection con = Conexao.getConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement(SELECT);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Especialidade esp = new Especialidade(rs.getInt("IDESPECIALIDADE"), rs.getString("DESCRICAO"));

				especialidades.add(esp);
			}
		} catch (SQLException e) {
			System.out.println("Nao foi possivel recuperar lista de especialidades!");
			return null;
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("Erro ao encerrar conexao com o Banco!");
				return null;
			}
		}
		return especialidades;
	}

	public boolean deletarEspecialidade(int idEspecialidade) {
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(DELETE);
			stmt.setInt(1, idEspecialidade);

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
				System.out.println("Especialidade não deletada");
				return false;
			}
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("Especialidade não deletada");
				return false;
			}
		}
		System.out.println("Especialidade deletada com sucesso!");
		return true;
	}

	public boolean atualizarEspecialidade(int idEspecialidade, String descricao) {
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(UPDATE);
			stmt.setString(1, descricao);
			stmt.setInt(2, idEspecialidade);

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
				System.out.println("Especialidade não atualizada");
				return false;
			}
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("Especialidade não atualizada");
				return false;
			}
		}
		System.out.println("Especialidade atualizada com sucesso!");
		return true;
	}

	public boolean consultar(int id) {
		// TODO Auto-generated method stub
		return false;
	}
}
