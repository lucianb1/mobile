package ro.hoptrop.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.core.exceptions.SecurityException;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;
import ro.hoptrop.model.facebook.FacebookUser;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.token.RememberMeToken;
import ro.hoptrop.repository.AccountRepository;
import ro.hoptrop.repository.MemberRepository;
import ro.hoptrop.repository.RememberMeTokenRepository;
import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.service.AuthenticationService;
import ro.hoptrop.utils.FacebookUtils;
import ro.hoptrop.utils.TokenUtils;
import ro.hoptrop.web.response.MobileLoginResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger LOG = Logger.getLogger(AuthenticationServiceImpl.class);
    private static final String NO_KEYWORD = "NO_KEYWORD";

    @Autowired
    private RememberMeTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FacebookUtils facebookUtils;

    @Override
    public PrincipalUser authenticateByToken(String token) {
        RememberMeToken rememberMeToken = null;
        try {
            rememberMeToken = tokenRepository.findToken(token);
        } catch (NotFoundException e) {
            throw new SecurityException("Token not found");
        }
        Account account = accountRepository.findAccount(rememberMeToken.getAccountID());
        return mapToPrincipal(account);
    }

    @Override
    public MobileLoginResponse mobileLogin(String email, String password) {
        Account account = null;
        try {
            account = accountRepository.findAccount(email);
        } catch (NotFoundException e) {
            throw new SecurityException("Invalid email or passwor");
        }
        if (passwordEncoder.matches(password, account.getPassword())) {
            String token = createTokenForAccount(account.getId());
            LOG.info("Successfully login for user with email: " + email);
            return mapToLoginResponse(account, token);
        } else {
            throw new SecurityException("Invalid email or password");
        }
    }

    @Override
    public void mobileLogout(String token) {
        tokenRepository.deleteToken(token);
    }

    @Override
    public MobileLoginResponse loginAccount(Account account) {
        String token = createTokenForAccount(account.getId());
        return mapToLoginResponse(account, token);
    }

    @Override
    public MobileLoginResponse loginWithFacebook(String token) {
        String longToken = facebookUtils.createFacebookLongLivedToken(token);
        FacebookTemplate facebook = new FacebookTemplate(longToken);
        String[] fields = {"email", "name"};
        FacebookUser loadedUser = facebook.fetchObject("me", FacebookUser.class, fields);
        String email = loadedUser.getEmail();
        String name = loadedUser.getName();
        Account account = null;
        try {
            account = accountRepository.findAccount(email);
        } catch (NotFoundException e) {
            account = accountRepository.createAccount(email, passwordEncoder.encode(TokenUtils.generateToken()), name, NO_KEYWORD, AccountType.USER);
        }
        return this.loginAccount(account);
    }

    private String createTokenForAccount(int accountID) {
        String token = TokenUtils.generateToken();
        while (tokenRepository.tokenExists(token)) {
            token = TokenUtils.generateToken();
        }
        tokenRepository.createToken(accountID, token);
        return token;
    }

    private PrincipalUser mapToPrincipal(Account account) {
        PrincipalUser principal = new PrincipalUser(account.getEmail(), "", createAuthorities(account.getType().name()))
            .setName(account.getName())
            .setPhone(account.getPhone())
            .setId(account.getId());
        //TODO maybe store in account table also the member/company id
        if (!account.getType().equals(AccountType.USER)) { // is member
            Member member = memberRepository.findMemberByAccount(account.getId());
            principal.setMemberID(member.getId());
            principal.setCompanyID(member.getCompanyID());
        }
        return principal;
    }

    private MobileLoginResponse mapToLoginResponse(Account account, String token) {
        return new MobileLoginResponse()
            .setToken(token)
            .setUser(mapToPrincipal(account));
    }

    private List<GrantedAuthority> createAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

}
