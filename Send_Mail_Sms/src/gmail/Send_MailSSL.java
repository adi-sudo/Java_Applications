package gmail;

public class Send_MailSSL {

	public static void main(String[] args) {
		//from,password,to,subject,message
		Mailer.send("@gmail.com", "password", "@gmail.com", "Hello Java", "This is first java application message");

	}

}
