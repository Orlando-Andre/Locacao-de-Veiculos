/*Essa classe controla a tela de menu , a qual apresenta todas as principais fun��es do sistema*/

package controleVisao;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import relatorios.VisualizaRelatorio;
import util.Utilidade;

public class FXML_MenuControle extends FXML_LoginControle {


    @FXML
    private Button btnRelatorio;
	
    @FXML
    private Button btnCadastrarMarca;

    @FXML
    private Button btnCadastrarModelo;

    @FXML
    private Button btnCadastrarCarro;

    @FXML
    private Button btnCadastrarCliente;

    @FXML
    private Button btnSimulacao;

    @FXML
    private Button btnCadastrarUsuario;
    
    private String tipo = FXML_LoginControle.getTipo();

    @FXML
    void btnCadastrarCarroOnAction(ActionEvent event) throws IOException {

    	Parent root = FXMLLoader.load(getClass().getResource("/visao/FXML_CadastrarCarro.fxml"));
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    }

    @FXML
    void btnCadastrarClienteOnAction(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/visao/FXML_CadastrarCliente.fxml"));
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();

    }

    @FXML
    void btnCadastrarMarcaOnAction(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/visao/FXML_CadastrarMarcas.fxml"));
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();

    }

    @FXML
    void btnCadastrarModeloOnAction(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/visao/FXML_CadastrarModelo.fxml"));
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();

    }

    @FXML
    void btnCadastrarUsuarioOnAction(ActionEvent event) throws IOException {
    	if(tipo.equals("Padr�o")){
    		Utilidade.mensagemErro("Este usu�rio n�o tem permiss�o para acessar essa fun��o");
    	}else{
    		Parent root = FXMLLoader.load(getClass().getResource("/visao/FXML_CadastrarUsuario.fxml"));
        	Scene scene = new Scene(root);
        	Stage stage = new Stage();
        	stage.setScene(scene);
        	stage.initModality(Modality.APPLICATION_MODAL);
        	stage.showAndWait();
    	}
    	
    }

    @FXML
    void btnSimulacaoOnAction(ActionEvent event) throws Exception {
    	
    	Parent root = FXMLLoader.load(getClass().getResource("/visao/FXML_Simulacao.fxml"));
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    	
    	

    }
    

    @FXML
    void btnRelatoriosOnAction(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/visao/FXML_Relatorios.fxml"));
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    }
}
