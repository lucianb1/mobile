package ro.hoptrop.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.hoptrop.core.exceptions.AlreadyExistsException;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;
import ro.hoptrop.repository.AccountRepository;
import ro.hoptrop.service.AccountService;

/**
 * Created by Luci on 11-Dec-16.
 */
@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOG = Logger.getLogger(AccountServiceImpl.class);


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account registerUser(String email, String password, String name, String phone) throws AlreadyExistsException {
        try {
            accountRepository.findAccount(email);
            throw new AlreadyExistsException("Email already used");
        } catch (NotFoundException e) { // the email is not used yet
            return accountRepository.createAccount(email, passwordEncoder.encode(password), name, phone, AccountType.USER);
        }
    }

    @Override
    public void updateAccountDetails(int accountID, String newName, String newPhone) {
        Account account = accountRepository.findAccount(accountID);
        newName = newName == null ? account.getName() : newName;
        newPhone = newPhone == null ? account.getPhone() : newPhone;
        accountRepository.updateAccountDetails(accountID, newName, newPhone);
    }
}
