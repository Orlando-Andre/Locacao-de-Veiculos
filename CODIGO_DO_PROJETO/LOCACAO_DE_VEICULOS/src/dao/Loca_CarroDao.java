package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.Conexao;
import modelo.Carro;
import modelo.Loca_Carro;

public class Loca_CarroDao extends Conexao {

	public boolean incluir(Loca_Carro car) throws SQLException {
		
		boolean retorno = false;
		int intRetorno = 0;
		
		String INSERT = "INSERT INTO " + "\"LOCA/CARRO\"" + " (id_carro,id_cliente,data_inicial,data_final,preco) VALUES (?,?,?,?,?);";
		Connection conn = null;
		
		try{
			 conn = this.getConnection();
			 PreparedStatement prepared = conn.prepareStatement(INSERT);
			 prepared.setInt(1,car.getIdCarro());
			 prepared.setInt(2, car.getIdCliente());
			 prepared.setDate(3, (Date) car.getDtInicial());
			 prepared.setDate(4, (Date) car.getDtFinal());
			 prepared.setDouble(5, car.getPreco());
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
	
}
