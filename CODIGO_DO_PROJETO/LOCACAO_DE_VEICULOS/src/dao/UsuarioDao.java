package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexao.Conexao;
import modelo.Cliente;
import modelo.Usuario;

public class UsuarioDao extends Conexao {
	
	//incluir / testado
	public boolean incluir(Usuario u) throws SQLException{
		
		boolean retorno = false;
		int intRetorno = 0;
		
		String INSERT = "INSERT INTO " + "\"USUARIO\"" + " (nome,senha,tipo) VALUES (?,?,?);";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(INSERT);
			 prepared.setString(1,u.getNome());
			 prepared.setString(2,u.getSenha());
			 prepared.setString(3,u.getTipo());
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
	//alterar / testado
	public boolean alterar(Usuario u)throws SQLException  {
		
		boolean retorno = false;
		int intRetorno = 0;
		
		String UPDATE = "UPDATE \"USUARIO\" SET nome = ? , senha = ? , tipo = ?  WHERE \"id_usuario\" = ?;";
		
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(UPDATE);
			 prepared.setString(1,u.getNome());
			 prepared.setString(2,u.getSenha());
			 prepared.setString(3,u.getTipo());
			 prepared.setInt(4,u.getId());
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
	//excluir
	
	public boolean excluir(Usuario u)throws SQLException {
		
		boolean retorno = false;
		int intRetorno = 0;
		
		String DELETE = "DELETE FROM " + "\"USUARIO\"" + " WHERE \"id_usuario\" = ?;";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(DELETE);
			 prepared.setInt(1,u.getId());
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
	
	//Consultar todos
	public ArrayList<Usuario> consultarTodos()throws SQLException{
			
			Connection conn;
			Usuario user;
			
			ArrayList<Usuario> userList = new ArrayList<Usuario>();
			
			conn = this.getConnection();
			ResultSet rs = null;
			Statement st = conn.createStatement();
			
			String sql = "SELECT * FROM \"USUARIO\" ORDER BY nome;";
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				user = new Usuario();
				user.setId(rs.getInt("id_usuario"));
				user.setNome(rs.getString("nome"));
				user.setSenha(rs.getString("senha"));
				user.setTipo(rs.getString("tipo"));
				userList.add(user);
			}
			
			st.close();
			rs.close();
			conn.close();
		
			return userList;
		}

	//Consultar por nome / testado
	public ArrayList<Usuario> consultarNome(String nome)throws SQLException{
			
			Connection conn;
			Usuario user;
			
			ArrayList<Usuario> listUser = new ArrayList<Usuario>();
			
			conn = this.getConnection();
			ResultSet rs = null;
			Statement st = (Statement) conn.createStatement();
			
			String sql = "SELECT * FROM \"USUARIO\" WHERE nome like '%" +nome+ "%';";
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				user = new Usuario();
				user.setId(rs.getInt("id_usuario"));
				user.setNome((rs.getString("nome")));
				user.setSenha(rs.getString("senha"));
				user.setTipo(rs.getString("tipo"));
				listUser.add(user);
			}
			
			st.close();
			rs.close();
			conn.close();
			
			return listUser;
		}
	
	//Consultar por nome e senha / testado
	public Usuario consultarNomeSenha(String nome, String senha)throws SQLException{
		
		Connection conn;
		
		Usuario user = new Usuario();
		
		conn = this.getConnection();
		ResultSet rs = null;
		Statement st = (Statement) conn.createStatement();
		
		String sql = "SELECT * FROM \"USUARIO\" WHERE nome like '" + nome + "'AND senha like '" + senha + "';";
		rs = st.executeQuery(sql);
		
		if(rs.next()){
			user.setId(rs.getInt("id_usuario"));
			user.setNome((rs.getString("nome")));
			user.setSenha(rs.getString("senha"));
			user.setTipo(rs.getString("tipo"));
		}
		else{
			user = null;
		}
		
		st.close();
		rs.close();
		conn.close();
		
		return user;
	}
}
