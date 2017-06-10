package it.polito.tdp.flight;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.AirportAndNum;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtDistanzaInput;

	@FXML
	private TextField txtPasseggeriInput;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		txtResult.clear();
		String sDist =this.txtDistanzaInput.getText();
		
		if(sDist.equals("")){
			txtResult.appendText("ERRORE: Inserire una distanza massima\n");
			return;
		}
		
		int dist;
		try{
		 dist=Integer.parseInt(sDist);
		}catch(NumberFormatException e){
			txtResult.appendText("ERRORE: Inserire un numero \n");
			return;
		}
		
		if(model.creaGrafo(dist)){
			txtResult.appendText("Creato grafo completamente connesso\n");
		}
		else{
			txtResult.appendText("Creato grafo non connesso\n");
		}
		
		txtResult.appendText("Aereoporto più lontano da Fiumicino "+model.getPiùLontanoByVoloDiretto(1555).toString()+"\n");
		
	}

	@FXML
	void doSimula(ActionEvent event) {
		String kS=this.txtPasseggeriInput.getText();
		if(kS.equals("")){
			txtResult.appendText("ERRORE:Inserire il numero di passeggeri! \n");
			return;
		}
		int k;
		try{
		k=Integer.parseInt(kS);
		}catch(NumberFormatException e){
			txtResult.appendText("ERRORE:Inserire un numero \n");
			return;
		}
		int counter=1;
		for(AirportAndNum an: model.simula(k)){
			txtResult.appendText(counter+")"+an.getAirport().toString()+" "+an.getNumPass()+"\n" );
			counter++;
		}
		
	}

	@FXML
	void initialize() {
		assert txtDistanzaInput != null : "fx:id=\"txtDistanzaInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtPasseggeriInput != null : "fx:id=\"txtPasseggeriInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Untitled'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
