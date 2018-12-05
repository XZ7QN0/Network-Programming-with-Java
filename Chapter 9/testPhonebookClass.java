public class testPhonebookClass {
	public static void main(String[] args) {
		PhonebookContact person1;// = new PhonebookContact();
		
		String contact = args.length > 0 ? args[1] : "";
		String phoneNumber = args.length > 0 ? args[2] : "";
		
		person1 = new PhonebookContact(contact, phoneNumber);
		
		System.out.println(person1);
	}
}