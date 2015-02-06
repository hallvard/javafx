package games.cards;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class TiledImageView extends ImageView {

	public TiledImageView(String url, int row, int column) {
		this(TiledImage.getTiledImage(url), row, column);
	}
	
	public TiledImageView(TiledImage image, int row, int column) {
		super(image);
		setTile(row, column);
	}

	protected void setTiledImage(String url, int row, int column) {
		setTiledImage(TiledImage.getTiledImage(url), row, column);
	}
	
	protected void setTiledImage(TiledImage image, int row, int column) {
		setImage(image);
		setTile(row, column);
	}
	
	private void setTile(int row, int column) {
		TiledImage image = (TiledImage) getImage();
		double tileWidth = image.getWidth() / image.columnCount;
		double tileHeight = image.getHeight() / image.rowCount;
		setViewport(new Rectangle2D(tileWidth * column, tileHeight * row, tileWidth, tileHeight));
	}
}
