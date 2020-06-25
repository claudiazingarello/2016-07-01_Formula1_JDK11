package it.polito.tdp.formulaone;

import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Season;
import it.polito.tdp.formulaone.model.TopPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller del turno A --> switchare al branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Season> boxAnno;

    @FXML
    private Button btnDreamTeam;
    
    @FXML
    private TextField textInputK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Season stagione = boxAnno.getValue();
    	if(stagione == null) {
    		txtResult.appendText("ERRORE: devi scegliere una stagione");
    		return;
    	}
    	
    	model.creaGrafo(stagione);
    	txtResult.appendText("Grafo creato!\n");
    	
    	TopPlayer topPlayer = model.topPlayer();
    	txtResult.appendText("Il pilota che ha totalizzato il miglior risultato nel "+stagione.getYear()+" è "+ topPlayer.getD().toString() +"\n");
    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		boxAnno.getItems().clear();
		boxAnno.getItems().addAll(model.getSeason());
		btnDreamTeam.setDisable(true);
	}
}
