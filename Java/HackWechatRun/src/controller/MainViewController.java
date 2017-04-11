/*
 * Author: VincentChou
 * Date: 2017.4.11
 * Version: 1.0
 */
package controller;

import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import service.UploadStepsService;

public class MainViewController {
	@FXML private Parent root;
	@FXML private TextField stepsTF;
	@FXML private TextField uidTF;
	@FXML private TextField pcValueTF;
	@FXML private Label stepsWarning; 
	@FXML private Label uidWarning;
	@FXML private Label pcValueWarning;
	@FXML private Button submitButton;
	@FXML private ProgressIndicator progress;
	
	@FXML
	private void submit() {
		clearWarning();
		if (checkInput()) {
			UploadStepsService service = new UploadStepsService(Integer.parseInt(stepsTF.getText()), uidTF.getText(), pcValueTF.getText());
			initUploadService(service);
			service.start();
		}
	}
	
	@FXML
	private void exit() {
		root.getScene().getWindow().hide();
	}	
	
	@FXML
	private void initialize() {
		Platform.runLater(() -> root.requestFocus());
	}
	
	private void initUploadService(UploadStepsService service) {
		service.setOnSucceeded(e -> {
			Alert alert = new Alert(AlertType.INFORMATION, "Succeeded in flashing " + stepsTF.getText() + " steps!");
			alert.initModality(Modality.WINDOW_MODAL);
			alert.initOwner(root.getScene().getWindow());
			alert.setHeaderText(null);
			alert.show();
		});
		service.setOnFailed(e -> {
			Throwable exception = service.getException(); 
//			exception.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.initModality(Modality.WINDOW_MODAL);
			alert.initOwner(root.getScene().getWindow());
			alert.setHeaderText(null);
			if (exception instanceof UnknownHostException)
				alert.setContentText("Can not connect to Ledongli's server, please check the network!");
			else
				alert.setContentText(service.getException().getMessage());
			alert.show();
		});
		submitButton.disableProperty().bind(service.runningProperty());
		progress.visibleProperty().bind(service.runningProperty());
	}
	
	private void clearWarning() {
		stepsWarning.setText(null);
		uidWarning.setText(null);
		pcValueWarning.setText(null);
	}
	
	private boolean checkInput() {
		boolean valid = true;
		// check steps
		if (stepsTF.getText() == null || stepsTF.getText().equals("")) {
			stepsWarning.setText("Can't be empty!");
			valid = false;
		}
		else {
			try {
				int steps = Integer.parseInt(stepsTF.getText());
				if (steps < 0 || steps > 98800) {
					stepsWarning.setText("Out of range(0 ~ 98800)!");
					valid = false;
				}
			} catch (Exception e) {
				stepsWarning.setText("Not an integer!");
				valid = false;
			}
		}
		// check uid 
		if (uidTF.getText() == null || uidTF.getText().equals("")) {
			uidWarning.setText("Can't be empty!");
			valid = false;
		}
		//check pc_value
		if (pcValueTF.getText() == null || pcValueTF.getText().equals("")) {
			pcValueWarning.setText("Can't be empty!");
			valid = false;
		}
		return valid;
	}
}
