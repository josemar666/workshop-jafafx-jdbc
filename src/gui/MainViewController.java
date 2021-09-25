package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {
   
	 
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItenDepartament;
	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}

	@FXML
	public void onMenuItemDepartamentAction() {
		loadView("/gui/DepartamentList.fxml");
	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}

	private synchronized void loadView(String AbsoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(AbsoluteName));
			VBox newvbox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainvBox =(VBox)((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainvBox.getChildren().get(0);
			mainvBox.getChildren().clear();
			mainvBox.getChildren().add(mainMenu);
			mainvBox.getChildren().addAll(newvbox.getChildren());
		} catch (IOException e) {
			Alerts.showAlert("IO EXCEPTION ", "ERROR LOADING VIES ", e.getMessage(), AlertType.ERROR);
		}

	}
}
