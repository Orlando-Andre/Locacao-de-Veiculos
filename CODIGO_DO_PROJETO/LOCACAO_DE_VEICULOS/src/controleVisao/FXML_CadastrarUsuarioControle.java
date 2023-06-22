/*Essa classe controla a tela cadastrar Usuário e suas alterações e apresenta uma pesquisa simples e só pode ser acessado por usuários administrador*/
package controleVisao;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;



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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import modelo.Usuario;
import util.Utilidade;

public class FXML_CadastrarUsuarioControle implements Initializable {
	
	@FXML
	private TabPane tabPane;

    @FXML
    private Tab abaCadastro;

    @FXML
    private Label lblCodigo;

    @FXML
    private TextField txtCodigo;

    @FXML
    private Label lblNome;

    @FXML
    private TextField txtNome;

    @FXML
    private Label lblSenha;

    @FXML
    private TextField txtSenha;

    @FXML
    private Button btnIncluir;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private ComboBox<String> cmbTipoUsuario;

    @FXML
    private Tab abaPesquisa;

    @FXML
    private Label lblPesquisa;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Button btnPesquisar;

    @FXML
    private ImageView imgPesquisar;

    @FXML
    private TableView<Usuario> tableView;

    @FXML
    private TableColumn<Usuario, String> tableColumnNome;

    @FXML
    private Label lblTableView;
    
    private ObservableList<String> tipoUsuarioList = FXCollections.observableArrayList("Administrador", "Padrão");
    private UsuarioDao userDao;
    private ArrayList<Usuario> usuarios;
    private ObservableList<Usuario> oblUsuarios;
    
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		cmbTipoUsuario.setItems(tipoUsuarioList);
		cmbTipoUsuario.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event){
    			cmbTipoUsuarioOnAction();
    		}
    	});
		
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2){
				selecionarItemTableViewUsuario((Usuario) arg2);
			}
		});
	}
    

    @FXML
    void btnIncluirOnAction(ActionEvent event) {
    	boolean retorno;
    	String nome = txtNome.getText();
    	String senha = txtSenha.getText();
    	String tipo = cmbTipoUsuario.getSelectionModel().getSelectedItem();
    	
    	if(nome.length()== 0 || nome.isEmpty()){
    		Utilidade.mensagemErro("Nome inválido. digite novamente");
    	}else{
    		if(senha.length()== 0 || senha.isEmpty()){
        		Utilidade.mensagemErro("Senha inválida. digite novamente");
        	}else{
        		if(tipo == null){
            		Utilidade.mensagemErro("Item não selecionado. selecione novamente");
            	}else{
            		// desenvolver inclusão
            		Usuario u = new Usuario();
            		u.setNome(nome);
            		u.setSenha(senha);
            		u.setTipo(tipo);
            		
            		userDao = new UsuarioDao();
            		
            		try{
    	    			retorno = userDao.incluir(u);
    	    			if(retorno){
    	    				Utilidade.mensagemInformacao("INCLUSÃO DE USUARIO REALIZADA COM SUCESSO");
    	    			}
    	    			else{
    	    				Utilidade.mensagemErro("INCLUSÃO DE USUARIO NÃO REALIZADA");
    	    			}
    	    		}
    	    		catch(SQLException e){
    	    			Utilidade.mensagemErro("ERRO DE INCLUSÃO DE USUARIO");
    	    		}
    	    		limpaDadosTela();
            	}
        	}
    	}
    }
    
    @FXML
    void btnAlterarOnAction(ActionEvent event) {
    	  Usuario u;
		  boolean retorno;
		  String msg = "";
		  int conta = 0;
		  
		  if(txtCodigo.getText().length()==0 || txtCodigo.getText().isEmpty() || txtCodigo.getText().equals("")){
			  msg="Você deve selecionar um usuario na aba de pesquisa ";
			  conta++;
		  }
		  
		  if(txtNome.getText().length()==0 || txtNome.getText().isEmpty() || txtSenha.getText().length()==0 || txtSenha.getText().isEmpty()){
			  msg += "informe um nome e senha válido para o Usuario.";
			  txtNome.setFocusTraversable(true);
			  txtSenha.setFocusTraversable(true);
			  conta++;
		  }
		  
		  
		  if(conta>0){
			  Utilidade.mensagemInformacao(msg);
		  }
		  else {
			  u = new Usuario();
			  u.setId(Integer.parseInt(txtCodigo.getText()));
			  u.setNome(txtNome.getText());
			  u.setSenha(txtSenha.getText());
			  u.setTipo(cmbTipoUsuario.getPromptText());
			  
			  userDao = new UsuarioDao();
			  
			  try {
				  retorno = userDao.alterar(u);
				  if(retorno){
					  Utilidade.mensagemInformacao("Alteração de Usuario realizada com sucesso");
				  }
				  else{
					  Utilidade.mensagemErro("Erro de alteração de Ator");
				  }
			  
			  }catch(SQLException e){
				  Utilidade.mensagemErro("Erro de alteração de Ator");
			  }
			  limpaDadosTela();
		  }
	}

    @FXML
    void btnExcluirOnAction(ActionEvent event) {
    	  Usuario u;
		  boolean retorno;
		  String msg = "";
		  int conta = 0;
		  
		  if(txtCodigo.getText().length()==0 || txtCodigo.getText().isEmpty() || txtCodigo.getText().equals("")){
			  msg="Você deve selecionar um usuario na aba de pesquisa ";
			  conta++;
		  }
		  
		  if(txtNome.getText().length()==0 || txtNome.getText().isEmpty() || txtSenha.getText().length()==0 || txtSenha.getText().isEmpty()){
			  msg += "informe um nome e senha válido para o Usuario.";
			  txtNome.setFocusTraversable(true);
			  txtSenha.setFocusTraversable(true);
			  conta++;
		  }
		  
		  
		  if(conta>0){
			  Utilidade.mensagemInformacao(msg);
		  }else{
			 u = new Usuario();
			 u.setId(Integer.parseInt(txtCodigo.getText()));
			 
			 userDao = new UsuarioDao();
			 try {
    			 retorno = userDao.excluir(u);
    			 if(retorno){
    				 Utilidade.mensagemInformacao("Exclusao de Usuario Realizada com sucesso");
    			 }else{
    				 Utilidade.mensagemErro("Exclusao não realizada");
    			 }
    		 }catch(SQLException e){
	    		Utilidade.mensagemErro("Erro de exclusao de Usuario");
	    	 }
			 limpaDadosTela();
		  }
    }

    
    
    @FXML
    void btnPesquisarOnAction(ActionEvent event) {
  
    	ArrayList<Usuario> retorno;
    	String nome = txtPesquisa.getText();
    	
    	if(nome.length()== 0 || nome.isEmpty()){
    		Utilidade.mensagemErro("Nome inválido. digite novamente");
    	}else{
    		
    		userDao = new UsuarioDao();
    		
    		try{
    			retorno = userDao.consultarNome(nome);
    			
    			if(retorno != null){
    				
    				carregarTableViewUsuarios(retorno);
    			}
    			else{
    				Utilidade.mensagemErro("USUARIO NÃO ENCONTRADO");
    			}
    		}
    		catch(SQLException e){
    			Utilidade.mensagemErro("ERRO DE PESQUISA DE USUARIOS");
    		}
    		
    	}
    	
    }
    
    

    @FXML
    void cmbTipoUsuarioOnAction() {
    	
    }
    
    public void carregarTableViewUsuarios(ArrayList<Usuario> userList) throws SQLException{
    	
    	tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	ArrayList<Usuario> usuarios = userList;
    	oblUsuarios = FXCollections.observableArrayList(usuarios);
    	this.tableView.setItems(oblUsuarios);
    }
    
    public void selecionarItemTableViewUsuario(Usuario u){

    	this.txtCodigo.setText(Integer.toString(u.getId()));
    	this.txtNome.setText(u.getNome());
    	this.txtSenha.setText(u.getSenha());
    	this.cmbTipoUsuario.setPromptText(u.getTipo());
    	tabPane.getSelectionModel().select(abaCadastro);
    }
    
    public void limpaDadosTela(){
    	this.txtNome.setText("");
    	this.txtSenha.setText("");
    	this.txtCodigo.setText("");
    	this.cmbTipoUsuario.setPromptText("Tipo de Usuário");
    }
}
