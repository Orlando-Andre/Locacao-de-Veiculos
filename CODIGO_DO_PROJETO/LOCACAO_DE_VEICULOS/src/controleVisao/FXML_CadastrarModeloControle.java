/*Essa classe controla a tela cadastrar Modelo e suas altera��es ps. o modelo � cadastrado de acordo com a marca*/
package controleVisao;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.MarcaDao;
import dao.ModeloDao;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import modelo.Marca;
import modelo.Modelo;
import util.Utilidade;

public class FXML_CadastrarModeloControle extends FXML_LoginControle implements Initializable {

    @FXML
    private Label lblMarcasCadastrardas;

    @FXML
    private ComboBox<String> cmbMarcasCadastradas;

    @FXML
    private Button btnPesquisarModelo;

    @FXML
    private ImageView imgPesquisar;

    @FXML
    private Label lblModelo;

    @FXML
    private TextField txtModelo;
    
    @FXML
    private TableView<Modelo> tableView;

    @FXML
    private TableColumn<Modelo, String> tableColumnModelo;

    @FXML
    private Button btnIncluir;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;
    
    private ObservableList<String> oblMarcas = FXCollections.observableArrayList();
    private ObservableList<Modelo> oblModelos = FXCollections.observableArrayList();
    private MarcaDao marcaDao;
    private ModeloDao modeloDao;
    private int idModelo;
    
    private String tipo = FXML_LoginControle.getTipo();
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
    	pesquisarMarca();
    	
    	cmbMarcasCadastradas.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event){
    			cmbMarcasCadastradasOnAction();
    		}
    	});
    	

		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){
   			@Override
   			public void changed(ObservableValue arg0, Object arg1, Object arg2){
   				selecionarItemTableViewModelos((Modelo) arg2);
   			}
   		});
	}
    
    @FXML
    void btnIncluirOnAction(ActionEvent event) throws SQLException {
    	if(tipo.equals("Padr�o")){
    		Utilidade.mensagemErro("Este usu�rio n�o tem permiss�o para acessar essa fun��o");
    	}else{
    		boolean retorno;
        	
        	String nomeModelo = txtModelo.getText();
        	String nMarca = cmbMarcasCadastradas.getSelectionModel().getSelectedItem();
        	
        	if(nMarca.length()== 0 || nMarca.isEmpty()){
        		Utilidade.mensagemErro("Selecione uma marca na combobox");
        	}else{
        		if(nomeModelo.length()== 0 || nomeModelo.isEmpty()){
            		Utilidade.mensagemErro("Modelo inv�lido. digite novamente");
            	}else{
            		// desenvolver inclus�o
            		Modelo mod = new Modelo();
            		
            		nMarca = cmbMarcasCadastradas.getSelectionModel().getSelectedItem();
            		Marca marca = marcaDao.consultarNome(nMarca);
            		
            		mod.setNomeModelo(nomeModelo);
            		mod.setId_marca(marca.getId_marca());
                	
                	modeloDao = new ModeloDao();
            	
            		try{
            			retorno = modeloDao.incluir(mod);
            			if(retorno){
            				Utilidade.mensagemInformacao("INCLUS�O DE MODELO REALIZADA COM SUCESSO");
            			}
            			else{
            				Utilidade.mensagemErro("INCLUS�O DE MODELO N�O REALIZADA");
            			}
            		}
            		catch(SQLException e){
            			Utilidade.mensagemErro("ERRO DE INCLUS�O DE MODELO");
            		}
            	}
        	}
        	limpaDadosTela();
    	}
    	
    }

    
    
    @FXML
    void btnAlterarOnAction(ActionEvent event) {
    	if(tipo.equals("Padr�o")){
    		Utilidade.mensagemErro("Este usu�rio n�o tem permiss�o para acessar essa fun��o");
    	}else{
    		Modelo m;
  		  boolean retorno;
  		  String msg = "";
  		  int conta = 0;
  		  
  		  if(getIdModelo() == 0) {
  			  msg="Selecione uma marca na combobox em seguida, voc� deve selecionar uma modelo na table view ";
  			  conta++;
  		  }
  		  
  		  if(txtModelo.getText().length()==0 || txtModelo.getText().isEmpty()){
  			  msg += "informe um nome v�lido para o modelo.";
  			  txtModelo.setFocusTraversable(true);
  			  conta++;
  		  }
  		  
  		  if(conta>0){
  			  Utilidade.mensagemInformacao(msg);
  		  }else{
  			m = new Modelo();
  			m.setId_modelo(getIdModelo());
  			m.setNomeModelo(txtModelo.getText());
  			modeloDao = new ModeloDao();
  			
  			try {
  				  retorno = modeloDao.alterar(m);
  				  if(retorno){
  					  Utilidade.mensagemInformacao("Altera��o de modelo realizada com sucesso");
  				  }
  				  else{
  					  Utilidade.mensagemErro("Erro de altera��o de modelo");
  				  }
  			  
  			  }catch(SQLException e){
  				  Utilidade.mensagemErro("Erro de altera��o de modelo");
  			  }
  			  limpaDadosTela();
  		  }
    	}
    	  

    }

    @FXML
    void btnExcluirOnAction(ActionEvent event) {
    	if(tipo.equals("Padr�o")){
    		Utilidade.mensagemErro("Este usu�rio n�o tem permiss�o para acessar essa fun��o");
    	}else{
    		Modelo m;
  		  boolean retorno;
  		  String msg = "";
  		  int conta = 0;
  		  
  		  if(getIdModelo() == 0){
  			  msg="Voc� deve selecionar um modelo na table view ";
  			  conta++;
  		  }
  		  
  		  if(txtModelo.getText().length()==0 || txtModelo.getText().isEmpty()){
  			  msg += "informe um nome v�lido para o modelo.";
  			  txtModelo.setFocusTraversable(true);
  			  conta++;
  		  }
  		  
  		  if(conta>0){
  			  Utilidade.mensagemInformacao(msg);
  		  }else{
  			 m = new Modelo();
  			 m.setId_modelo(getIdModelo());
  			 modeloDao = new ModeloDao();
  			 try {
   			 retorno = modeloDao.excluir(m);
   			 if(retorno){
   				 Utilidade.mensagemInformacao("Exclus�o de modelo Realizada com sucesso");
   			 }else{
   				 Utilidade.mensagemErro("Exclus�o n�o realizada , modelo n�o encontrada");
   			 }
   		 }catch(SQLException e){
  	    		Utilidade.mensagemErro("Erro de exclus�o de modelo");
  	    	 }
  			 limpaDadosTela();
  		  }
    	}
    }
    
    @FXML
    void btnPesquisarModeloOnAction(ActionEvent event) {
    	
    	String nMarca = cmbMarcasCadastradas.getSelectionModel().getSelectedItem();
    	marcaDao = new MarcaDao();
    	
    	 try {

    		modeloDao = new ModeloDao();
			Marca m = marcaDao.consultarNome(nMarca);
			ArrayList<Modelo> modeloList = modeloDao.consultarModeloPorMarca(m);
			
			if(modeloList.size() != 0){
				
				carregarTableViewModelos(modeloList);
				
			}else{
				Utilidade.mensagemErro("ESTA MARCA N�O POSSUI MODELOS CADASTRADOS");
			}
			
		} catch (SQLException e) {
			Utilidade.mensagemErro("ERRO DE PESQUISA DE MODELOS");
		}
    }
    

    @FXML
    void cmbMarcasCadastradasOnAction() {

    }

    
    public void pesquisarMarca(){
    	ArrayList<Marca> retorno;
    	marcaDao = new MarcaDao();
		
		try{
			retorno = marcaDao.consultarTodos();
			
			if(retorno != null){
				for(int i = 0; i < retorno.size();i++){
		    		Marca marca = new Marca();
		    		marca = retorno.get(i);
		    		String nomeMarca = marca.getNomeMarca();
		    		oblMarcas.add(nomeMarca);
		    	}
				
				cmbMarcasCadastradas.setItems(oblMarcas);
			}
			else{
				Utilidade.mensagemErro("ERRO DE PESQUISA DE MARCA");
			}
		}
		catch(SQLException e){
			Utilidade.mensagemErro("ERRO DE PESQUISA DE MARCA");
		}
	}
    
    
    public void carregarTableViewModelos(ArrayList<Modelo> m) throws SQLException {
    	tableColumnModelo.setCellValueFactory(new PropertyValueFactory<>("nomeModelo"));
    	ArrayList<Modelo> modelos = m;
    	oblModelos = FXCollections.observableArrayList(modelos);
    	this.tableView.setItems(oblModelos);
	}

	public void selecionarItemTableViewModelos(Modelo m){
    	this.txtModelo.setText(m.getNomeModelo());
    	this.setIdModelo(m.getId_modelo());
    }

	public int getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(int idModelo) {
		this.idModelo = idModelo;
	}
    
    public void limpaDadosTela() {
		this.txtModelo.setText("");
		this.cmbMarcasCadastradas.setPromptText("Selecione uma marca");
		setIdModelo(0);
	}
}
