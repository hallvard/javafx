package games;

import java.util.AbstractMap;
import java.util.Map;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

public class FxmlBuilderFactory implements BuilderFactory {

	public static class SimpleEntryBuilder<K,V> implements Builder<Map.Entry<K, V>> {

		private K key;
		private V value;
		
		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
		
		@Override
		public Map.Entry<K, V> build() {
			Map.Entry<K,V> picker = new AbstractMap.SimpleEntry<K,V>(key, value);
			return picker;
		}
	}

	private JavaFXBuilderFactory defaultBuilderFactory = new JavaFXBuilderFactory();

	@Override
	public Builder<?> getBuilder(Class<?> type) {
		return (type == Map.Entry.class) ? new SimpleEntryBuilder() : defaultBuilderFactory.getBuilder(type);
	}
}
