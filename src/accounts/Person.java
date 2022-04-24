package accounts;

import java.time.LocalDate;

public abstract class Person {
	protected String firstName;
	protected String lastName;
	protected String phone;
	protected LocalDate birthdate;
	
	public Person(String firstName, String lastName, String phone, LocalDate birthdate) {
		setBirthdate(birthdate);
		setFirstName(firstName);
		setLastName(lastName);
		setPhone(phone);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", birthdate="
				+ birthdate + "]";
	}
	
	
	
}
