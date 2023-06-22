package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexao.Conexao;
import modelo.Cliente;

public class ClienteDao extends Conexao {

	//testado
	public boolean incluir(Cliente cli) throws SQLException{
			
		boolean retorno = false;
		int intRetorno = 0;
		
		String INSERT = "INSERT INTO " + "\"CLIENTE\"" + " (nome_cli,cnh_cli,cpf_cli) VALUES (?,?,?);";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(INSERT);
			 prepared.setString(1,cli.getNomeCli());
			 prepared.setString(2, cli.getCnh());
			 prepared.setString(3, cli.getCpf());
			 intRetorno = prepared.executeUpdate(); 
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		if(intRetorno > 0){
			retorno = true;
		}
		return retorno;
	}
	
	//testado
	public boolean alterar(Cliente cli) throws SQLException{
		boolean retorno = false;
		int intRetorno = 0;
		
		String UPDATE = "UPDATE \"CLIENTE\" SET nome_cli = ? , cnh_cli = ? , cpf_cli = ? WHERE \"id_cli\" = ?";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(UPDATE);
			 prepared.setString(1,cli.getNomeCli());
			 prepared.setString(2,cli.getCnh());
			 prepared.setString(3, cli.getCpf());
			 prepared.setInt(4, cli.getId_cli());
			 intRetorno = prepared.executeUpdate(); 
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		if(intRetorno > 0){
			retorno = true;
		}
		return retorno;
	}
	
	//testado
	public boolean excluir(Cliente cli)throws SQLException {
		
		boolean retorno = false;
		int intRetorno = 0;
		
		String DELETE = "DELETE FROM " + "\"CLIENTE\"" + " WHERE \"id_cli\" = ? ;";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(DELETE);
			 prepared.setInt(1,cli.getId_cli());
			 intRetorno = prepared.executeUpdate(); 
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		if(intRetorno > 0){
			retorno = true;
		}
		return retorno;
	}
	
	//Consulta simples pelo nome / testado
	public ArrayList<Cliente> consultar(String nome) throws SQLException {
		
		Connection conn;
		Cliente cli;
		
		ArrayList<Cliente> clienteList = new ArrayList<Cliente>();
		
		conn= this.getConnection();
		ResultSet rs = null;
		Statement st = conn.createStatement();
		
		String sql = "SELECT * FROM \"CLIENTE\" WHERE nome_cli like '%" + nome + "%';";
		rs = st.executeQuery(sql);
		
		while(rs.next()){
			cli = new Cliente();
			cli.setNomeCli(rs.getString("nome_cli"));
			cli.setCpf(rs.getString("cpf_cli"));
			cli.setCnh(rs.getString("cnh_cli"));
			cli.setId_cli(rs.getInt("id_cli"));
			clienteList.add(cli);
		}
		st.close();
		rs.close();
		conn.close();
	
		return clienteList;
		
	}
	
}
