package application.views;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import application.domain.Card;
import application.domain.ColorBlindModes;
import application.domain.Gem;
import application.util.ImageCache;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
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
 * Displays the front side of a card.
 *
 * @author Sanchez
 */
public class FrontCardView extends CardView implements ColorChangeable{

	private final static double CIRCLE_RESIZE_FACTOR = 0.17;
	private final static double PRESTIGELABEL_RESIZE_FACTOR = 0.23;
	private final static double GEM_RESIZE_FACTOR = 0.35;

	private Card card;
	private ArrayList<Circle> costCircles;

	/**
	 * Creates a new front card display.
	 *
	 * @param card
	 * @param sizeX the size horizontal
	 * @param sizeY the size vertical
	 */
	public FrontCardView(Card card, double sizeX, double sizeY) {
		super(sizeX, sizeY);
		this.card = card;
		
		GameView.colorBlindViews.add(this);

		try {
			this.buildUI();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Builds the UI.
	 *
	 * @throws RemoteException
	 */
	private void buildUI() throws RemoteException {
    	rect = new Rectangle(sizeX, sizeY);
    	costCircles = new ArrayList<Circle>();
    	
    	// Add image
    	Image image = ImageCache.getInstance().fetchImage(getImagePath(), true);
        ImagePattern imagePattern = new ImagePattern(image);
        rect.setFill(imagePattern);
        

        // Make rounded corners
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        
        // Add to root node
        
        root = new StackPane();
        root.setMaxSize(rect.getWidth(), rect.getHeight());
        
        root.getChildren().addAll(rect, addCardInformation());        
	}

	/**
	 * Adds the card information.
	 *
	 * @return BorderPane
	 * @throws RemoteException
	 */
	private BorderPane addCardInformation() throws RemoteException {
		BorderPane borderPane = new BorderPane();

		BorderPane prestigeAndBonus = new BorderPane();
		prestigeAndBonus.getStyleClass().add("cards-prestige-bonus");

		int prestigeValue = card.getPrestigeValue();

		double prestigeLabelSize = ((this.sizeX + this.sizeY) / 2.0) * PRESTIGELABEL_RESIZE_FACTOR;
		Label prestigeLabel = new Label(prestigeValue == 0 ? "" : String.valueOf(prestigeValue));
		prestigeLabel.setAlignment(Pos.CENTER);
		prestigeLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, prestigeLabelSize));

		Gem bonusGem = card.getBonusGem();
		String gemPath = String.format("file:resources/gems/%s.png", bonusGem.toString().toLowerCase());
		ImageView bonusImage = new ImageView(new Image(gemPath));
		// System.out.printf("Card dimensions: X: %.2f, Y: %.2f\n", this.sizeX,
		// this.sizeY);
		double imageWidth = this.sizeX * GEM_RESIZE_FACTOR;
		bonusImage.setFitWidth(imageWidth);
		bonusImage.setPreserveRatio(true);
		bonusImage.setSmooth(true);
		bonusImage.setCache(true);

		prestigeAndBonus.setLeft(prestigeLabel);
		prestigeAndBonus.setRight(bonusImage);
		BorderPane.setAlignment(prestigeLabel, Pos.CENTER);

		FlowPane costs = new FlowPane(Orientation.VERTICAL);
		costs.setAlignment(Pos.BOTTOM_CENTER);
		costs.setColumnHalignment(HPos.LEFT); // align labels on left

		// Render cost circles
		for (Map.Entry<Gem, Integer> entry : card.getCosts().entrySet()) {
			if (entry.getValue() != 0) {
				double circleRadius = ((this.sizeX + this.sizeY) / 2.0) * CIRCLE_RESIZE_FACTOR;
				Circle costCircle = new Circle(circleRadius);
				costCircle.getProperties().put("GemType", entry.getKey().toString().toUpperCase());
				costCircles.add(costCircle);
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

	/* (non-Javadoc)
	 * @see application.views.CardView#getImagePath()
	 */
	@Override
	protected String getImagePath() {
		String path = "";
		try {
			path = String.format("file:resources/cards/%s/%s.jpg", card.getLevel().name().toLowerCase(),
					card.getIllustration());
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return path;
	}

	/**
	 * Updates the card view's circle colours based on the colour blind mode that is applied.
	 *
	 * @param mode the mode
	 */
	@Override
	public void updateView(ColorBlindModes mode) {
		for(Circle c : costCircles) {
			Gem gemType = Gem.valueOf(c.getProperties().get("GemType").toString());
			double hueOffset = gemType.hueOffset(gemType, mode);
			ColorAdjust colorAdjust = new ColorAdjust();
			colorAdjust.setHue(+hueOffset);
			c.setEffect(colorAdjust);	
			
		}
	}

}
