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
public class PopUpWindowView {

	private BorderPane pane;
	/**
	 * Initialize a Popup screen and shows it on the screen
	 * @param paragraphText
	 * @param title
	 */
	public PopUpWindowView(String paragraphText, String title) {
		System.out.println("Showing PopupView");
		
		pane = new BorderPane();

		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		
		Text text = new Text(paragraphText);
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		Text titletext = new Text(title);
		titletext.setFill(Color.WHITE);
		titletext.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));

		box.getChildren().addAll(titletext, text);

		pane.setCenter(box);
		pane.getStyleClass().add("page-1");
		pane.setPrefHeight(800);
		pane.setPrefWidth(500);

		Scene scene = new Scene(pane, 550, 550);
		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(title);
		stage.setResizable(false);
		stage.show();
	}

}
