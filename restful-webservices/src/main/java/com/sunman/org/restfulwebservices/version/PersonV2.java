package com.sunman.org.restfulwebservices.version;

public class PersonV2 {

	private Person person;

	public PersonV2(Person person) {
		super();
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "PersonV2 [person=" + person + "]";
	}
}

