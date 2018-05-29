/**
 * 
 */
package application.views;



import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * Show a popupscreen 
 * @author Alexander
 *
 */
public class PopupView {

BorderPane pane;
	/**
	 * Initialize a Popup screen and shows it on the screen
	 * @param paragraphText
	 * @param title
	 */
	public PopupView(String paragraphText, String title) {
		System.out.println("Showing PopupView");
		
		pane  = new BorderPane();
		Scene scene = new Scene(pane, 550, 550);
		Stage stage = new Stage();
		VBox hbox = new VBox();
		Text text = new Text(paragraphText);
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		text.setFill(Color.WHITE);
				
		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
	
		hbox.getChildren().add(text);
		hbox.setAlignment(Pos.CENTER);
		
		pane.setCenter(hbox);
		pane.getStyleClass().add("page-1");
		pane.setPrefHeight(800);
		pane.setPrefWidth(500);
				
		stage.setScene(scene);
		stage.setTitle(title);
		stage.setResizable(false);
		stage.show();
	}

}
