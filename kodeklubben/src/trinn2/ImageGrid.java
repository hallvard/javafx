package trinn2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ImageGrid<T> extends GridPane {

	public ImageGrid() {
		super();
		setFocusTraversable(true);
	}
	
	private int columns = 0, rows = 0;

	public void setRowCount(int rows) {
		this.rows = rows;
		setDimensions(columns, rows);
	}

	public void setColumnCount(int columns) {
		this.columns = columns;
		setDimensions(columns, rows);
	}

	private ImageView[] imageViews = null;

	public void setDimensions(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		imageViews = new ImageView[columns * rows];
		getChildren().clear();
	}
	
	public int getRowCount() {
		return rows;
	}

	public int getColumnCount() {
		return columns;
	}

	//
	
	private List<Map.Entry<T, String>> imageKeyMapEntries = null;
	
	public List<Map.Entry<T, String>> getImageKeyMapEntries() {
		if (imageKeyMapEntries == null) {
			imageKeyMapEntries = new ArrayList<Map.Entry<T,String>>();
		}
		return imageKeyMapEntries;
	}
	
	private Map.Entry<T, String> lastImageKeyMapEntry = null;
	private Map<T, String> imageKeyMap;
	
	private Map<T, String> getImageKeyMap() {
		if (imageKeyMap == null) {
			imageKeyMap = new HashMap<T, String>();
		}
		if (imageKeyMapEntries != null) {
			int start = imageKeyMapEntries.indexOf(lastImageKeyMapEntry) + 1;
			for (int i = start; i < imageKeyMapEntries.size(); i++) {
				Entry<T, String> entry = imageKeyMapEntries.get(i);
				imageKeyMap.put(entry.getKey(), entry.getValue());
				lastImageKeyMapEntry = entry;
			}
		}
		return imageKeyMap;
	}
	
	public List<T> getImageKeys() {
		return new ArrayList<T>(getImageKeyMap().keySet());
	}

	private Map<Object, Image> images = new HashMap<Object, Image>();

	private Image getImage(String imageUrl, Object... contexts) {
		Image image = images.get(imageUrl);
		if (image == null) {
			URL url = null;
			try {
				url = new URL(imageUrl);
			} catch (MalformedURLException e) {
			}
			int i = 0;
			while (url == null && i < contexts.length) {
				url = contexts[i++].getClass().getResource(imageUrl);
			}
			if (url == null) {
				url = getClass().getResource(imageUrl);
			}
			if (url == null) {
				try {
					url = new URL("file:" + imageUrl);
				} catch (MalformedURLException e) {
				}
			}
			if (url != null) {
				image = new Image(url.toExternalForm());
				images.put(imageUrl, image);
			}
		}
		return image;
	}
	
	public void setImage(T imageKey, Image image) {
		images.put(imageKey, image);
	}

	public void setImage(T imageKey, String imageUrl, Object... contexts) {
		Image image = getImage(imageUrl, contexts);
		if (image == null) {
			imageException(imageUrl);
		}
		getImageKeyMap().put(imageKey, imageUrl);
		images.put(imageUrl, image);
		images.put(imageKey, image);
	}

	private void imageException(Object imageKey) {
		throw new IllegalArgumentException("Couldn't get image for " + imageKey);
	}

	private String imageUrlFormat = null;

	public String getImageUrlFormat() {
		return imageUrlFormat;
	}

	public void setImageUrlFormat(String imageUrlFormat) {
		this.imageUrlFormat = imageUrlFormat;
	}
	
	private Image getImage(T imageKey) {
		Image image = images.get(imageKey);
		if (image == null && imageUrlFormat != null) {
			String imageKey2 = imageUrlFormat.replace("${key}", String.valueOf(imageKey));
			image = getImage(imageKey2);
			if (image != null) {
				images.put(imageKey, image);
				images.put(imageKey2, image);
			}
		}
		return image;
	}

	public Image setImage(T imageKey, int column, int row) {
		Image image = getImage(imageKey);
		int imagePos = row * columns + column;
		ImageView imageView = imageViews[imagePos];
		if (imageView != null) {
			imageView.setImage(image);
		} else {
			imageView = new ImageView(image);
			imageViews[imagePos] = imageView;
			add(imageView, column, row);
		}
		return image;
	}
}
