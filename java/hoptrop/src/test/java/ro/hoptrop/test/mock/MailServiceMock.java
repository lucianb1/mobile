package ro.hoptrop.test.mock;

import ro.hoptrop.service.MailService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luci on 04-Mar-17.
 */
public class MailServiceMock implements MailService {

    private Map<String, String> newPasswordsMap = new HashMap<>();

    @Override
    public void sendNewPasswordMail(String email, String newPassword) {
        newPasswordsMap.put(email, newPassword);
    }

    public String getNewPasswordForEmail(String email) {
        return newPasswordsMap.get(email);
    }
}
