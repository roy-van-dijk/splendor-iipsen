package application.views;

import application.StageManager;
import application.controllers.GameController;
import application.controllers.MenuController;
import application.domain.Game;
import application.domain.GameObserver;
import application.domain.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
/**
 * 
 * @author Roy
 *
 */
public class HomeView implements UIComponent  {
	
//	private Home home;
	private MenuController menuController;
	
	
	private BorderPane root;
	

	private Pane buttons;
	
	
	private Button btnJoinLobby;
	private Button btnPreviousGame;
	private Button btnNewGame;
	
	// POC var - see opt 2. @ this.modelChanged() 
	//private List<UIComponent> comps = new ArrayList<>();

	public HomeView(MenuController menuController) {
//		this.home = home;
		this.menuController = menuController;
		
		
		// home.addObserver(this); // Causes modelChanged() to be called upon initialization, before the scene is even added to the primaryStage.
		
		this.buildUI();
		
	}
	
	
	// POC
	public void modelChanged()
	{
		/*
		 * Big question of what to do here.
		 * 1. We can either re-build the ENTIRE UI (easiest, but very inefficient) -> means calling this.buildUI() and switching scene's root node
		 * 2. [No] Or we can update either the ENTIRE UI (harder, but more efficient) -> means looping through all childviews and triggering modelChanged [THIS IS NOT EASILY POSSIBLE]  
		 * 3. Or we should do neither and instead have ALL "subdomain" objects implement the observer pattern and have them ONLY notify THEIR listeners. (shitload of classes, but most efficient)
		 */
		System.out.println("Updating home view");
		this.buildUI(); // Rebuild entire UI for now
		StageManager.getInstance().switchScene(root);
	}
	
	
	private void buildUI()
	{
		root = new BorderPane();
		buttons = buildButtons();
		
		root.setRight(buttons);
		root.getStyleClass().add("home-view");
		root.setPadding(new Insets(0));
	}
	
	private VBox buildButtons()
	{
		VBox buttons = new VBox(3);
		
		btnJoinLobby = new Button("Join Lobby");
		btnPreviousGame = new Button("Host Previous Game");
		btnNewGame = new Button("Host New Game");
		
		
		btnJoinLobby.setPrefWidth(450);
		btnPreviousGame.setPrefWidth(450);
		btnNewGame.setPrefWidth(450);
		
		btnJoinLobby.getStyleClass().add("home-button");
		btnPreviousGame.getStyleClass().add("home-button");
		btnNewGame.getStyleClass().add("home-button");
		
		btnJoinLobby.setOnAction(e -> menuController.joinLobby(root) );
		
		btnPreviousGame.setOnAction(e -> menuController.hostPreviousGame() );
		
		btnNewGame.setOnAction(e -> menuController.hostNewGame() ); 
		
		buttons.setAlignment(Pos.CENTER_LEFT); 
		buttons.setSpacing(10);
		buttons.setTranslateX(-250);
		buttons.setTranslateY(100);
		
		buttons.getChildren().addAll(btnJoinLobby, btnPreviousGame, btnNewGame);
		return buttons;
	}	

	public Pane asPane() {
		return root;
	}
}
