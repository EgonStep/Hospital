package br.com.opet.hospital.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import br.com.opet.hospital.conexao.Conexao;
import br.com.opet.hospital.model.Especialidade;
import br.com.opet.hospital.model.Medico;
import br.com.opet.hospital.model.Pessoa;

public class MedicoDAO extends Pessoa {

	private final String INSERT_MEDICO = "insert into MEDICO (IDMED, IDPESSOA, IDESPECIALIDADE) "
			+ "values (MEDICO_SEQ.NEXTVAL,?,?)";
	private final String UPDATE = "update MEDICO set IDESPECIALIDADE = ? where IDPESSOA = ?";
	private final String SELECT_ALL = "select p.id, p.cpf, p.nome, p.nascimento, p.rg, p.email, e.idespecialidade, e.descricao "
			+ "from PESSOA p, MEDICO m, ESPECIALIDADE e where m.IDMED = p.ID and m.IDESPECIALIDADE = e.IDESPECIALIDADE";
	private final String SELECT = "select e.idespecialidade, e.descricao "
			+ "from medico m inner join especialidades e on e.idespecialidade = m.idespecialidade where m.IDMED = ?";
	private final String DELETE = "delete from MEDICO where IDPESSOA = ?";
	
	public MedicoDAO() {	}
	
	public MedicoDAO(String nome, String cpf, Date nascimento, String rg, String email) {
		super(nome, cpf, nascimento, rg, email);
	}

	public boolean cadastrar(Medico medico) {
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		
		try {
			conn.setAutoCommit(false);
			
			stmt = conn.prepareStatement(INSERT_MEDICO);
			stmt.setInt(1, super.cadastrar(conn));
			stmt.setInt(2, medico.getEspecialidade().getId());

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
	
	protected Medico recuperar(String cpf) {
		Connection conn = Conexao.getConexao();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Medico medicoTMP = new Medico();
		
		try {
			ps = conn.prepareStatement(SELECT);
			ps.setString(1, cpf);
			
			rs = ps.executeQuery();
			
			boolean isFilled = false;
			while (rs.next()) {
				Especialidade esp = new Especialidade(rs.getInt("idespecialidade"), rs.getString("descricao"));
				medicoTMP.setEspecialidade(esp);
				isFilled = true;
			}
			if(!isFilled) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return medicoTMP;	
	}

	public ArrayList<Pessoa> listar() {
		ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();

		Connection con = Conexao.getConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement(SELECT_ALL);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Especialidade especialidade = new Especialidade(rs.getInt("idespecialidade"), rs.getString("descricao"));
				Medico medicoTMP = new Medico(rs.getString("nome"), rs.getString("cpf"), 
						rs.getDate("nascimento"), rs.getString("rg"), rs.getString("email"), especialidade);
				
				pessoas.add(medicoTMP);
			}
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				return null;
			}
		}
		return pessoas;
	}

	public boolean excluir(String cpf) {
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(DELETE);
			stmt.setString(1, cpf);

			if (stmt.executeUpdate() == 1) {
				try {
					super.excluir(conn);
				} catch (Exception e) {
					return false;
				}
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

	public boolean atualizar(Medico medicoTMP) {
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(UPDATE);
			stmt.setInt(1, medicoTMP.getEspecialidade().getId());
			stmt.setString(2, medicoTMP.getCpf());

			if (stmt.executeUpdate() == 1) {
				super.atualizar(conn);
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
