package application.util;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
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
		this.initStyle(StageStyle.UNDECORATED);
		this.setHeaderText(null);
		
		DialogPane dialogPane = this.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		dialogPane.getStyleClass().add("alert-dialog");
	}
}
