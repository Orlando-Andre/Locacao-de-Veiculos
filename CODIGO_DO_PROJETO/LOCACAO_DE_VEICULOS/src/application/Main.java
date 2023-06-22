package application;
	
import java.sql.SQLException;

import dao.UsuarioDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws SQLException {
		

        /*Conexao cx = new Conexao();
        Connection conn = null;
        conn = cx.getConnection();
        if (conn == null) {
            System.out.println("Aconexão não ocorreu");

        } else {
            System.out.println("O banco está conectado");
        }
        */
		
		
		
		try {
			AnchorPane root = new AnchorPane();
			root = FXMLLoader.load(getClass().getResource("/visao/FXML_Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
