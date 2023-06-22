
package controleVisao;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import relatorios.VisualizaRelatorio;
import util.Utilidade;

public class FXML_RelatoriosControle {

    @FXML
    private Tab abaSimples;

    @FXML
    private Button simples;

    @FXML
    private Tab abaComplexo;

    @FXML
    private Button complexo;

    @FXML
    private DatePicker dtInicial;

    @FXML
    private DatePicker dtFinal;

    @FXML
    private Label lblDtInicial;

    @FXML
    private Label lblDtFinal;

    @FXML
    void btnComplexoOnAction(ActionEvent event) throws Exception {
    	
    	LocalDate dt1 = dtInicial.getValue();
    	LocalDate dt2 = dtFinal.getValue();
    	
    	if(dt1 == null){
    		Utilidade.mensagemErro("INFORME UMA DATA VÁLIDA");
    	}else{
    		if(dt2 == null){
    			Utilidade.mensagemErro("INFORME UMA DATA VÁLIDA");
    		}else{
    			boolean retorno = Utilidade.compararDatas(dt1, dt2);
    	    	if(retorno == true){
    	    		// desenvolver código
    	    		
    	    		//converter Local Date para date
    	    		Date date1 =  Date.from(dt1.atStartOfDay(ZoneId.systemDefault()).toInstant());
        	    	Date date2 =  Date.from(dt2.atStartOfDay(ZoneId.systemDefault()).toInstant());
        	    	
        	    	// converter util.date para sql.date
        	    	java.sql.Date dataSqlInicial = new java.sql.Date(date1.getTime());
    				java.sql.Date dataSqlFinal = new java.sql.Date(date2.getTime());
    	    		
    	    		
        	    	VisualizaRelatorio vr = new VisualizaRelatorio();
        	    	vr.gerarPdfComplexo(dataSqlInicial, dataSqlFinal);

    	    		
    	    		
    	    	}else{
    	    		Utilidade.mensagemErro("DATAS INVÁLIDAS");
    	    	}
    		}
    	}
    	this.dtInicial.getEditor().clear();
		this.dtInicial.setValue(null);
		this.dtFinal.getEditor().clear();
		this.dtFinal.setValue(null);
    }

    @FXML
    void btnSimplesOnAction(ActionEvent event) throws Exception {
    	
    	VisualizaRelatorio vr = new VisualizaRelatorio();
    	vr.gerarPdfSimples();

    }

}
