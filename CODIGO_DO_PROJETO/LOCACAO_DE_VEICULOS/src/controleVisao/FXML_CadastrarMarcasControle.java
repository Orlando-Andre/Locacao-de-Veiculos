/*Essa classe controla a tela cadastrar Marca e suas altera��es*/
package controleVisao;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.MarcaDao;
import dao.UsuarioDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Marca;
import modelo.Usuario;
import util.Utilidade;

public class FXML_CadastrarMarcasControle extends FXML_LoginControle implements Initializable  {

	
    @FXML
    private Label lblTableView;

    @FXML
    private TableView<Marca> tableView;

    @FXML
    private TableColumn<Marca, String> tableColumnNomeMarca;

    @FXML
    private Label lblMarca;

    @FXML
    private TextField txtMarca;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnAtualizar;
    
    private MarcaDao marcaDao;
    private ObservableList<Marca> oblMarcas;
    private int idMarca = 0;
    
    private String tipo = FXML_LoginControle.getTipo();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){
   			@Override
   			public void changed(ObservableValue arg0, Object arg1, Object arg2){
   				selecionarItemTableViewMarcas((Marca) arg2);
   			}
   		});
		
	}
    
    

    @FXML
    void btnInserirOnAction(ActionEvent event) {
    	if(tipo.equals("Padr�o")){
    		Utilidade.mensagemErro("Este usu�rio n�o tem permiss�o para acessar essa fun��o");
    	}else{
    		boolean retorno;
        	
        	String marca = txtMarca.getText();
        	
        	if(marca.length()== 0 || marca.isEmpty()){
        		Utilidade.mensagemErro("Marca inv�lido. digite novamente");
        	}else{
        		// desenvolver inclus�o
        		Marca m = new Marca();
        		m.setNomeMarca(marca);
        		
        		marcaDao = new MarcaDao();
        		
        		try{
        			retorno = marcaDao.incluir(m);
        			if(retorno){
        				Utilidade.mensagemInformacao("INCLUS�O DE MARCA REALIZADA COM SUCESSO");
        			}
        			else{
        				Utilidade.mensagemErro("INCLUS�O DE MARCA N�O REALIZADA");
        			}
        		}
        		catch(SQLException e){
        			Utilidade.mensagemErro("ERRO DE INCLUS�O DE MARCA");
        		}
        		
        		limpaDadosTela();
        		btnAtualizarOnAction(event);
        	}
    	}
    }

   
	@FXML
    void btnAlterarOnAction(ActionEvent event) {
		if(tipo.equals("Padr�o")){
    		Utilidade.mensagemErro("Este usu�rio n�o tem permiss�o para acessar essa fun��o");
    	}else{
    		Marca m;
  		  boolean retorno;
  		  String msg = "";
  		  int conta = 0;
  		  
  		  if(getIdMarca() == 0) {
  			  msg="Voc� deve selecionar uma marca na table view ";
  			  conta++;
  		  }
  		  
  		  if(txtMarca.getText().length()==0 || txtMarca.getText().isEmpty()){
  			  msg += "informe um nome v�lido para a Marca.";
  			  txtMarca.setFocusTraversable(true);
  			  conta++;
  		  }
  		  
  		  if(conta>0){
  			  Utilidade.mensagemInformacao(msg);
  		  }else{
  			m = new Marca();
  			m.setId_marca(getIdMarca());
  			m.setNomeMarca(txtMarca.getText());
  			marcaDao = new MarcaDao();
  			
  			try {
  				  retorno = marcaDao.alterar(m);
  				  if(retorno){
  					  Utilidade.mensagemInformacao("Altera��o de marca realizada com sucesso");
  				  }
  				  else{
  					  Utilidade.mensagemErro("Erro de altera��o de marca");
  				  }
  			  
  			  }catch(SQLException e){
  				  Utilidade.mensagemErro("Erro de altera��o de marca");
  			  }
  			  limpaDadosTela();
  			  btnAtualizarOnAction(event);
  		  }
    		
    	}
		  
    }
    
    @FXML
    void btnExcluirOnAction(ActionEvent event) {
    	if(tipo.equals("Padr�o")){
    		Utilidade.mensagemErro("Este usu�rio n�o tem permiss�o para acessar essa fun��o");
    	}else{
    		Marca m;
  		  boolean retorno;
  		  String msg = "";
  		  int conta = 0;
  		  
  		  if(getIdMarca() == 0){
  			  msg="Voc� deve selecionar uma marca na table view ";
  			  conta++;
  		  }
  		  
  		  if(txtMarca.getText().length()==0 || txtMarca.getText().isEmpty()){
  			  msg += "informe um nome v�lido para a Marca.";
  			  txtMarca.setFocusTraversable(true);
  			  conta++;
  		  }
  		  
  		  if(conta>0){
  			  Utilidade.mensagemInformacao(msg);
  		  }else{
  			 m = new Marca();
  			 m.setId_marca(getIdMarca());
  			 marcaDao = new MarcaDao();
  			 try {
    			 retorno = marcaDao.excluir(m);
    			 if(retorno){
    				 Utilidade.mensagemInformacao("Exclus�o de marca Realizada com sucesso");
    			 }else{
    				 Utilidade.mensagemErro("Exclus�o n�o realizada , marca n�o encontrada");
    			 }
    		 }catch(SQLException e){
  	    		Utilidade.mensagemErro("Erro de exclus�o de Marca");
  	    	 }
  			 limpaDadosTela();
  			 btnAtualizarOnAction(event);
  		  }
    		
    	}
    	  
    }
    
    
    @FXML
    void btnAtualizarOnAction(ActionEvent event) {
    	
    	ArrayList<Marca> retorno;
		marcaDao = new MarcaDao();
		try{
			retorno = marcaDao.consultarTodos();
			
			if(retorno.size() != 0){
				
				carregarTableViewMarcas(retorno);
			}
			else{
				Utilidade.mensagemErro("MARCA N�O ENCONTRADAS");
			}
		}catch(SQLException e){
			Utilidade.mensagemErro("ERRO DE PESQUISA DE MARCAS");
		}
    }

	private void carregarTableViewMarcas(ArrayList<Marca> marcaList) throws SQLException {
    	tableColumnNomeMarca.setCellValueFactory(new PropertyValueFactory<>("nomeMarca"));
    	ArrayList<Marca> marcas = marcaList;
    	oblMarcas = FXCollections.observableArrayList(marcas);
    	this.tableView.setItems(oblMarcas);
	}
	
	public void selecionarItemTableViewMarcas(Marca m){
    	this.txtMarca.setText(m.getNomeMarca());
    	this.setIdMarca(m.getId_marca());
    }
    
    public void limpaDadosTela() {
		this.txtMarca.setText("");
		this.setIdMarca(0);
	}


	public int getIdMarca() {
		return idMarca;
	}
	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
	}

}
