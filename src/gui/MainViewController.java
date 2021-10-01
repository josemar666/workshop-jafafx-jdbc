package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.DepartamentService;
import model.services.SellerService;

public class MainViewController implements Initializable {
   
	 
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItenDepartament;
	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml", (SellerListController controller ) ->{
			controller.setSellerService(new SellerService());
			controller.upDateTableView();
		});
	}

	

	@FXML
	public void onMenuItemDepartamentAction() {
		loadView("/gui/DepartamentList.fxml", (DepartmentListController controller ) ->{
			controller.setDepartamentService(new DepartamentService());
			controller.upDateTableView();
		});
	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml" , x ->{});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}

	private synchronized <T> void loadView(String AbsoluteName , Consumer<T> iniatialinzigAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(AbsoluteName));
			VBox newvbox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainvBox =(VBox)((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainvBox.getChildren().get(0);
			mainvBox.getChildren().clear();
			mainvBox.getChildren().add(mainMenu);
			mainvBox.getChildren().addAll(newvbox.getChildren());
			
			T controller = loader.getController();
			iniatialinzigAction.accept(controller);
			
		} catch (IOException e) {
			Alerts.showAlert("IO EXCEPTION ", "ERROR LOADING VIES ", e.getMessage(), AlertType.ERROR);
		}
	
		


	}
}
