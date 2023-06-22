package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexao.Conexao;
import modelo.Marca;
import modelo.Modelo;

public class ModeloDao extends Conexao {
	
	//testado
	public boolean incluir(Modelo a) throws SQLException{
		
		boolean retorno = false;
		int intRetorno = 0;
		
		String INSERT = "INSERT INTO " + "\"MODELO\"" + " (nome_modelo , id_marca) VALUES (? , ?);";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(INSERT);
			 prepared.setString(1,a.getNomeModelo());
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
	public boolean alterar(Modelo a) throws SQLException{
		boolean retorno = false;
		int intRetorno = 0;
		
		String UPDATE = "UPDATE \"MODELO\" SET nome_modelo = ? WHERE \"id_modelo\" = ?";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(UPDATE);
			 prepared.setString(1,a.getNomeModelo());
			 prepared.setInt(2,a.getId_modelo());
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
	public boolean excluir(Modelo a)throws SQLException {
		
		boolean retorno = false;
		int intRetorno = 0;
		
		String DELETE = "DELETE FROM " + "\"MODELO\"" + " WHERE \"id_modelo\" = ? ;";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(DELETE);
			 prepared.setInt(1,a.getId_modelo());
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
	
	//Consultar todos os modelos / testado
	public ArrayList<Modelo> consultarTodos()throws SQLException{
		
		Connection conn;
		Modelo mod;
		
		ArrayList<Modelo> modeloList = new ArrayList<Modelo>();
		
		conn= this.getConnection();
		ResultSet rs = null;
		Statement st = conn.createStatement();
		
		String sql = "SELECT * FROM \"MODELO\" ORDER BY nome_modelo;";
		rs = st.executeQuery(sql);
		
		while(rs.next()){
			mod = new Modelo();
			mod.setNomeModelo(rs.getString("nome_modelo"));
			mod.setId_marca(rs.getInt("id_marca"));
			mod.setId_modelo(rs.getInt("id_modelo"));
			modeloList.add(mod);
		}
		st.close();
		rs.close();
		conn.close();
	
		return modeloList;
	}
	
	//Consultar  modelo por nome / testado
	public Modelo consultarNome(String nome)throws SQLException{
			
		Connection conn;
		
		Modelo modelo = new Modelo();
		
		conn = this.getConnection();
		ResultSet rs = null;
		Statement st = conn.createStatement();
		
		String sql = "SELECT * FROM \"MODELO\" WHERE nome_modelo like '%" +nome+ "%';";
		rs = st.executeQuery(sql);
		
		if(rs.next()){
			modelo.setId_marca(rs.getInt("id_marca"));
			modelo.setNomeModelo(rs.getString("nome_modelo"));
			modelo.setId_modelo(rs.getInt("id_modelo"));
		}
		else{
			modelo = null;
		}
		
		st.close();
		rs.close();
		conn.close();
		
		return modelo;
		
	}
		
	//Consultar todos modelos de acordo com a marca / testado
	public ArrayList<Modelo> consultarModeloPorMarca(Marca a)throws SQLException{
		
		Connection conn;
		Modelo mod;
		
		ArrayList<Modelo> modeloList = new ArrayList<Modelo>();
		
		conn= this.getConnection();
		ResultSet rs = null;
		Statement st = conn.createStatement();
		
		String sql = "SELECT * FROM \"MODELO\" WHERE \"id_marca\" = " + a.getId_marca() + " ORDER BY nome_modelo;";
		rs = st.executeQuery(sql);
		
		while(rs.next()){
			mod = new Modelo();
			mod.setNomeModelo(rs.getString("nome_modelo"));
			mod.setId_marca(rs.getInt("id_marca"));
			mod.setId_modelo(rs.getInt("id_modelo"));
			modeloList.add(mod);
		}
		
		st.close();
		rs.close();
		conn.close();
	
		return modeloList;
	}
		
}
