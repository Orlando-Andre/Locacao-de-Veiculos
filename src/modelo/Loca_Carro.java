package modelo;

import java.sql.Date;

public class Loca_Carro {

	private int idCarro;
	private int idCliente;
	private Date dtInicial;
	private Date dtFinal;
	private double preco;
	
	public Loca_Carro(){
		
	}
	
	
	public int getIdCarro() {
		return idCarro;
	}
	public void setIdCarro(int idCarro) {
		this.idCarro = idCarro;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public Date getDtInicial() {
		return dtInicial;
	}
	public void setDtInicial(java.sql.Date date1) {
		this.dtInicial = date1;
	}
	public Date getDtFinal() {
		return dtFinal;
	}
	public void setDtFinal(java.sql.Date date2) {
		this.dtFinal = date2;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	
}
