package fxmlbox2d;

import javafx.scene.Node;

public interface INodeMapper<T> {
	T create(Node node);
}
