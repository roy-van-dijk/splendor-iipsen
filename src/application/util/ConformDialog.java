package application.util;
/**
 * 
 * @author Alexander
 *
 */
public class ConformDialog extends javafx.scene.control.Alert{

	public ConformDialog(AlertType alertType) {
		super(alertType);
		this.setTitle("Confirmation Dialog");
		this.setHeaderText("Look, a Confirmation Dialog");
		this.setContentText("Are you ok with this?");
	}

}
