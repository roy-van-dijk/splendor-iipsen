package application.util;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * @author Sanchez
 *
 */
public class AlertDialog extends javafx.scene.control.Alert {

	public AlertDialog(AlertType alertType) {
		super(alertType);
		this.stylizeAlert();
	}

	public AlertDialog(AlertType alertType, String contentText, ButtonType... buttons) {
		super(alertType, contentText, buttons);
		this.stylizeAlert();
	}
	
	private void stylizeAlert() {
		this.initStyle(StageStyle.TRANSPARENT);
		this.setHeaderText("Error");
		
		DialogPane dialogPane = this.getDialogPane();
		
		dialogPane.getScene().getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		dialogPane.getScene().setFill(null);
		dialogPane.getStyleClass().add("alert-dialog");
		
	}
}
