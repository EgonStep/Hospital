package br.com.opet.hospital.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	public static Connection getConexao() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "DBhospital", "SYSTEM");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Falha ao conectar com o Banco!");
		}
		return conn;
	}
}
