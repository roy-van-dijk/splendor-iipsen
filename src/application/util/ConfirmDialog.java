package application.util;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

/**
 * Shows a new confirm dialog window.
 * @author Alexander
 *
 */
public class ConfirmDialog extends javafx.scene.control.Alert{

	/**
	 * Shows a new confirm dialog window.
	 * @param AlertType alertType
	 */
	public ConfirmDialog(AlertType alertType) {
		super(alertType);
		this.stylizeAlert();
	
	}
	
	/**
	 * Styles the confirm dialog window.
	 *
	 */
	private void stylizeAlert() {
		this.initStyle(StageStyle.TRANSPARENT);
		this.setHeaderText(null);
		
		DialogPane dialogPane = this.getDialogPane();
		
		dialogPane.getScene().getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		dialogPane.getScene().setFill(null);
		dialogPane.getStyleClass().add("alert-dialog");
		
	}
	
	

}
