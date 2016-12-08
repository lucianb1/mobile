package ro.hoptrop.service;

public interface MailService {

	void sendNewPasswordMail(String email, String password);

}
