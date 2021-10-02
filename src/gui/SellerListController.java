package gui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entites.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {

	private SellerService service;

	@FXML
	private TableView<Seller> tableViewSeller;
	@FXML
	private TableColumn<Seller, Integer> tableColumnId;
	@FXML
	private TableColumn<Seller, String> tableColumnName;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;
	
	@FXML
	private TableColumn<Seller, String> tableColumnEmail;
    
	@FXML
	private TableColumn<Seller, Date> tableColumnBirthDate;
	
	@FXML
	private TableColumn<Seller, Double> tableColumnBaseSalary;
	
	
	@FXML
	private Button btNew;

	private ObservableList<Seller> obsList;

	@FXML
	public void onbtNewAction(ActionEvent event) {
		Stage parentStage = gui.util.Utils.currentStage(event);
		Seller obj = new Seller();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}

	public void setSellerService(SellerService service) {

		this.service = service;

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("bithDate"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
		

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());

	}

	public void upDateTableView() {
		if (service == null) {
			throw new IllegalStateException("service was null !");
		}
		List<Seller> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewSeller.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	public void createDialogForm(Seller obj, String AbsoluteName, Stage parentStage) {
	//		try {
	//		FXMLLoader loader = new FXMLLoader(getClass().getResource(AbsoluteName));
	//			Pane pane = loader.load();
	//	
   //				DepartmentFormController controller = loader.getController();
		//	
		//			controller.setDepartment(obj);
		//			controller.setDepartmentService(new SellerService());
		//				controller.subscribeDataChangeListener(this);
		//		controller.upDateFormData();

		//			Stage dialogStage = new Stage();
		//		dialogStage.setTitle("Enter Department Data :");
		//		dialogStage.setScene(new Scene(pane));
		//		dialogStage.setResizable(false);
		//		dialogStage.initOwner(parentStage);
		//		dialogStage.initModality(Modality.WINDOW_MODAL);
		//		dialogStage.showAndWait();

		//	} catch (IOException e) {
		//		Alerts.showAlert("IOEXCEPTION", " ERROR LOADING VIEW  ", e.getMessage(), AlertType.ERROR);
		//		}
	}

	@Override
	public void onDataChanged() {
		upDateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/DepartmentForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Seller obj) {
		
	Optional<ButtonType> result =	Alerts.showConfirmation("Confirmation", "are you sure to delete ");
	if(result.get() == ButtonType.OK) {
		if(service == null) {
			throw new IllegalStateException("service was null");
		}try {
		service.remove(obj);
		upDateTableView();
		}catch(DbIntegrityException e) {
			Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	}

}
