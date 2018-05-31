package application.util;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

/**
 * 
 * @author Alexander
 *
 */
public class ConfirmDialog extends javafx.scene.control.Alert{

	public ConfirmDialog(AlertType alertType) {
		super(alertType);
		this.stylizeAlert();
	
	}
	
	private void stylizeAlert() {
		this.initStyle(StageStyle.TRANSPARENT);
		this.setHeaderText(null);
		
		DialogPane dialogPane = this.getDialogPane();
		
		dialogPane.getScene().getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		dialogPane.getScene().setFill(null);
		dialogPane.getStyleClass().add("alert-dialog");
		
	}
	
	

}
