package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;



public class Utilidade {
	
	
	
	public static void mensagemInformacao(String msg){
		Alert alert;
		alert = new Alert(AlertType.ERROR,msg,ButtonType.OK);
		alert.setTitle("Locação de veículos");
		alert.setHeaderText("Informação");
		alert.show();
	}
	
	public static void mensagemErro(String msg){
		Alert alert;
		alert = new Alert(AlertType.ERROR,msg,ButtonType.OK);
		alert.setTitle("Locação de veículos");
		alert.setHeaderText("Erro");
		alert.show();
	}
	
	public static boolean validaConteudoInteiro(String s){
		int a = 0;
		boolean retorno = true;
		try{
			a = Integer.parseInt(s);
		}
		catch(Exception e){
			retorno = false;
		}
		return retorno;
	}

	public static boolean compararDatas(LocalDate dt1, LocalDate dt2) {
		
		int retorno = dt1.compareTo(dt2);
		
		//Data1 é anterior a data 2
		if(retorno < 0){
			return true;
		}else{
			return false;
		}
	}
}
