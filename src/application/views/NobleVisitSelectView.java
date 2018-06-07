//package application.views;
//
//import java.rmi.RemoteException;
//import java.util.List;
//
//import application.domain.Noble;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.effect.DropShadow;
//import javafx.scene.image.Image;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.paint.ImagePattern;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
///**
// * 
// * Show a popupscreen select 1 noble to visit 
// * @author Tom
// *
// */
//public class NobleVisitSelectView {
//
//	private BorderPane pane;
//	private Noble noble;
//	private Node nobleImg;
//
//	/**
//	 * maybe to be implemented if enough time
//	 * @param paragraphText
//	 * @param title
//	 */
//
//	public NobleVisitSelectView(List<Noble> visitingNobles) {
//		System.out.println("showing Noble visit select view");
//
//		pane = new BorderPane();
//		
//		Scene scene = new Scene(pane, 750, 500);
//		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
//
//		FlowPane noblePane = new FlowPane();
//		noblePane.setAlignment(Pos.CENTER);
//		
//		DropShadow ds = new DropShadow();
//		ds.setSpread(0.7f);
//		ds.setColor(Color.color(0, 0, 0, 0.7));
//		
//		Text text = new Text("Select the noble you would like to own");
//		text.setFill(Color.WHITE);
//		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
//		text.setEffect(ds);
//		
//		for(Noble noble : visitingNobles) 
//		{
//			NobleView nobleImg = new NobleView(noble, 20, 20);
//			nobleImg.asPane().setOnMouseClicked(e -> {
//				
//			});
//			noblePane.getChildren().add(this.nobleImg);
//		}
//		
//		
//		
//		
//		pane.setCenter(noblePane);
//		pane.getStyleClass().addAll("popup-view");
//		
//		Stage stage = new Stage();
//		stage.setScene(scene);
//		stage.setTitle("Kies de noble die je wilt hebben neef");
//		stage.setResizable(false);
//		stage.show();
//	}
//}
