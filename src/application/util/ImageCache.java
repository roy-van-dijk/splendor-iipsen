package application.util;

import java.util.HashMap;
import java.util.Map;

import application.StageManager;
import javafx.scene.image.Image;

/**
 * @author Sanchez
 *
 */
public class ImageCache {
	
	private static ImageCache instance;
	private static Map<String, Image> images;
	
	private ImageCache() {
		images = new HashMap<>();
	}
	
	public static ImageCache getInstance() {
		if (instance == null)
			instance = new ImageCache();

		return instance;
	}
	
	/**
	 * (Partial javadoc)
	 * @param backgroundLoading - Determines whether the image should be loaded in the background.
	 * This will throw an exception if the image is used in an ImagePattern before it's finished loading.
	 * 
	 */
	public Image fetchImage(String path, boolean backgroundLoading) {
		Image image = images.get(path);
		if(image == null)
		{
			image = new Image(path, false);
			images.put(path, image);
		}
		return image;
	}
}
