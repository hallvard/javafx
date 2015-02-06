package games.cards;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class TiledImage extends Image {

	final int rowCount, columnCount;

	public TiledImage(String url, int rowCount, int columnCount) {
		super(url);
		this.rowCount = rowCount;
		this.columnCount = columnCount;
	}
	
	private static Map<String, TiledImage> tiledImages = new HashMap<>();
	
	public static TiledImage getTiledImage(String url) {
		return tiledImages.get(url);
	}
	
	public static TiledImage getTiledImage(String url, int rowCount, int columnCount) {
		TiledImage image = getTiledImage(url);
		if (image == null) {
			image = new TiledImage(url, rowCount, columnCount);
			tiledImages.put(url, image);
		}
		return image;
	}
}
