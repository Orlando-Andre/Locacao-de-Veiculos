/*Essa classe controla a tela de Login e serve de base para todas as outras*/

package controleVisao;

import java.io.IOException;
import java.sql.SQLException;

import dao.UsuarioDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Usuario;
import util.Utilidade;

public class FXML_LoginControle {

    @FXML
    private ImageView imgLogin;

    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnSair;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private TextField txtUsuario;
    
    private static String tipo;

    @FXML
    void btnEntrarOnAction(ActionEvent event) throws IOException  {
    	Usuario u = null;
    	
    	if(txtUsuario.getText().length()== 0 || txtUsuario.getText().isEmpty() || pfSenha.getText().length()==0 || pfSenha.getText().isEmpty()){
    		Utilidade.mensagemErro("Usuário ou senha inválido");
    		
    	}
    	else{
    		u = new Usuario();
    		String nome = txtUsuario.getText();
        	String senha = pfSenha.getText();
        	limpaDadosTela();
        	UsuarioDao userDAO = new UsuarioDao();
        	try{
        		u = userDAO.consultarNomeSenha(nome, senha);
        		if(u != null){
        			
        			setTipo(u.getTipo());
        			abreJanelaMenu();
        			
        		}
        		else{
    				Utilidade.mensagemErro("Usuario não encontrado");
    			}
        		
        	}catch(SQLException e){
        		Utilidade.mensagemErro("*****************");
        	}
    	}
    }

    @FXML
    void btnSairOnAction(ActionEvent event) {
    	System.exit(0);
    }
    
    
    public void abreJanelaMenu() throws IOException{
    	
    	Parent root = FXMLLoader.load(getClass().getResource("/visao/FXML_Menu.fxml"));
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    }
    
    
    public void limpaDadosTela(){
    	this.txtUsuario.setText("");
    	this.pfSenha.setText("");
    }

	public static String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
    
}
