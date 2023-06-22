/*Essa classe controla a tela cadastrar carro e suas alterações e apresenta uma pesquisa complexa*/

package controleVisao;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.CarroDao;
import dao.MarcaDao;
import dao.ModeloDao;
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
import modelo.Carro;
import modelo.Cliente;
import modelo.Marca;
import modelo.Modelo;
import modelo.Usuario;
import util.Utilidade;

public class FXML_CadastrarCarroControle extends FXML_LoginControle implements Initializable {
	
	@FXML
    private TabPane tabPane;

    @FXML
    private Tab abaCadastro;

    @FXML
    private Button btnIncluir;

    @FXML
    private TextField txtCor;

    @FXML
    private TextField txtPlaca;

    @FXML
    private Label lblPlaca;

    @FXML
    private ComboBox<String> cmbMarcas;

    @FXML
    private ComboBox<String> cmbModelo;

    @FXML
    private Label lblCor;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Tab abaPesquisa;

    @FXML
    private Label lblTipoPesquisa;

    @FXML
    private ComboBox<String> cmbTipoPesquisa;

    @FXML
    private Label lblPesquisa;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private TableView<Carro> tableView;

    @FXML
    private TableColumn<Carro, String> tableColumnModelo;

    @FXML
    private TableColumn<Carro, String> tableColumnPlaca;

    @FXML
    private TableColumn<Carro, String> tableColumnCor;

    @FXML
    private Button btnPesquisa;

    @FXML
    private ImageView imgPesquisa;

    @FXML
    private Label lblTableView;
    
    private ObservableList<String> oblTipoPesquisa = FXCollections.observableArrayList("Marca", "Modelo", "Cor", "Placa");
    private ObservableList<String> oblMarcas = FXCollections.observableArrayList();
    private ObservableList<String> oblModelos = FXCollections.observableArrayList();
    private ObservableList<Carro> oblCarros = FXCollections.observableArrayList();
    private MarcaDao marcaDao;
    private ModeloDao modeloDao;
    private CarroDao carDao;
    private int idCarro;
    
    private String tipo = FXML_LoginControle.getTipo();
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
    	pesquisarMarcas();
		cmbMarcas.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event){
    			marcasComboBoxAction(event);
    		}
    	});
		
		cmbModelo.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event){
    			modeloComboBoxAction();
    		}
    	});
		
		cmbTipoPesquisa.setItems(oblTipoPesquisa);
		cmbTipoPesquisa.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event){
    			cmbTipoPesquisaOnAction();
    		}
    	});
		
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){
   			@Override
   			public void changed(ObservableValue arg0, Object arg1, Object arg2){
   				selecionarItemTableViewCarros((Carro) arg2);
   			}
   		});
	}

    @FXML
    void btnIncluirOnAction(ActionEvent event) throws SQLException {
    	if(tipo.equals("Padrão")){
    		Utilidade.mensagemErro("Este usuário não tem permissão para acessar essa função");
    	}else{
    		boolean retorno;
        	
        	String nomeMarca = cmbMarcas.getSelectionModel().getSelectedItem();
        	String nomeModelo = cmbModelo.getSelectionModel().getSelectedItem();
        	String placa  = txtPlaca.getText();
        	String cor = txtCor.getText();
        	
        	if(nomeMarca.length()== 0 || nomeMarca.isEmpty()){
        		Utilidade.mensagemErro("Selecione uma marca na combobox");
        	}else{
        		if(nomeModelo.length()== 0 || nomeModelo.isEmpty()){
            		Utilidade.mensagemErro("Selecione um modelo na combobox");
            	}else{
            		if(placa.length()== 0 || placa.isEmpty()){
            			Utilidade.mensagemErro("Digite uma Placa válida");
            		}else{
            			if(cor.length()== 0 || cor.isEmpty()){
            				Utilidade.mensagemErro("Digite uma cor válida");
            			}else{
            				
                   
                    		Modelo modelo = modeloDao.consultarNome(nomeModelo);
                    		Carro car = new Carro();
                    		
                    		
                    		car.setId_marca(modelo.getId_marca());
                    		car.setId_modelo(modelo.getId_modelo());
                    		car.setPlaca(placa);
                    		car.setCor(cor);
                        	
                        	carDao = new CarroDao();
                    	
                    		try{
                    			retorno = carDao.incluir(car);
                    			if(retorno){
                    				Utilidade.mensagemInformacao("INCLUSÃO DE CARRO REALIZADA COM SUCESSO");
                    			}
                    			else{
                    				Utilidade.mensagemErro("INCLUSÃO DE CARRO NÃO REALIZADA");
                    			}
                    		}
                    		catch(SQLException e){
                    			Utilidade.mensagemErro("ERRO DE INCLUSÃO DE CARRO");
                    		}
                    	}
            		}
            	}
            }
        	limpaDadosTela();
    		
    	}
    }
    	
    
    
    

	@FXML
    void btnAlterarOnAction(ActionEvent event) {
		if(tipo.equals("Padrão")){
    		Utilidade.mensagemErro("Este usuário não tem permissão para acessar essa função");
    	}else{
    		Carro carro;
  		  boolean retorno;
  		  String msg = "";
  		  int conta = 0;
  		  
  		  if(getIdCarro() == 0) {
  			  msg="Selecione um veículo na aba Pesquisa ";
  			  conta++;
  		  }
  		  
  		  if(txtPlaca.getText().length()==0 || txtPlaca.getText().isEmpty() || txtCor.getText().length()==0 || txtCor.getText().isEmpty() ){
  			  msg += "informe uma cor ou placa válida para o veículo.";
  			  txtPlaca.setFocusTraversable(true);
  			  txtCor.setFocusTraversable(true);
  			  conta++;
  		  }
  		  
  		  if(conta>0){
  			  Utilidade.mensagemInformacao(msg);
  		  }else{
  			carro = new Carro();
  			carro.setId_carro(getIdCarro());
  			carro.setCor(txtCor.getText());
  			carro.setPlaca(txtPlaca.getText());
  	
  			carDao = new CarroDao();
  			
  			try {
  				  retorno = carDao.alterar(carro);
  				  if(retorno){
  					  Utilidade.mensagemInformacao("Alteração de veículo realizada com sucesso");
  				  }
  				  else{
  					  Utilidade.mensagemErro("Erro de alteração de veículo");
  				  }
  			  
  			  }catch(SQLException e){
  				  Utilidade.mensagemErro("Erro de alteração de veículo");
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
    		Carro carro;
    		boolean retorno;
    		String msg = "";
    		int conta = 0;
    		  
    		if(getIdCarro() == 0) {
    			msg="Selecione um veículo na aba Pesquisa ";
    			conta++;
    		}
    		  
    		if(conta>0){
    			 Utilidade.mensagemInformacao(msg);
    		 }else{
    			carro = new Carro();
    			carro.setId_carro(getIdCarro());
    		
    			carDao = new CarroDao();
    			
    			try {
    				  retorno = carDao.excluir(carro);
    				  if(retorno){
    					  Utilidade.mensagemInformacao("Veículo excluído com sucesso");
    				  }
    				  else{
    					  Utilidade.mensagemErro("Erro de exclusão de veículo");
    				  }
    			  
    			  }catch(SQLException e){
    				  Utilidade.mensagemErro("Erro de exclusão de veículo");
    			  }
    			  limpaDadosTela();
    		  }
    	}
    }


    @FXML
    void btnPesquisaOnAction(ActionEvent event) throws SQLException {
    	
    	String tipoEscolhido = cmbTipoPesquisa.getSelectionModel().getSelectedItem();
    	String txtPesq = txtPesquisa.getText();
    	
    	if(tipoEscolhido == null || tipoEscolhido.length()== 0 || tipoEscolhido.isEmpty()){
    	
    		Utilidade.mensagemErro("Selecione uma tipo de pesquisa na combobox");
    	}else{
    		if(txtPesq.length()== 0 || txtPesq.isEmpty()){
        		Utilidade.mensagemErro("Selecione um modelo na combobox");
        	}else{
   
        		switch(tipoEscolhido){
        		case "Marca":
        			/*Pesquisar marca pelo nome, pegar o id da marca e pesquisar carros com aquela marca*/
        			
        			marcaDao = new MarcaDao();
    	    		Marca marca = marcaDao.consultarNome(txtPesq);
            		 try {
        	    		
        	    		ArrayList<Carro> carList = new ArrayList<Carro>();
        	    		
        	    		carDao = new CarroDao();
        	    		carList = carDao.consultarCarroMarca(marca);
        				
        				if(carList.size() != 0){
        					
        					carregarTableViewModelos(carList);
        					
        				}else{
        					Utilidade.mensagemErro("ESTA MARCA NÃO POSSUI CARROS CADASTRADOS");
        				}
        				
        			} catch (SQLException e) {
        				Utilidade.mensagemErro("ERRO DE PESQUISA DE VEÍCULOS");
        			}
        			
        			break;
        		case "Modelo":
        			/*Pesquisar modelo pelo nome, pegar o id do modelo e pesquisar carros com aquele modelo*/
        			modeloDao = new ModeloDao();
    	    		Modelo modelo = modeloDao.consultarNome(txtPesq);
            		 try {
        	    		
        	    		ArrayList<Carro> carList = new ArrayList<Carro>();
        	    		
        	    		carDao = new CarroDao();
        	    		carList = carDao.consultarCarroModelo(modelo);
        				
        	    		
        				if(carList.size() != 0){
        					
        					carregarTableViewModelos(carList);
        					
        				}else{
        					Utilidade.mensagemErro("MODELO NÃO ENCONTRADO");
        				}
        				
        			} catch (SQLException e) {
        				Utilidade.mensagemErro("ERRO DE PESQUISA DE VEÍCULOS");
        			}
        			
        			break;
        		case "Cor" : 
        			/*Pesquisar todos os carros daquela cor */
            		 try {
        	    		
        	    		ArrayList<Carro> carList = new ArrayList<Carro>();
        	    		
        	    		carDao = new CarroDao();
        	    		carList = carDao.consultarCarroCor(txtPesq);
        				
        	    		
        				if(carList.size() != 0){
        					
        					carregarTableViewModelos(carList);
        					
        				}else{
        					Utilidade.mensagemErro("COR NÃO ENCONTRADA");
        				}
        				
        			} catch (SQLException e) {
        				Utilidade.mensagemErro("ERRO DE PESQUISA DE VEÍCULOS");
        			}
        			
        			break;
        		case "Placa":
        			/*Pesquisar todas os carros daquela placa */
        			
        			try {
        	    		
        	    		ArrayList<Carro> carList = new ArrayList<Carro>();
        	    		
        	    		carDao = new CarroDao();
        	    		carList = carDao.consultarCarroPlaca(txtPesq);
        	    		
        				if(carList.size() != 0){
        					
        					carregarTableViewModelos(carList);
        					
        				}else{
        					Utilidade.mensagemErro("PLACA NÃO ENCONTRADA");
        				}
        				
        			} catch (SQLException e) {
        				Utilidade.mensagemErro("ERRO DE PESQUISA DE VEÍCULOS");
        			}
        			
        			break;
        		default:
        			Utilidade.mensagemErro("Informação não encontrada");
        		}
        	}
    	}
    }
    
    

	@FXML
    void marcasComboBoxAction(ActionEvent event) {

    	String nMarca = cmbMarcas.getSelectionModel().getSelectedItem();
    	marcaDao = new MarcaDao();
    	
    	 try {

    		modeloDao = new ModeloDao();
			Marca m = marcaDao.consultarNome(nMarca);
			ArrayList<Modelo> modeloList = modeloDao.consultarModeloPorMarca(m);
			
			oblModelos.clear();
			if(modeloList.size() != 0){
				for(int i = 0; i < modeloList.size();i++){
		    		Modelo mod = new Modelo();
		    		mod = modeloList.get(i);
		    		String nomeModelo = mod.getNomeModelo();
		    		oblModelos.add(nomeModelo);
		    	}
				
				cmbModelo.setItems(oblModelos);
				
				
			}else{
				Utilidade.mensagemErro("ESTA MARCA NÃO POSSUI MODELOS CADASTRADOS");
			}
			
		} catch (SQLException e) {
			Utilidade.mensagemErro("ERRO DE PESQUISA DE MODELOS");
		}

    }

    @FXML
    void modeloComboBoxAction() {
    	
    }

    @FXML
    void cmbTipoPesquisaOnAction() {
    	
    }
    
    public void pesquisarMarcas(){
    	ArrayList<Marca> retorno;
    	marcaDao = new MarcaDao();
		
		try{
			retorno = marcaDao.consultarTodos();
			
			if(retorno.size() != 0){
				
				for(int i = 0; i < retorno.size();i++){
		    		Marca marca = new Marca();
		    		marca = retorno.get(i);
		    		String nomeMarca = marca.getNomeMarca();
		    		oblMarcas.add(nomeMarca);
		    	}
				
				cmbMarcas.setItems(oblMarcas);
			}
			else{
				Utilidade.mensagemErro("ERRO DE PESQUISA DE MARCA");
			}
		}
		catch(SQLException e){
			Utilidade.mensagemErro("ERRO DE PESQUISA DE MARCA");
		}
	}
    	
    
    
    public void pesquisarModelos(){
    	ArrayList<Modelo> retorno;
    	modeloDao = new ModeloDao();
		
		try{
			retorno = modeloDao.consultarTodos();
			
			if(retorno != null){
				for(int i = 0; i < retorno.size();i++){
		    		Modelo mod = new Modelo();
		    		mod = retorno.get(i);
		    		String nomeModelo = mod.getNomeModelo();
		    		oblModelos.add(nomeModelo);
		    	}
			
				cmbModelo.setItems(oblModelos);
			}
			else{
				Utilidade.mensagemErro("ERRO DE PESQUISA DE MODELO");
			}
		}
		catch(SQLException e){
			Utilidade.mensagemErro("ERRO DE PESQUISA DE MODELO");
		}
    	
    }
    
    private void carregarTableViewModelos(ArrayList<Carro> carrosList) {
    	tableColumnModelo.setCellValueFactory(new PropertyValueFactory<>("nome_modelo"));
    	tableColumnPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
    	tableColumnCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
    	ArrayList<Carro> modelos = carrosList;
    	oblCarros = FXCollections.observableArrayList(modelos);
    	this.tableView.setItems(oblCarros);
	}
    
    	
	private void selecionarItemTableViewCarros(Carro c) {
		this.cmbMarcas.getSelectionModel().select(c.getNome_marca());
		this.cmbModelo.getSelectionModel().select(c.getNome_modelo());
    	this.txtPlaca.setText(c.getPlaca());
    	this.txtCor.setText(c.getCor());
    	this.setIdCarro(c.getId_carro());
    	limparAbaPesquisa();
    	tabPane.getSelectionModel().select(abaCadastro);
	}
	
	

	public int getIdCarro() {
		return idCarro;
	}

	public void setIdCarro(int idCarro) {
		this.idCarro = idCarro;
	}
    
    public void limpaDadosTela() {
		this.txtCor.setText("");
		this.txtPlaca.setText("");
		this.setIdCarro(0);
	}
    
    private void limparAbaPesquisa() {
		this.txtPesquisa.setText("");
		this.cmbTipoPesquisa.getSelectionModel().clearSelection();
		this.oblCarros.clear();
		this.tableView.setItems(oblCarros);
	}
}
