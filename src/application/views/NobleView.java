package application.views;

import java.rmi.RemoteException;

import application.domain.Noble;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
/**
 * 
 * @author Sanchez
 *
 */
public class NobleView implements UIComponent {
	private Noble noble;
	private Pane root;
	
	private double sizeX, sizeY;

	public NobleView(Noble noble, double sizeX, double sizeY) {
		this.noble = noble;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		this.buildUI();
	}
	
	// TODO: See comment @ Noble.illustrationID
	public String getNobleImagePath() throws RemoteException
	{
		String pictureID = String.valueOf(noble.getIllustrationID());
		return String.format("file:resources/nobles/noble%s.png", pictureID);
	}
	
	
	private void buildUI()
	{
		Rectangle rect = new Rectangle(sizeX, sizeY / 1.5);

		//ImagePattern imagePattern = new ImagePattern(new Image(getNobleImagePath()));
        //rect.setFill(imagePattern);
        
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        
        root = new StackPane(rect);
	}

	public Pane asPane() {
		return root;
	}
	
}
