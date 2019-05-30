package br.com.opet.hospital.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.opet.hospital.conexao.Conexao;
import br.com.opet.hospital.model.Administrativo;
import br.com.opet.hospital.model.Enfermeiro;
import br.com.opet.hospital.model.Especialidade;
import br.com.opet.hospital.model.Medico;
import br.com.opet.hospital.model.Pessoa;

public class PessoaDAO {
	
	protected final String INSERT_PESSOA = "insert into PESSOA (ID, CPF, NOME, NASCIMENTO, RG, EMAIL) "
			+ "values (PESSOA_SEQ.NEXTVAL,?,?,?,?,?)";
	protected final String SELECT_LEFT_JOIN = "select P.ID, P.CPF, P.NOME, P.NASCIMENTO, P.RG, P.EMAIL, M.IDMED as med, E.IDENF as enf, A.IDADM as adm "
			+ "from PESSOA P "
			+ "left join MEDICO M on P.ID = M.IDPESSOA "
			+ "left join ENFERMEIRO E on P.ID = E.IDPESSOA "
			+ "left join ADMINISTRATIVO A on P.ID = A.IDPESSOA where P.ID = ?";
	protected final String SELECT_ALL = "select P.ID, P.CPF, P.NOME, P.NASCIMENTO, P.RG, P.EMAIL, ESP.DESCRICAO, E.CARGAHORARIA, A.SALARIO " + 
			"from PESSOA P " + 
			"left join MEDICO M on P.ID = M.IDPESSOA " + 
			"left join ESPECIALIDADE ESP on M.IDESPECIALIDADE = ESP.IDESPECIALIDADE " + 
			"left join ENFERMEIRO E on P.ID = E.IDPESSOA " + 
			"left join ADMINISTRATIVO A on P.ID = A.IDPESSOA";
	protected final String DELETE = "delete from PESSOA where ID = ?";

	public PessoaDAO() { }
	
public int cadastrar(Connection conn, Pessoa pessoa) {
		
		String generatedColumn[] = {"ID"};
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(INSERT_PESSOA, generatedColumn);
			stmt.setInt(1, Integer.parseInt(pessoa.getCpf()));
			stmt.setString(2, pessoa.getNome());
			stmt.setDate(3, new Date(pessoa.getNascimento().getTime()));
			stmt.setString(4, pessoa.getRg());
			stmt.setString(5, pessoa.getEmail());
			
			int linhasAtualizadas = stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
		
			while(rs.next()) {
				int idPessoa = rs.getInt(1);
				pessoa.setId(idPessoa);
			}
			rs.close();
			
			if (linhasAtualizadas == 1) {
				conn.commit();
			} else {
				conn.rollback();
				return -1;
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				return -1;
			}
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				return -1;
			}
		}
		return pessoa.getId();
	}
	
	protected Pessoa recuperar(String CPF) {
		Pessoa pessoa = null;
		
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(SELECT_LEFT_JOIN);
			rs = stmt.executeQuery();
			stmt.setString(1, CPF);

			boolean isFilled = false;
			
			while (rs.next()) {
				
				String tipo = rs.getString("med");
				if(tipo != null && tipo.isEmpty())
					pessoa = new Medico();
				
				tipo = rs.getString("enf");
				if(tipo != null && !tipo.isEmpty())
					pessoa = new Enfermeiro();
				
				tipo = rs.getString("adm");
				if(tipo != null && !tipo.isEmpty())
					pessoa = new Administrativo();
				
				// TODO Finalizar nas classes especializadas e a retirada da pessoa do banco
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setNome(rs.getString("nome"));
				
				isFilled = true;
			}
			if(!isFilled) {
				return null;
			}
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("Erro ao encerrar conexao com o Banco!");
			}
		}
		return pessoa;
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
				if(rs.getString("descricao") != null) {
					Especialidade especialidade = new Especialidade(rs.getInt("idespecialidade"), rs.getString("descricao"));
					Medico medico = new Medico(rs.getString("nome"), rs.getString("cpf"), rs.getDate("nascimento"), 
							rs.getString("rg"), rs.getString("email"), especialidade);
					pessoas.add(medico);
				} else if (rs.getDouble("salario") != 0) {
					Administrativo administrativo = new Administrativo(rs.getString("nome"), rs.getString("cpf"), rs.getDate("nascimento"), 
							rs.getString("rg"), rs.getString("email"), rs.getDouble("salario"));
					pessoas.add(administrativo);
				} else if (rs.getInt("cargaHoraria") != 0) {
					Enfermeiro enfermeiro = new Enfermeiro(rs.getString("nome"), rs.getString("cpf"), rs.getDate("nascimento"), 
							rs.getString("rg"), rs.getString("email"), rs.getInt("cargaHoraria"));
					pessoas.add(enfermeiro);
				}
			}
		} catch (SQLException e) {
			System.out.println("Nao foi possivel recuperar lista de pessoas!");
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("Erro ao encerrar conexao com o Banco!");
			}
		}
		return pessoas;
	}

	public boolean excluir(Connection conn, String cpf) {
		conn = Conexao.getConexao();
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(DELETE);
			stmt.setString(1, cpf);

			if (stmt.executeUpdate() == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				return false;
			}
		}
	}

	public boolean atualizar(Connection conn, Pessoa pessoa) {
		// TODO Auto-generated method stub
		return false;
	}
}
