import java.io.*;

public class PhonebookContact {
	private String contactPerson;
	private String phoneNumber;
	
	PhonebookContact(String contactPerson, String phoneNumber) {
		this.contactPerson = contactPerson;
		this.phoneNumber = phoneNumber;
	}
	
	public String getContactPerson(String contactPerson) {
		return contactPerson;
	}
	
	public String getPhoneNumber(String phoneNumber) {
		return phoneNumber;
	}
	
	@Override
	public String toString() {
		return "Contact: " + this.contactPerson + "\nPhone Number: " + this.phoneNumber + "\n\n";
	}
}