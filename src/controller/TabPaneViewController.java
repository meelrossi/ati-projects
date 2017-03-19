package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class TabPaneViewController implements Initializable {
	@FXML
	private ImageTab imageTab;
	@FXML
	private OperationsTab operationsTab;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageTab.initialize();
		operationsTab.initialize();
	}

}
