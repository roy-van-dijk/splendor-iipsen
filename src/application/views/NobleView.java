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
		String illustration = String.valueOf(noble.getIllustration());
		return String.format("file:resources/nobles/%s.png", illustration);
	}
	
	
	private void buildUI()
	{
		Rectangle rect = new Rectangle(sizeX, sizeY);

		ImagePattern imagePattern = null;
		try {
			imagePattern = new ImagePattern(new Image(getNobleImagePath()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        rect.setFill(imagePattern);
        
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        
        root = new StackPane(rect);
	}

	public Pane asPane() {
		return root;
	}
	
}
