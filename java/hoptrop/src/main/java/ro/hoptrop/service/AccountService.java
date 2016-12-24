package ro.hoptrop.service;

import ro.hoptrop.core.exceptions.AlreadyExistsException;
import ro.hoptrop.model.account.Account;

/**
 * Created by Luci on 11-Dec-16.
 */
public interface AccountService {

    Account registerUser(String email, String password, String name, String phone) throws AlreadyExistsException;

    void updateAccountDetails(int accountID, String newName, String newPhone);

}
