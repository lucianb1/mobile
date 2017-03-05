package ro.hoptrop.test.config;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import ro.hoptrop.core.exceptions.AlreadyExistsException;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;
import ro.hoptrop.model.company.Location;
import ro.hoptrop.repository.AccountRepository;
import ro.hoptrop.service.AccountService;
import ro.hoptrop.service.AuthenticationService;
import ro.hoptrop.test.mock.MailServiceMock;
import ro.hoptrop.web.response.MobileLoginResponse;

import java.util.Random;

/**
 * Created by Luci on 04-Mar-17.
 */
@ContextConfiguration(classes = { TestConfig.class }, initializers = ConfigFileApplicationContextInitializer.class)
@WebAppConfiguration
public class BaseTestClass extends AbstractTestNGSpringContextTests {

    private final Random rand = new Random();

    @Autowired
    protected MailServiceMock mailServiceMock;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationService authenticationService;

    private Account createAdminUser() {
        try {
            Account adminAccount = accountRepository.findAccount(1);
            if (adminAccount.getType().equals(AccountType.ADMIN)) {
                return adminAccount;
            }
        } catch (NotFoundException e) {

        }

        String email = randomEmail();
        String password = randomString(10);
        String name = randomString(10);
        String phone = RandomStringUtils.randomNumeric(10);
        try {
            Account account = accountService.registerUser(email, password, name, phone);
            accountRepository.updateAccountType(account.getId(), AccountType.ADMIN);
            return account;
        } catch (AlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getSuperAdminToken() {
        Account admin = createAdminUser();
        MobileLoginResponse mobileLoginResponse = authenticationService.loginAccount(admin);
        return mobileLoginResponse.getToken();
    }

    protected int randomInt() {
        return randomInt(10000);
    }

    protected int randomInt(int max) {
        return rand.nextInt(max);
    }

    protected String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    protected String randomEmail() {
        return randomString(10).toLowerCase() + "@" + randomString(5).toLowerCase() + ".com";
    }

    protected Location randomLocation() {
        double lon = randomInt(360 - 180);
        double lat = randomInt(360 - 180);
        String address = randomString(10);
        return Location.builder()
            .setLatitude(lat)
            .setLongitude(lon)
            .setAddress(address)
            .build();
    }

}
