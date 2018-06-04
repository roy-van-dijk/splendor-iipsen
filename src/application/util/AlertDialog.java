package application.util;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * Shows a new alert dialog window.
 * @author Sanchez
 *
 */
public class AlertDialog extends javafx.scene.control.Alert {

	/**
	 * Shows a new alert dialog window.
	 * @param AlertType
	 */
	public AlertDialog(AlertType alertType) {
		super(alertType);
		this.stylizeAlert();
	}

	/**
	 * Shows a new alert dialog window with custom text and buttons.
	 * @param AlertType alertType 
	 * @param String contentText Text to display in the dialog window.
	 * @param ButtonType... buttons Buttons to display on the dialog window.
	 */
	public AlertDialog(AlertType alertType, String contentText, ButtonType... buttons) {
		super(alertType, contentText, buttons);
		this.stylizeAlert();
	}
	
	/**
	 * Styles the alert dialog window.
	 *
	 */
	private void stylizeAlert() {
		this.initStyle(StageStyle.TRANSPARENT);
		this.setHeaderText("Error");
		
		DialogPane dialogPane = this.getDialogPane();

		dialogPane.getScene().getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		dialogPane.getScene().setFill(null);
		dialogPane.getStyleClass().add("alert-dialog");

	}
}
