package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class Main extends Application {
	
	private final static int cardSizeX = 120, cardSizeY = 190;
	private final static int tokenSizeRadius = 40;
	private final static int playingFieldSpacing = 15;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane gameLayout = new BorderPane();
			
			HBox topLayout = new HBox(10);
			VBox playingFieldLayout = createPlayingFieldLayout();
			VBox opponentsLayout = createOpponentsLayout();
			VBox tokensLayout = createFieldTokensLayout();
			HBox playerLayout = createPlayerLayout();
			playerLayout.setPadding(new Insets(15, 25, 15, 25));
			
			
			//gameLayout.setTop(topLayout);
			gameLayout.setCenter(playingFieldLayout);
			gameLayout.setLeft(opponentsLayout);
			gameLayout.setRight(tokensLayout);
			gameLayout.setBottom(playerLayout);
			
			gameLayout.setPadding(new Insets(0));
			
			CardsReader reader = new CardsReader();
			
			
			Scene scene = new Scene(gameLayout, 1800, 1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.getIcons().add(new Image(("file:splendor.png")));
			primaryStage.setScene(scene);
			//primaryStage.initStyle(StageStyle.UNDECORATED); // borderless
			//primaryStage.setMaximized(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public GridPane createPlayerTokens()
	{
		GridPane tokensGrid = new GridPane();
		tokensGrid.setVgap(2);
		tokensGrid.setHgap(15);
	        
		String[] tokenTypes = {"gold", "diamond","sapphire","emerald","ruby", "onyx"};
		
		int col = 0, row = 0;
		
		// Ugly way of filling grid
		for(String type : tokenTypes)
		{
			tokensGrid.add(createToken(type, randomNumberString(0,5), tokenSizeRadius / 2), col % 2, row % 3);
			col++;
			row++;
		}
		
		return tokensGrid;
	}
	
	public HBox createPlayerLayout()
	{
		HBox playerLayout = new HBox(25);
		playerLayout.getStyleClass().add("player");
		
		// Player prestige
		Label prestigeLabel = new Label(randomNumberString(0,14));
		prestigeLabel.setAlignment(Pos.CENTER);
		prestigeLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 50));
		prestigeLabel.getStyleClass().add("prestige");
		StackPane prestige = new StackPane(prestigeLabel);
		
		GridPane playerTokens = createPlayerTokens();
		
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		sep.setValignment(VPos.CENTER);
		//sep.setPrefHeight(80);
		
		playerLayout.getChildren().addAll(prestige, sep, playerTokens);
		return playerLayout;
	}
	
	public VBox createOpponentsLayout()
	{
		VBox opponentsRows = new VBox(10);

		opponentsRows.setAlignment(Pos.CENTER_LEFT);
		opponentsRows.getStyleClass().add("opponents");
		
		opponentsRows.setPrefWidth(600);
		
		VBox op1 = createOpponentLayout("Alexander"); // TODO: pass a Player object instead
		VBox op2 = createOpponentLayout("Tom");
		VBox op3 = createOpponentLayout("Roy");
		
		opponentsRows.getChildren().addAll(op1, op2, op3);
		
		return opponentsRows;
	}
	
	public HBox createOpponentTokensFrame()
	{
		HBox tokensFrame = new HBox();
		tokensFrame.setAlignment(Pos.CENTER);
		
		String[] tokenTypes = {"gold", "diamond","sapphire","emerald","ruby", "onyx"};
		for(String tokenType : tokenTypes)
		{
			VBox tokenFrame = new VBox(10); // TODO: Make custom class
			tokenFrame.setAlignment(Pos.CENTER);
			tokenFrame.setPadding(new Insets(5));
			HBox.setHgrow(tokenFrame, Priority.ALWAYS);
			
			Circle token = new Circle(tokenSizeRadius / 2);
			String imagePath = String.format("file:resources/tokens/token_%s.png", tokenType);
	        ImagePattern imagePattern = new ImagePattern(new Image(imagePath));
	        token.setFill(imagePattern);
	        
	        Label tokenCountLabel = new Label(randomNumberString(0, 5));
	        tokenCountLabel.setAlignment(Pos.CENTER);
	        tokenCountLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
	        
	        tokenFrame.getChildren().addAll(token, tokenCountLabel);
	        
	        tokensFrame.getChildren().add(tokenFrame);
		}
		return tokensFrame;
	}
	
	public HBox createOpponentReservedCardsFrame()
	{
		HBox reservedCardsFrame = new HBox();
		reservedCardsFrame.setAlignment(Pos.CENTER);
		
		String[] reservedCardsWithDifferentLevels = { "level1", "level2", "level3" }; // silly var for demo purposes
		
        for(String cardLevel : reservedCardsWithDifferentLevels)
        {
            StackPane container = new StackPane();
            container.setAlignment(Pos.CENTER);
            HBox.setHgrow(container, Priority.ALWAYS);
            
        	Rectangle card = new Rectangle(cardSizeX / 1.8, cardSizeY / 1.8);
    		String imagePath = String.format("file:resources/cards/%s/%s.png", cardLevel, cardLevel); 
            ImagePattern imagePattern = new ImagePattern(new Image(imagePath));
            card.setFill(imagePattern); 
            card.setArcHeight(10);
            card.setArcWidth(10);
            
            container.getChildren().add(card);

            reservedCardsFrame.getChildren().add(container);
        }
        
        return reservedCardsFrame;
	}
	
	public HBox createOpponentInfoFrame(String opponentName)
	{
		HBox topFrame = new HBox(10);
		topFrame.getStyleClass().add("opponent-header");	
		
		topFrame.setAlignment(Pos.CENTER);
		
		Label opponentNameLabel = new Label(opponentName);
		opponentNameLabel.setAlignment(Pos.CENTER_LEFT);
		opponentNameLabel.getStyleClass().add("opponent-name");	
		
		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.ALWAYS);    // Give prestige points any extra space
		
		Label opponentPrestigePoints = new Label(randomNumberString(0, 5));
		opponentPrestigePoints.setAlignment(Pos.CENTER_RIGHT);
		opponentPrestigePoints.getStyleClass().add("prestige");
		
		topFrame.getChildren().addAll(opponentNameLabel, spacer, opponentPrestigePoints);
		
		return topFrame;
	}
	
	public VBox createOpponentLayout(String opponentName)
	{
		VBox opponentFrame = new VBox(0);
		opponentFrame.getStyleClass().add("opponent");
		
		HBox topFrame = createOpponentInfoFrame(opponentName);
		HBox tokensFrame = createOpponentTokensFrame();
		HBox reservedCardsFrame = createOpponentReservedCardsFrame();
		
        opponentFrame.getChildren().addAll(topFrame, tokensFrame, reservedCardsFrame);
		
		return opponentFrame;
	}
	
	public String randomNumberString(int min, int max)
	{
		return Integer.toString((int) (Math.random() * max + min));
	}
	
	public HBox createToken(String type, String count, int radius)
	{
		Circle token = new Circle(radius);
		String imagePath = String.format("file:resources/tokens/token_%s.png", type);
        ImagePattern imagePattern = new ImagePattern(new Image(imagePath));
        token.setFill(imagePattern);
        
        Label tokenCountLabel = new Label(count);
        tokenCountLabel.setAlignment(Pos.CENTER);
        tokenCountLabel.getStyleClass().add("token-count");	
        tokenCountLabel.setFont(Font.font(radius * 2));
        
		HBox tokenRow = new HBox(10, token, tokenCountLabel);
		tokenRow.setAlignment(Pos.CENTER);
		
		return tokenRow;
	}
	
	public VBox createFieldTokensLayout()
	{
		VBox tokenRows = new VBox(10);
		tokenRows.setAlignment(Pos.CENTER);
		tokenRows.setPadding(new Insets(0, 150, 0, 0)); // remove later
		
		String[] tokenTypes = {"gold", "diamond","sapphire","emerald","ruby", "onyx"};
		
		for(String tokenType : tokenTypes)
		{
			HBox token = createToken(tokenType, randomNumberString(0,5), tokenSizeRadius);
			tokenRows.getChildren().add(token);
		}
		
		return tokenRows;
	}
	
	public VBox createPlayingFieldLayout()
	{
		VBox playingField = new VBox(playingFieldSpacing);
		playingField.setPadding(new Insets(0, 0, 0, 50)); // remove later
		
		HBox nobles = createNobles();
		HBox deckRow1 = createDeckCardRow(CardLevel.LEVEL1);
		HBox deckRow2 = createDeckCardRow(CardLevel.LEVEL2);
		HBox deckRow3 = createDeckCardRow(CardLevel.LEVEL3);
		
		HBox buttons = createActionButtons();
		
		playingField.getChildren().addAll(nobles,deckRow3,deckRow2,deckRow1,buttons);
		return playingField;
	}
	
	public HBox createDeckCardRow(CardLevel level)
	{
		HBox row = new HBox(playingFieldSpacing);
		
		int maxcards = 4;
		
		Rectangle deck = new Rectangle(cardSizeX, cardSizeY);
		String imagePath = String.format("file:resources/cards/%s/%s.png", level.name().toLowerCase(), level.name().toLowerCase());
        ImagePattern imagePattern = new ImagePattern(new Image(imagePath));
        deck.setFill(imagePattern);
        deck.setStroke(Color.BLACK);
        deck.setStrokeType(StrokeType.INSIDE);
        deck.setStrokeWidth(5);
        deck.setArcHeight(10);
        deck.setArcWidth(10);
        row.getChildren().add(deck);
        
        // POC
        for(int i=0; i < maxcards; i++)
        {
        	Rectangle card = new Rectangle(cardSizeX, cardSizeY);
    		imagePath = String.format("file:resources/cards/%s/%s.png", level.name().toLowerCase(), "bg1");
            imagePattern = new ImagePattern(new Image(imagePath));
            card.setFill(imagePattern); 
            card.setArcHeight(10);
            card.setArcWidth(10);
            row.getChildren().add(card);
        }
        
        return row;
	}
	
	public HBox createNobles()
	{
		HBox nobles = new HBox(playingFieldSpacing);
		for(int i = 0; i < 5; i++)
		{
			Rectangle noble = new Rectangle(cardSizeX, cardSizeY / 1.5);
			String pictureID = randomNumberString(1, 10);
			System.out.println(pictureID);
			String imagePath = String.format("file:resources/nobles/noble%s.png",pictureID);
	        ImagePattern imagePattern = new ImagePattern(new Image(imagePath));
	        noble.setFill(imagePattern);
	        noble.setArcHeight(10);
	        noble.setArcWidth(10);
			nobles.getChildren().add(noble);
		}
		
		return nobles;
	}
	
	public HBox createActionButtons()
	{
		HBox buttons = new HBox(5);
		
		Button btnReserveCard = new Button("Reserve card");
		Button btnPurchaseCard = new Button("Purchase card");
		Button btnTakeTwoTokens = new Button("Take two tokens");
		Button btnTakeThreeTokens = new Button("Take three tokens");
		
		buttons.getChildren().addAll(btnReserveCard, btnPurchaseCard, btnTakeTwoTokens, btnTakeThreeTokens);
		
		return buttons;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
