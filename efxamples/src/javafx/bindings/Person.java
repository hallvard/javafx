package javafx.bindings;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

public class Person {
	
	private String name;
	private String email;
	
	public Person() {
	}
	
	public Person(String name, String email) {
		setName(name);
		setEmail(email);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public static void main(String[] args) throws NoSuchMethodException {
		Person person = new Person("Hallvard Trætteberg", "hal@idi.ntnu.no");
		JavaBeanStringPropertyBuilder namePropertyBuilder = JavaBeanStringPropertyBuilder.create();
		StringProperty nameProperty = namePropertyBuilder.bean(person).name("name").build();
		JavaBeanStringPropertyBuilder emailPropertyBuilder = JavaBeanStringPropertyBuilder.create();
		StringProperty emailProperty = emailPropertyBuilder.bean(person).name("email").build();
		StringBinding binding = new StringFormatBinding("%s %s", nameProperty, emailProperty);
		System.out.println(binding.get());
	}
}
