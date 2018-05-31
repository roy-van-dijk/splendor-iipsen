package application.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ManualWindowView {
		
	BorderPane pane;
	
	public ManualWindowView() {
		System.out.println("Showing manual");
		
		pane  = new BorderPane();
		Scene scene = new Scene(pane, 735, 950);
		Stage stage = new Stage();
		HBox navButtons = new HBox();
		
		Button nextPage = new Button("Next");
		
		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		
		nextPage.getStyleClass().add("manual-navigation-button");
		
		navButtons.getChildren().add(nextPage);
		navButtons.setAlignment(Pos.CENTER);
		navButtons.setSpacing(10);
		navButtons.setPadding(new Insets(10, 10, 10, 10));
		
		pane.setBottom(navButtons);
		pane.getStyleClass().add("page-1");
		pane.setPrefHeight(800);
		pane.setPrefWidth(500);
		
		nextPage.setOnAction(e -> switchPage());
				
		stage.setScene(scene);
		stage.setTitle("Manual");
		stage.setResizable(false);
		stage.show();
	}
	
	public void switchPage () {
		System.out.println("Switching manual pages");
		
		String page = pane.getStyleClass().get(1);
		int currentPage = Integer.parseInt(page.substring(page.length() - 1)); 
		
		if (currentPage < 4) {
			currentPage = currentPage + 1;
		}
		else {
			currentPage = 1;
		}
		
		pane.getStyleClass().remove(1);
		pane.getStyleClass().add("page-" + currentPage);
	}

}
