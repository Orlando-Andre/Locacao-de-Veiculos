/*Essa classe controla a tela cadastrar clientes e suas alterações e apresenta uma pesquisa simples*/
package controleVisao;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.ClienteDao;
import dao.MarcaDao;
import dao.ModeloDao;
import dao.UsuarioDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Cliente;
import modelo.Marca;
import modelo.Modelo;
import modelo.Usuario;
import util.Utilidade;

public class FXML_CadastrarClienteControle extends FXML_LoginControle implements Initializable {
	
	@FXML
	private ImageView mudaIdiomaEN;

	@FXML
	private ImageView mudaIdiomaES;
	
	@FXML
    private TabPane tabPane;

    @FXML
    private Tab abaCadastro;

    @FXML
    private Label lblCodigo;

    @FXML
    private TextField txtCodigo;

    @FXML
    private Label lblCNH;

    @FXML
    private TextField txtCNH;

    @FXML
    private Label lblNome;

    @FXML
    private TextField txtNome;

    @FXML
    private Label lblCPF;

    @FXML
    private TextField txtCPF;

    @FXML
    private Button btnIncluir;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

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
    private TableView<Cliente> tableView;

    @FXML
    private TableColumn<Cliente, String> tableColumnNome;

    @FXML
    private Label lblTableView;
    
    private ClienteDao cliDao;
    private Cliente cli;
    private ObservableList<Cliente> oblClientes;
    
    private String tipo = FXML_LoginControle.getTipo();
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){
   			@Override
   			public void changed(ObservableValue arg0, Object arg1, Object arg2){
   				selecionarItemTableViewClientes((Cliente) arg2);
   			}
   		});
    	
	}
    
    @FXML
    void btnIncluirOnAction(ActionEvent event) {
    	if(tipo.equals("Padrão")){
    		Utilidade.mensagemErro("Este usuário não tem permissão para acessar essa função");
    	}else{
    		boolean retorno;
        	Utilidade u = new Utilidade();
        	String nomeCli = txtNome.getText();
        	String cpfCli = txtCPF.getText();
        	String cnhCli = txtCNH.getText();
        	
        	if(nomeCli.length()== 0 || nomeCli.isEmpty()){
        		Utilidade.mensagemErro("Nome inválido. digite novamente");
        	}else{
        		if(cpfCli.length()== 0 || cpfCli.isEmpty() || u.validaConteudoInteiro(cpfCli)== false ){
            		Utilidade.mensagemErro("CPF inválido. digite novamente");
            	}else{
            		if(cnhCli.length()== 0 || cnhCli.isEmpty() || u.validaConteudoInteiro(cnhCli) == false){
                		Utilidade.mensagemErro("CNH inválido. digite novamente");
                	}else{
                		
                		cli = new Cliente();
                		
                		cli.setNomeCli(nomeCli);
                		cli.setCpf(cpfCli);
                		cli.setCnh(cnhCli);
                		
                		
                		cliDao = new ClienteDao();
                		
                		try{
                			retorno = cliDao.incluir(cli);
                			if(retorno){
                				Utilidade.mensagemInformacao("INCLUSÃO DE CLIENTE REALIZADA COM SUCESSO");
                			}
                			else{
                				Utilidade.mensagemErro("INCLUSÃO DE CLIENTE NÃO REALIZADA");
                			}
                		}
                		catch(SQLException e){
                			Utilidade.mensagemErro("ERRO DE INCLUSÃO DE CLIENTE");
                		}
                		
                		limpaDadosTela();
                		
                	}	
                }
            }
    	}
    }

    @FXML
    void btnAlterarOnAction(ActionEvent event) {
    	if(tipo.equals("Padrão")){
    		Utilidade.mensagemErro("Este usuário não tem permissão para acessar essa função");
    	}else{
    		 Utilidade u = new Utilidade();
       	  Cliente cli;
   		  boolean retorno;
   		  String msg = "";
   		  int conta = 0;
   		  
   		  if(txtCodigo.getText().length()== 0 || txtCodigo.getText().isEmpty()) {
   			  msg="Selecione uma cliente na aba pesquisa.";
   			  conta++;
   		  }
   		  
   		  if(txtNome.getText().length()==0 || txtNome.getText().isEmpty()){
   			  msg += "informe um nome válido para o Cliente. ";
   			  txtNome.setFocusTraversable(true);
   			  conta++;
   		  }
   		  
   		  if(txtCPF.getText().length()==0 || txtCPF.getText().isEmpty() || u.validaConteudoInteiro(txtCPF.getText())== false){
   			  msg += "informe um CPF válido para o Cliente.";
   			  txtCPF.setFocusTraversable(true);
   			  conta++;
   		  }
   		  
   		  if(txtCNH.getText().length()==0 || txtCNH.getText().isEmpty() || u.validaConteudoInteiro(txtCNH.getText())== false){
   			  msg += "informe um CNH válido para o Cliente.";
   			  txtCNH.setFocusTraversable(true);
   			  conta++;
   		  }
   		  
   		  if(conta>0){
   			  Utilidade.mensagemInformacao(msg);
   		  }else{
   			  
   			cli = new Cliente();
   			cli.setId_cli(Integer.parseInt(txtCodigo.getText()));
   			cli.setNomeCli(txtNome.getText());
   			cli.setCnh(txtCNH.getText());
   			cli.setCpf(txtCPF.getText());
   			
   			cliDao = new ClienteDao();
   			
   			try {
   				  retorno = cliDao.alterar(cli);
   				  if(retorno){
   					  Utilidade.mensagemInformacao("Alteração de Cliente realizada com sucesso");
   				  }
   				  else{
   					  Utilidade.mensagemErro("Erro de alteração de cliente");
   				  }
   			  
   			  }catch(SQLException e){
   				  Utilidade.mensagemErro("Erro de alteração de cliente");
   			  }
   			  limpaDadosTela();
   		  }
           		
    	}
    }

    @FXML
    void btnExcluirOnAction(ActionEvent event) {
    	if(tipo.equals("Padrão")){
    		Utilidade.mensagemErro("Este usuário não tem permissão para acessar essa função");
    	}else{
    		Cliente cli;
  		  boolean retorno;
  		  String msg = "";
  		  int conta = 0;
  		  
  		  if(txtCodigo.getText().length()==0 || txtCodigo.getText().isEmpty() || txtCodigo.getText().equals("")){
  			  msg="Você deve selecionar um cliente na aba de pesquisa ";
  			  conta++;
  		  }
  		  
  		  if(conta>0){
  			  Utilidade.mensagemInformacao(msg);
  		  }else{
  			 cli = new Cliente();
  			 cli.setId_cli(Integer.parseInt(txtCodigo.getText()));
  			 
  			 cliDao = new ClienteDao();
  			 try {
    			 retorno = cliDao.excluir(cli);
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
    }

    
    

    

	@FXML
    void btnPesquisarOnAction(ActionEvent event) {
		
		ArrayList<Cliente> retorno;
    	String nome = txtPesquisa.getText();
    	
    	if(nome.length()== 0 || nome.isEmpty()){
    		Utilidade.mensagemErro("Nome inválido. digite novamente");
    	}else{
    		
    		cliDao = new ClienteDao();
    		
    		try{
    			retorno = cliDao.consultar(nome);
    			
    			if(retorno.size() != 0){
    				carregarTableView(retorno);
    				
    			}
    			else{
    				Utilidade.mensagemErro("CLIENTE NÃO ENCONTRADO");
    			}
    		}
    		catch(SQLException e){
    			Utilidade.mensagemErro("ERRO DE PESQUISA DE CLIENTE");
    		}
    		
    	}

    }
	
	private void carregarTableView(ArrayList<Cliente> cliList) throws SQLException {
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeCli"));
    	ArrayList<Cliente> clientes = cliList;
    	oblClientes = FXCollections.observableArrayList(clientes);
    	this.tableView.setItems(oblClientes);
	}
	
    
    public void selecionarItemTableViewClientes(Cliente cli){
    	this.txtCodigo.setText(Integer.toString(cli.getId_cli()));
    	this.txtNome.setText(cli.getNomeCli());
    	this.txtCPF.setText(cli.getCpf());
    	this.txtCNH.setText(cli.getCnh());
    	tabPane.getSelectionModel().select(abaCadastro);
    }

	public void limpaDadosTela() {
		this.txtNome.setText("");
		this.txtCPF.setText("");
		this.txtCNH.setText("");
		this.txtCodigo.setText("");
	}
	
	
	@FXML
    void mudaIdiomaENOnAction(MouseEvent event) {
		String  lang = "en";
		String country = "US";
		
		Locale l = new Locale(lang,country);
		ResourceBundle r = ResourceBundle.getBundle("resources/Bundle" , l);
		
		abaCadastro.setText(r.getString("abaCad"));
		abaPesquisa.setText(r.getString("abaPesq"));
		lblCodigo.setText(r.getString("lblCod"));
		lblNome.setText(r.getString("lblNome"));
		lblCPF.setText(r.getString("lblcpf"));
		lblCNH.setText(r.getString("lblcnh"));
		btnIncluir.setText(r.getString("btnIncluir"));
		btnAlterar.setText(r.getString("btnAlterar"));
		btnExcluir.setText(r.getString("btnExcluir"));
		lblPesquisa.setText(r.getString("lblPesq"));
		lblTableView.setText(r.getString("lblTable"));
		tableColumnNome.setText(r.getString("tableCol"));
		
    }

    @FXML
    void mudaIdiomaESOnAction(MouseEvent event) {

    	String  lang = "es";
		String country = "ES";
		
		Locale l = new Locale(lang,country);
		ResourceBundle r = ResourceBundle.getBundle("resources/Bundle" , l);
		
		abaCadastro.setText(r.getString("abaCad"));
		abaPesquisa.setText(r.getString("abaPesq"));
		lblCodigo.setText(r.getString("lblCod"));
		lblNome.setText(r.getString("lblNome"));
		lblCPF.setText(r.getString("lblcpf"));
		lblCNH.setText(r.getString("lblcnh"));
		btnIncluir.setText(r.getString("btnIncluir"));
		btnAlterar.setText(r.getString("btnAlterar"));
		btnExcluir.setText(r.getString("btnExcluir"));
		lblPesquisa.setText(r.getString("lblPesq"));
		lblTableView.setText(r.getString("lblTable"));
		tableColumnNome.setText(r.getString("tableCol"));
    }
}
