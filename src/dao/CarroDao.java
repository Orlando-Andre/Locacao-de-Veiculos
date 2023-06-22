package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexao.Conexao;
import modelo.Carro;
import modelo.Marca;
import modelo.Modelo;

public class CarroDao extends Conexao {
		
	//testado
	public boolean incluir(Carro car) throws SQLException {
			
		boolean retorno = false;
		int intRetorno = 0;
		
		String INSERT = "INSERT INTO " + "\"CARRO\"" + " (id_marca,id_modelo,placa,cor) VALUES (?,?,?,?);";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(INSERT);
			 prepared.setInt(1,car.getId_marca());
			 prepared.setInt(2, car.getId_modelo());
			 prepared.setString(3, car.getPlaca());
			 prepared.setString(4, car.getCor());
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
	public boolean alterar(Carro car) throws SQLException{
		boolean retorno = false;
		int intRetorno = 0;
		
		String UPDATE = "UPDATE \"CARRO\" SET placa = ? , cor = ? WHERE \"id_carro\" = ?";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(UPDATE);
			 prepared.setString(1,car.getPlaca());
			 prepared.setString(2,car.getCor());
			 prepared.setInt(3, car.getId_carro());
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
	
	// testado
	public boolean excluir(Carro car)throws SQLException {
		
		boolean retorno = false;
		int intRetorno = 0;
		
		String DELETE = "DELETE FROM \"CARRO\"  WHERE \"id_carro\" = ? ;";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(DELETE);
			 prepared.setInt(1,car.getId_carro());
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
	
	
	//consultar todos os carros por marca / testado
		public ArrayList<Carro> consultarCarroMarca(Marca a) throws SQLException {
			
			Connection conn;
			
			Carro car = new Carro();
			
			conn = this.getConnection();
			ResultSet rs = null;
			Statement st = conn.createStatement();
			
			int idMarca = a.getId_marca();
			ArrayList<Carro> carrosList = new ArrayList<Carro>();
			
			String sql = "SELECT \"nome_marca\" , \"nome_modelo\" , placa, cor , \"id_carro\" , C.\"id_modelo\" , C.\"id_marca\" FROM \"CARRO\" C, \"MARCA\" , \"MODELO\"  WHERE \"MARCA\".\"id_marca\" = " + idMarca + " AND \"MODELO\".\"id_marca\" = " + idMarca + " AND C.\"id_marca\" = " + idMarca +"";
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				car = new Carro();
				car.setId_carro(rs.getInt("id_carro"));
				car.setId_modelo(rs.getInt("id_modelo"));
				car.setId_marca(rs.getInt("id_marca"));
				car.setPlaca(rs.getString("placa"));
				car.setCor(rs.getString("cor"));
				car.setNome_marca(rs.getString("nome_marca"));
				car.setNome_modelo(rs.getString("nome_modelo"));
				carrosList.add(car);
			}
			
			
			st.close();
			rs.close();
			conn.close();
			
			return carrosList;
			
		}
		
		//consultar todos os carros por modelo / testado
		public ArrayList<Carro> consultarCarroModelo(Modelo m) throws SQLException {
			
			Connection conn;
			
			Carro car = new Carro();
			
			conn = this.getConnection();
			ResultSet rs = null;
			Statement st = conn.createStatement();
			
			int idModelo = m.getId_modelo();
			ArrayList<Carro> carrosList = new ArrayList<Carro>();
			
			String sql = "SELECT \"nome_marca\" , \"nome_modelo\" , placa, cor , \"id_carro\" , C.\"id_modelo\" , C.\"id_marca\" FROM \"CARRO\" C, \"MARCA\" , \"MODELO\"  WHERE \"MARCA\".\"id_marca\" = C.\"id_marca\" AND \"MODELO\".\"id_modelo\" = " + idModelo + " AND C.\"id_modelo\" = " + idModelo +"";
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				car = new Carro();
				car.setId_carro(rs.getInt("id_carro"));
				car.setId_modelo(rs.getInt("id_modelo"));
				car.setId_marca(rs.getInt("id_marca"));
				car.setPlaca(rs.getString("placa"));
				car.setCor(rs.getString("cor"));
				car.setNome_marca(rs.getString("nome_marca"));
				car.setNome_modelo(rs.getString("nome_modelo"));
				carrosList.add(car);
			}
			
			
			st.close();
			rs.close();
			conn.close();
			
			return carrosList;
			
		}
		
		//testado
		public ArrayList<Carro> consultarCarroCor(String cor) throws SQLException{
	
			Connection conn;
			
			Carro car = new Carro();
			
			conn = this.getConnection();
			ResultSet rs = null;
			Statement st = conn.createStatement();
			
			String c = cor;
			ArrayList<Carro> carrosList = new ArrayList<Carro>();
			
			String sql = "SELECT \"nome_marca\" , \"nome_modelo\" , placa, cor , \"id_carro\" , C.\"id_modelo\" , C.\"id_marca\" FROM \"CARRO\" C, \"MARCA\" , \"MODELO\"  WHERE \"MARCA\".\"id_marca\" = C.\"id_marca\" AND \"MODELO\".\"id_modelo\" =  C.\"id_modelo\" AND C.\"cor\" = \'" + cor +"\'";
			System.out.println(sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				car = new Carro();
				car.setId_carro(rs.getInt("id_carro"));
				car.setId_modelo(rs.getInt("id_modelo"));
				car.setId_marca(rs.getInt("id_marca"));
				car.setPlaca(rs.getString("placa"));
				car.setCor(rs.getString("cor"));
				car.setNome_marca(rs.getString("nome_marca"));
				car.setNome_modelo(rs.getString("nome_modelo"));
				carrosList.add(car);
			}
			
			st.close();
			rs.close();
			conn.close();
			
			return carrosList;
		}


		//testado
		public ArrayList<Carro> consultarCarroPlaca(String placa) throws SQLException {
			
			Connection conn;
			
			Carro car = new Carro();
			
			conn = this.getConnection();
			ResultSet rs = null;
			Statement st = conn.createStatement();
			
			String p = placa;
			ArrayList<Carro> carrosList = new ArrayList<Carro>();
			String sql = "SELECT \"nome_marca\" , \"nome_modelo\" , placa, cor , \"id_carro\" , C.\"id_modelo\" , C.\"id_marca\" FROM \"CARRO\" C, \"MARCA\" , \"MODELO\"  WHERE \"MARCA\".\"id_marca\" = C.\"id_marca\" AND \"MODELO\".\"id_modelo\" =  C.\"id_modelo\" AND C.\"placa\" = \'" + p +"\'";
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				car = new Carro();
				car.setId_carro(rs.getInt("id_carro"));
				car.setId_modelo(rs.getInt("id_modelo"));
				car.setId_marca(rs.getInt("id_marca"));
				car.setPlaca(rs.getString("placa"));
				car.setCor(rs.getString("cor"));
				car.setNome_marca(rs.getString("nome_marca"));
				car.setNome_modelo(rs.getString("nome_modelo"));
				carrosList.add(car);
			}
			
			st.close();
			rs.close();
			conn.close();
			
			return carrosList;
		}

}
