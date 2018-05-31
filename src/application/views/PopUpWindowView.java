package application.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
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
		
		Scene scene = new Scene(pane, 750, 500);
		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());

		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		
		DropShadow ds = new DropShadow();
		ds.setSpread(0.7f);
		ds.setColor(Color.color(0, 0, 0, 0.7));
		
		Text text = new Text(paragraphText);
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
		
		text.setEffect(ds);
		
		Text titletext = new Text(title);
		titletext.setFill(Color.WHITE);
		titletext.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
		
		titletext.setEffect(ds);

		box.getChildren().addAll(titletext, text);
		
		pane.setCenter(box);
		pane.getStyleClass().addAll("popup-view");
		
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(title);
		stage.setResizable(false);
		stage.show();
	}

}
