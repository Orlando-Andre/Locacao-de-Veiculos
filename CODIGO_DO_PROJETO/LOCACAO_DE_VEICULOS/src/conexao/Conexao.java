package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private String login = "postgres";
    private String senha = "aluno";
    private String host = "localhost:5432";
    private String dbName = "DB_LOC_VEICULOS";
    private String url = "jdbc:postgresql://"+ host + "/" + dbName;

    public Connection conexao = null;

    public Conexao(){    }

    public Connection getConnection(){
        
        try {

            Class.forName("org.postgresql.Driver");
            
        } catch (ClassNotFoundException e) {

            return null;
        }

        try {

            this.conexao = (Connection) DriverManager.getConnection(url, login, senha);

        } catch (SQLException e) {

            return null;
        }
        return this.conexao;
    }
}












