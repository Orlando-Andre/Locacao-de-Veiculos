package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexao.Conexao;
import modelo.Marca;


public class MarcaDao extends Conexao {
	
	//testado
	public boolean incluir(Marca a) throws SQLException{
		
		boolean retorno = false;
		int intRetorno = 0;
		
		String INSERT = "INSERT INTO " + "\"MARCA\"" + " (nome_marca) VALUES (?);";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(INSERT);
			 prepared.setString(1,a.getNomeMarca());
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
	public boolean alterar(Marca a) throws SQLException{
		boolean retorno = false;
		int intRetorno = 0;
		
		String UPDATE = "UPDATE \"MARCA\" SET nome_marca = ? WHERE \"id_marca\" = ?";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(UPDATE);
			 prepared.setString(1,a.getNomeMarca());
			 prepared.setInt(2,a.getId_marca());
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
	public boolean excluir(Marca a)throws SQLException {
			
		boolean retorno = false;
		int intRetorno = 0;
		
		String DELETE = "DELETE FROM" + "\"MARCA\"" + " WHERE \"id_marca\" = ? ;";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(DELETE);
			 prepared.setLong(1,a.getId_marca());
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
		
	//consultar todas as marcas / testado
	public ArrayList<Marca> consultarTodos()throws SQLException{
			
		Connection conn;
		Marca a;
		
		ArrayList<Marca> marcaList = new ArrayList<Marca>();
		
		conn= this.getConnection();
		ResultSet rs = null;
		Statement st = conn.createStatement();
		
		String sql = "SELECT * FROM \"MARCA\" ORDER BY nome_marca;";
		rs = st.executeQuery(sql);
		
		while(rs.next()){
			a = new Marca();
			a.setNomeMarca(rs.getString("nome_marca"));
			a.setId_marca(rs.getInt("id_marca"));
			marcaList.add(a);
		}
		
		st.close();
		rs.close();
		conn.close();
	
		return marcaList;
	}
	
	//Consultar marcas por nome /testado
	public Marca consultarNome(String nome)throws SQLException{
			
		Connection conn;
		
		Marca marca = new Marca();
		
		conn = this.getConnection();
		ResultSet rs = null;
		Statement st = conn.createStatement();
		
		String sql = "SELECT * FROM \"MARCA\" WHERE nome_marca like '%" +nome+ "%';";
		rs = st.executeQuery(sql);
		
		if(rs.next()){
			marca.setId_marca(rs.getInt("id_marca"));
			marca.setNomeMarca(rs.getString("nome_marca"));
		}
		else{
			marca = null;
		}
		
		st.close();
		rs.close();
		conn.close();
		
		return marca;
	}
}
