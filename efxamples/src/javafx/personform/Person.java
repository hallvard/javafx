package javafx.personform;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

public class Person {
	
	public Person() {
	}
	
	public Person(String name, String email) {
		setName(name);
		setEmail(email);
	}

	public static String NAME_PROPERTY = "name";
	
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if (! name.matches("[\\p{L}\\.\\-\\s)]*")) {
			throw new IllegalArgumentException("Illegal character in name");
		}
		String oldValue = this.name;
		this.name = name;
		pcs.firePropertyChange(NAME_PROPERTY, oldValue, name);
	}

	public static String EMAIL_PROPERTY = "email";

	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		String oldValue = this.email;
		this.email = email;
		pcs.firePropertyChange(EMAIL_PROPERTY, oldValue, email);
	}

	public static String BIRTHDAY_PROPERTY = "birthday";

	private LocalDate birthday;
	
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		LocalDate oldValue = this.birthday;
		this.birthday = birthday;
		pcs.firePropertyChange(BIRTHDAY_PROPERTY, oldValue, birthday);		
	}
	
	//
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
}
