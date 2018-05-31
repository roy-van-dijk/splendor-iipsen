package application.views;


import java.rmi.RemoteException;
import java.util.Map;

import application.domain.Card;
import application.domain.Gem;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/**
 * 
 * @author Sanchez
 *
 */
public class FrontCardView extends CardView {
	
	private Card card;
	
	public FrontCardView(Card card, double sizeX, double sizeY) {
		super(sizeX, sizeY);
		this.card = card;
		
		try {
			this.buildUI();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void buildUI() throws RemoteException
	{
    	rect = new Rectangle(sizeX, sizeY);
    	
    	// Add image
        ImagePattern imagePattern = new ImagePattern(new Image(getImagePath()));
        rect.setFill(imagePattern);
        

        // Make rounded corners
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        
        // Add to root node
        root = new StackPane(rect);
        
        // TODO: add costs & gem type
        root.getChildren().add(addCardInformation());
        
	}
	
	private BorderPane addCardInformation() throws RemoteException
	{
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(7, 0, 15, 0));
		
		BorderPane prestigeAndBonus = new BorderPane();
		prestigeAndBonus.getStyleClass().add("cards-prestige-bonus");
		
		int prestigeValue = card.getPrestigeValue();

		Label prestigeLabel = new Label(prestigeValue == 0 ? "" : String.valueOf(prestigeValue));
		prestigeLabel.setAlignment(Pos.CENTER);
		prestigeLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 35));
			
		Gem bonusGem = card.getBonusGem();
		String gemPath = String.format("file:resources/gems/%s.png", bonusGem.toString().toLowerCase());
		ImageView bonusImage = new ImageView(new Image(gemPath));
		bonusImage.setFitWidth(50);
		bonusImage.setPreserveRatio(true);
        bonusImage.setSmooth(true);
        bonusImage.setCache(true);
		
		//prestigeAndBonus.getChildren().addAll(prestigeLabel, bonusImage);
		prestigeAndBonus.setLeft(prestigeLabel);
		prestigeAndBonus.setRight(bonusImage);
		BorderPane.setAlignment(prestigeLabel, Pos.CENTER);
        
		FlowPane costs = new FlowPane(Orientation.VERTICAL);
		costs.setAlignment(Pos.BOTTOM_CENTER);
		costs.setColumnHalignment(HPos.LEFT); // align labels on left
		// Render cost circles
		for(Map.Entry<Gem, Integer> entry : card.getCosts().entrySet())
		{	
			if(entry.getValue() != 0)
			{
				Circle costCircle = new Circle(26);
				String circlePath = String.format("file:resources/costs/circle_%s.png", entry.getKey().toString().toLowerCase());
				ImagePattern imagePattern = new ImagePattern(new Image(circlePath));
				costCircle.setFill(imagePattern);
				
				Label costLabel = new Label(String.valueOf(entry.getValue()));
				costLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 24));
				
				StackPane costPane = new StackPane(costCircle, costLabel);
				costs.getChildren().add(costPane);
			}
		}
		

		borderPane.setTop(prestigeAndBonus);
		borderPane.setLeft(costs);
		
		return borderPane;
	}
	
	@Override
	protected String getImagePath()
	{
		String path = "";
		try {
			path = String.format("file:resources/cards/%s/%s.jpg", card.getLevel().name().toLowerCase(), card.getIllustration());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return path;
	}

}
