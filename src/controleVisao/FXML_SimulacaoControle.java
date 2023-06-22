/*Essa classe controla a tela de simulação e processamento de uma locação, é aqui que tudo acontece , além disso apresenta pesquisa simples*/

package controleVisao;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import dao.CarroDao;
import dao.ClienteDao;
import dao.Loca_CarroDao;
import dao.MarcaDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
import modelo.Loca_Carro;
import modelo.Marca;
import modelo.Modelo;
import util.Utilidade;

public class FXML_SimulacaoControle extends FXML_LoginControle implements Initializable {
	
	@FXML
    private TabPane tabPane;
	
	@FXML
	private Tab abaSimulacao;

    @FXML
    private Label lblDadosAuto;

    @FXML
    private Label lblMarca;

    @FXML
    private Label lblModelo;

    @FXML
    private Label lblPlaca;

    @FXML
    private Label lblCor;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtCor;

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtModelo;

    @FXML
    private DatePicker dtpInicial;

    @FXML
    private DatePicker dtpFinal;

    @FXML
    private Label lblDias;

    @FXML
    private TextField txtDias;

    @FXML
    private TextField txtPreco;

    @FXML
    private Label lblPreco;

    @FXML
    private Label lblValorTotal;

    @FXML
    private TextField txtValorTotal;

    @FXML
    private Button btnNovaSimulacao;

    @FXML
    private Button btnLocar;
    
    @FXML
    private Button btnCalcularDias;

    @FXML
    private Tab abaProcessamento;

    @FXML
    private Label lblDadosAutoProc;

    @FXML
    private Label lblMarcaProc;

    @FXML
    private Label lblModeloProc;

    @FXML
    private Label lblPlacaProc;

    @FXML
    private Label lblCorProc;

    @FXML
    private TextField txtMarcaProc;

    @FXML
    private TextField txtCorProc;

    @FXML
    private TextField txtPlacaProc;

    @FXML
    private TextField txtModeloProc;

    @FXML
    private DatePicker dtpInicialProc;

    @FXML
    private DatePicker dtpFinalProc;

    @FXML
    private Label lblDiasProc;

    @FXML
    private TextField txtDiasProc;

    @FXML
    private TextField txtPrecoProc;

    @FXML
    private Label lblPrecoProc;

    @FXML
    private Label lblValorTotalProc;

    @FXML
    private TextField txtValorTotalProc;

    @FXML
    private Label lblDadosLocProc;

    @FXML
    private Label lblDadosClienteProc;

    @FXML
    private Label lblCNHProc;

    @FXML
    private Label lblNomeProc;

    @FXML
    private Label lblCPFProc;

    @FXML
    private TextField txtCnhProc;

    @FXML
    private TextField txtNomeProc;

    @FXML
    private TextField txtCPFProc;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnFechar;

    @FXML
    private Tab abaPesquisaVeiculos;

    @FXML
    private Label lblPesquisa;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Button btnPesquisar;

    @FXML
    private ImageView imgPesquisar;

    @FXML
    private TableView<Carro> tableView;

    @FXML
    private TableColumn<Carro, String> tableColumnModelo;

    @FXML
    private TableColumn<Carro, String> tableColumnCor;

    @FXML
    private TableColumn<Carro, String> tableColumnPlaca;

    @FXML
    private Label lblTableView;
    
    @FXML
    private Tab abaPesquisaClientes;

    @FXML
    private Label lblPesquisa1;

    @FXML
    private TextField txtPesquisaCliente;

    @FXML
    private Button btnPesquisarCliente;

    @FXML
    private ImageView imgPesquisar1;
    
    @FXML
    private TableView<Cliente> tableViewCliente;

    @FXML
    private TableColumn<Cliente, String> tableColumnNome;

    @FXML
    private Label lblTableViewCliente;

    
    private MarcaDao marcaDao;
    private ClienteDao cliDao;
    private CarroDao carDao;
    private Loca_CarroDao LocCarDao;
    private ObservableList<Carro> oblCarro = FXCollections.observableArrayList();
    private ObservableList<Cliente> oblClientes = FXCollections.observableArrayList();
    
    private int IdCarro;
    private int idCliente;
    
    private String tipo = FXML_LoginControle.getTipo();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
    	txtPreco.setText("100.00");
    	txtPrecoProc.setText("100.00");
    	
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){
   			@Override
   			public void changed(ObservableValue arg0, Object arg1, Object arg2){
   				selecionarItemTableViewModelo((Carro) arg2);
   			}
   		});
		
		tableViewCliente.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){
   			@Override
   			public void changed(ObservableValue arg0, Object arg1, Object arg2){
   				selecionarItemTableViewClientes((Cliente) arg2);
   			}
   		});

	}
    
    @FXML
    void btnCalcularDiasOnAction(ActionEvent event) {
    	
    	LocalDate dt1 = dtpInicial.getValue();
    	LocalDate dt2 = dtpFinal.getValue();
    	
    	if(dt1 == null){
    		Utilidade.mensagemErro("INFORME UMA DATA VÁLIDA");
    	}else{
    		if(dt2 == null){
    			Utilidade.mensagemErro("INFORME UMA DATA VÁLIDA");
    		}else{
    			
    			boolean retorno = Utilidade.compararDatas(dt1, dt2);
    	    	if(retorno == true){
    	    		
    	    		dtpInicialProc.setValue(dt1);
    	    		dtpFinalProc.setValue(dt2);
    	    		
    	    		//converter Local Date para date
    	    		Date date1 = Date.from(dt1.atStartOfDay(ZoneId.systemDefault()).toInstant());
        	    	Date date2 = Date.from(dt2.atStartOfDay(ZoneId.systemDefault()).toInstant());
        	    	
        	    	//Calcular dias
        	    	long diasMili = (date2.getTime() - date1.getTime());
        	    	int dias = (int) (diasMili/86400000);
        	    	
        	    	this.txtDias.setText(Integer.toString(dias));
        	    	this.txtDiasProc.setText(Integer.toString(dias));
        	  
        	    	double valorTotal = (dias * Double.valueOf(txtPreco.getText()).doubleValue());
        	    	DecimalFormat df = new DecimalFormat("#,###.00");
        	    	
        	    	txtPrecoProc.setText(txtPreco.getText());
        	    	txtValorTotal.setText(df.format(valorTotal));
        	    	txtValorTotalProc.setText(df.format(valorTotal));
    	    	}else{
    	    		Utilidade.mensagemErro("DATAS INVÁLIDAS");
    	    	}
    		}
    	}
    	dt1 = null;
    	dt2 = null;
    }
    

    @FXML
    void btnLocarOnAction(ActionEvent event) {
    	if(tipo.equals("Padrão")){
    		Utilidade.mensagemErro("Este usuário não tem permissão para acessar essa função");
    	}else{
    		String mar = txtMarca.getText();
        	String dias = txtDias.getText();
        	
        	if(mar.length()== 0 || mar.isEmpty()){
        		Utilidade.mensagemErro("Selecione a aba pesquisar veículos e faça uma pesquisa");
        	}else{
        		if(dias.length()== 0 || dias.isEmpty()){
        			Utilidade.mensagemErro("Escolha datas válidas e clique no botão calcular dias");
        		}else{
        			tabPane.getSelectionModel().select(abaProcessamento);
        		}
        	}
    	}
    	
    }
    
    
    @FXML
    void btnNovaSimulacaoOnAction(ActionEvent event) {
    	limparAbaSimulacao();
    	limparAbaProcessamento();
		
    }

    @FXML
    void btnPesquisarOnAction(ActionEvent event) throws SQLException {
    	
    	String nMarca = txtPesquisa.getText();
    	
    	if(nMarca.length()== 0 || nMarca.isEmpty()){
    		Utilidade.mensagemErro("Informe uma marca válida");
    	}else{
 
    		marcaDao = new MarcaDao();
    		Marca marca = marcaDao.consultarNome(nMarca);
    		if(marca == null){
    			Utilidade.mensagemErro("Marca não encontrada");
    		}else {
    			try {
            		
            		ArrayList<Carro> carList = new ArrayList<Carro>();
            		
            		carDao = new CarroDao();
            		carList = carDao.consultarCarroMarca(marca);
        			
        			if(carList.size() != 0){
        				
        				carregarTableViewModelo(carList);
        				
        			}else{
        				Utilidade.mensagemErro("ESTA MARCA NÃO POSSUI CARROS CADASTRADOS");
        			}
        			
        		} catch (SQLException e) {
        			Utilidade.mensagemErro("ERRO DE PESQUISA DE VEÍCULOS");
        		}
    		}
    	}	
    }
    
    @FXML
    void btnPesquisarClienteOnAction(ActionEvent event) {
    	
    	ArrayList<Cliente> retorno;
    	String nome = txtPesquisaCliente.getText();
    	
    	if(nome.length()== 0 || nome.isEmpty()){
    		Utilidade.mensagemErro("Nome inválido. digite novamente");
    	}else{
    		
    		cliDao = new ClienteDao();
    		
    		try{
    			
    			retorno = cliDao.consultar(nome);
    			
    			if(retorno.size() != 0){
    				
    				carregarTableViewCliente(retorno);
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
    
	@FXML
    void btnConfirmarOnAction(ActionEvent event) {
		
		boolean retorno;
		
		int idCliente = getIdCliente();
		int idCarro = getIdCarro();
		
		LocalDate dt1 = dtpInicialProc.getValue();
		LocalDate dt2 = dtpFinalProc.getValue();

		Date date1 = Date.from(dt1.atStartOfDay(ZoneId.systemDefault()).toInstant());
    	Date date2 = Date.from(dt2.atStartOfDay(ZoneId.systemDefault()).toInstant());
    	
    	String preco = txtPrecoProc.getText();
		
    	if(idCarro == 0 ){
    		Utilidade.mensagemErro("Selecione um carro na aba selecionar veículos");
    	}else{
    		if(idCliente == 0){
        		Utilidade.mensagemErro("Selecione um cliente na aba selecionar clientes");
        	}else{
        		if(preco.length()== 0 || preco.isEmpty()){
        			Utilidade.mensagemErro("Digite um preco na aba simulação");
        		}else{
        			if(date1 == null || date2 == null){
        				Utilidade.mensagemErro("Datas inválidas");
        			}else{
        				
        				//converter data de util para sql
        				java.sql.Date dataSqlInicial = new java.sql.Date(date1.getTime());
        				java.sql.Date dataSqlFinal = new java.sql.Date(date2.getTime());
                		
                		Loca_Carro locCar = new Loca_Carro();
                		
                		locCar.setIdCarro(idCarro);
                		locCar.setIdCliente(idCliente);
                		locCar.setDtInicial(dataSqlInicial);
                		locCar.setDtFinal(dataSqlFinal);
                		locCar.setPreco(Double.parseDouble(preco));
                
                    	Loca_CarroDao locCarDao = new Loca_CarroDao();
                	
                		try{
                			retorno = locCarDao.incluir(locCar);
                			if(retorno){
                				Utilidade.mensagemInformacao("LOCAÇÃO REALIZADA COM SUCESSO");
                			}
                			else{
                				Utilidade.mensagemErro("LOCAÇÃO NÃO REALIZADA");
                			}
                		}
                		catch(SQLException e){
                			Utilidade.mensagemErro("ERRO DE LOCAÇÃO DE CARRO");
                		}
                	}
        		}
        	}
        }
    	limparAbaSimulacao();
    	limparAbaProcessamento();
    }

    private void carregarTableViewModelo(ArrayList<Carro> car){
    	tableColumnModelo.setCellValueFactory(new PropertyValueFactory<>("nome_modelo"));
    	tableColumnPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
    	tableColumnCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
    	ArrayList<Carro> modelo = car;
    	this.oblCarro = FXCollections.observableArrayList(modelo);
    	this.tableView.setItems(oblCarro);
    }
    
    private void selecionarItemTableViewModelo(Carro c) {
    	//Preencher tela simulacao
		this.txtMarca.setText(c.getNome_marca());
		this.txtModelo.setText(c.getNome_modelo());
		this.txtPlaca.setText(c.getPlaca());
    	this.txtCor.setText(c.getCor());
    	this.setIdCarro(c.getId_carro());
    	//Preencher tela Processamento
    	this.txtMarcaProc.setText(c.getNome_marca());
		this.txtModeloProc.setText(c.getNome_modelo());
		this.txtPlacaProc.setText(c.getPlaca());
    	this.txtCorProc.setText(c.getCor());
    	
    	tabPane.getSelectionModel().select(abaSimulacao);
    	limparAbaPesquisaVeiculos();
	}
    
    private void carregarTableViewCliente(ArrayList<Cliente> cli) {
    	tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeCli"));
    	ArrayList<Cliente> clientes = cli;
    	oblClientes = FXCollections.observableArrayList(clientes);
    	this.tableViewCliente.setItems(oblClientes);
	}
    
    private void selecionarItemTableViewClientes(Cliente cli) {
    	//Preenche os dados do cliente na tela processamento
    	this.txtNomeProc.setText(cli.getNomeCli());
    	this.txtCPFProc.setText(cli.getCpf());
    	this.txtCnhProc.setText(cli.getCnh());
    	this.setIdCliente(cli.getId_cli());
    	tabPane.getSelectionModel().select(abaProcessamento);
    	limparAbaPesquisaCliente();
	}
    
	private void limparAbaSimulacao(){
		this.txtMarca.setText("");
		this.txtModelo.setText("");
		this.txtPlaca.setText("");
		this.txtCor.setText("");
		
		this.dtpInicial.getEditor().clear();
		this.dtpInicial.setValue(null);
		this.dtpFinal.getEditor().clear();
		this.dtpFinal.setValue(null);
		
		this.txtPreco.setText("");
		this.txtDias.setText("");
		this.txtValorTotal.setText("");
		this.setIdCarro(0);
	}
	
	private void limparAbaProcessamento(){
		this.txtMarcaProc.setText("");
		this.txtModeloProc.setText("");
		this.txtPlacaProc.setText("");
		this.txtCorProc.setText("");
		
		this.txtCnhProc.setText("");
		this.txtNomeProc.setText("");
		this.txtCPFProc.setText("");
		this.setIdCliente(0);
		
		this.dtpInicialProc.getEditor().clear();
		this.dtpInicialProc.setValue(null);
		this.dtpFinalProc.getEditor().clear();
		this.dtpFinalProc.setValue(null);
		this.txtPrecoProc.setText("");
		this.txtDiasProc.setText("");
		this.txtValorTotalProc.setText("");
	}

	private void limparAbaPesquisaVeiculos() {
		this.txtPesquisa.setText("");
		this.oblCarro.clear();
		//this.tableView.setItems(oblCarro);
	}
	
	private void limparAbaPesquisaCliente() {
		this.txtPesquisaCliente.setText("");
		this.oblClientes.clear();
		//this.tableView.setItems(oblClientes);
	}
	
	@FXML
    void dtpFinalOnAction(ActionEvent event) {
		
    }

    @FXML
    void dtpInicialOnAction(ActionEvent event) {
    	
    }

    //metodos getters e setters
	public int getIdCarro() {
		return IdCarro;
	}

	public void setIdCarro(int idCarro) {
		IdCarro = idCarro;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	@FXML
    void btnFecharOnAction(ActionEvent event) {
    	System.exit(0);
    }
}
