package ro.hoptrop.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.repository.AccountRepository;
import ro.hoptrop.service.ForgotPasswordService;
import ro.hoptrop.service.MailService;
import ro.hoptrop.utils.PasswordUtils;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private static final Logger LOG = Logger.getLogger(ForgotPasswordServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;


    @Override
    public void resetPassword(String email) {
        Account account = accountRepository.findAccount(email);
        String plainPassword = PasswordUtils.generateRandomPassword();
        String hashedPassword = passwordEncoder.encode(plainPassword);
        accountRepository.updateAccountPassword(account.getId(), hashedPassword);
        mailService.sendNewPasswordMail(email, plainPassword);
    }

}
