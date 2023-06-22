package modelo;

import javafx.scene.control.SingleSelectionModel;

public class Carro {
	private String nome_marca;
	private String nome_modelo;
	private int id_carro;
	private int id_marca;
	private int id_modelo;
	private String placa;
	private String cor;
	
	public Carro(){
		
	}

	

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public int getId_carro() {
		return id_carro;
	}

	public void setId_carro(int id_carro) {
		this.id_carro = id_carro;
	}



	public int getId_marca() {
		return id_marca;
	}



	public void setId_marca(int id_marca) {
		this.id_marca = id_marca;
	}



	public int getId_modelo() {
		return id_modelo;
	}



	public void setId_modelo(int id_modelo) {
		this.id_modelo = id_modelo;
	}



	public String getNome_marca() {
		return nome_marca;
	}



	public void setNome_marca(String nome_marca) {
		this.nome_marca = nome_marca;
	}



	public String getNome_modelo() {
		return nome_modelo;
	}



	public void setNome_modelo(String nome_modelo) {
		this.nome_modelo = nome_modelo;
	}
}
