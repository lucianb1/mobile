package ro.hoptrop.service;

import ro.hoptrop.core.exceptions.AlreadyExistsException;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;

/**
 * Created by Luci on 11-Dec-16.
 */
public interface AccountService {

    Account registerAccount(String email, String password, String name, String phone, AccountType type) throws AlreadyExistsException;

    void updateAccountDetails(int accountID, String newName, String newPhone);

}
