package relatorios;

import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;

import conexao.Conexao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class VisualizaRelatorio {

	private String path;
	private String pathReportPackage;
	
	public VisualizaRelatorio(){
		
	}
	
	//Gera pdf
	public void gerarPdfSimples() throws Exception{
		
		Conexao conexao = new Conexao();
		Connection conn = conexao.getConnection();
		String caminhoArq = "src\\relatorios\\Relatorio_Simples.jasper";
		HashMap parametros = null;
		JasperPrint jp = JasperFillManager.fillReport(caminhoArq, parametros, conn);
		
		JasperViewer.viewReport(jp,false);
	}
	
	
	public void gerarPdfComplexo(Date d1 , Date d2) throws Exception{
		
		Conexao conexao = new Conexao();
		Connection conn = conexao.getConnection();
		
		String caminhoArq = "src\\relatorios\\Relatorio_Complexo.jasper";
		HashMap<String, Object> parametros = new HashMap<String , Object>();
		parametros.put("data1", d1);
		parametros.put("data2", d2);
		JasperPrint jp = JasperFillManager.fillReport(caminhoArq, parametros, conn);
		
		JasperViewer.viewReport(jp,false);
	}
	
	
	
	
	
	public String getPathToReportPackage(){
		return this.pathReportPackage;
	}
	
	public String getPath(){
		return this.path;
	}

}
