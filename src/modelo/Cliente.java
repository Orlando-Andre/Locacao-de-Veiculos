package modelo;

public class Cliente {
	private int id_cli;
	private String cnh;
	private String nomeCli;
	private String cpf;
	
	public Cliente(){
		
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public String getNomeCli() {
		return nomeCli;
	}

	public void setNomeCli(String nomeCli) {
		this.nomeCli = nomeCli;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public int getId_cli() {
		return id_cli;
	}

	public void setId_cli(int id_cli) {
		this.id_cli = id_cli;
	}
	
}
