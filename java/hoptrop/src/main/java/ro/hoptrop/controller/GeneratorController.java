package ro.hoptrop.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.hoptrop.core.converter.WeekTimetableConverter;
import ro.hoptrop.core.exceptions.AlreadyExistsException;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;
import ro.hoptrop.model.company.Company;
import ro.hoptrop.model.company.Location;
import ro.hoptrop.model.domain.CompanyDomain;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.token.member.MemberToken;
import ro.hoptrop.repository.AccountRepository;
import ro.hoptrop.service.*;
import ro.hoptrop.web.request.member.CreateMemberServiceRequest;
import ro.hoptrop.web.response.MobileLoginResponse;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Luci on 24-Dec-16.
 */
@RestController
@RequestMapping("/generator")
public class GeneratorController {
    private static final Logger LOG = Logger.getLogger(GeneratorController.class);
    private static final Random random = new Random();
    @Autowired
    private AccountService accountService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private DomainService domainService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public MobileLoginResponse generateAdmin() throws AlreadyExistsException {
        String email = RandomStringUtils.randomAlphabetic(10) + "@" + RandomStringUtils.randomAlphanumeric(5) + ".com";
        String name = RandomStringUtils.randomAlphanumeric(10);
        String phone = RandomStringUtils.randomNumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10).toLowerCase();
        Account account = accountService.registerUser(email, password, name, phone);
        accountRepository.updateAccountType(account.getId(), AccountType.ADMIN);
        Account updatedAccount = accountRepository.findAccount(account.getId());
        MobileLoginResponse mobileLoginResponse = authenticationService.loginAccount(updatedAccount);
        return mobileLoginResponse;
    }

    @RequestMapping(value = "/{users}/{companies}", method = RequestMethod.GET)
    public void generate(@PathVariable int users, @PathVariable int companies) {
        createDomains();
        createUsers(users, AccountType.USER);
        createCompanies(companies);
    }

    private void createDomains() {
        domainService.createCompanyDomain("Frizerii", 0);
        domainService.createCompanyDomain("Machiaj", 0);
        domainService.createCompanyDomain("Masaj", 0);
        domainService.createCompanyDomain("Manichiura", 0);
    }

    private void createUsers(int count, AccountType accountType) {
        for (int i = 0; i < count; i++) {

            createUser();
        }
    }

    private Account createUser() {
        String password = RandomStringUtils.randomAlphanumeric(6);
        String email = RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@" + RandomStringUtils.randomAlphanumeric(3).toLowerCase() + ".com";
        try {
            Account account = accountService.registerUser(email, password, RandomStringUtils.randomAlphabetic(10).toLowerCase(), RandomStringUtils.randomNumeric(6));
            LOG.info(String.format("=====> Password for '%s' is '%s'", email, password));
            return account;
        } catch (Exception e) {
            LOG.info("Some exception", e);
        }

        return null;
    }

    private void createCompanies(int count) {
        List<Integer> companyDomains = domainService.getAllCompanyDomains().stream().map(CompanyDomain::getId).collect(Collectors.toList());
        for (int i = 0; i < count; i++) {
            Integer domain = companyDomains.get(random.nextInt(companyDomains.size()));
            Company newCompany = companyService.createCompany(randomString(6), generateRandomLocation(), new HashSet<>(Collections.singletonList(domain)), 0);
            LOG.info("Members:");
            Account user1 = createUser();
            Account user2 = createUser();

            MemberToken membersToken = companyService.getMembersToken(newCompany.getId());
            Member member1 = memberService.registerByToken(user1.getId(), membersToken.getToken());
            Member member2 = memberService.registerByToken(user2.getId(), membersToken.getToken());

            memberService.createMemberServices(member1.getId(), createServicesRequest(domain));
            memberService.createMemberServices(member2.getId(), createServicesRequest(domain));

            memberService.setDefaultTimetable(member1.getId(), randomTimetable());
            memberService.setDefaultTimetable(member2.getId(), randomTimetable());
            //TODO set timetable for activation
        }
    }

    private String randomTimetable() {
        short[][] timetable = new short[7][96];
        for (int i =0 ;i < 7; i++) {
            for (int j = 20; j < 30; j++) {
                timetable[i][j] = 1;
            }
            for (int j = 40; j < 50; j++) {
                timetable[i][j] = 1;
            }
        }
        return WeekTimetableConverter.toStringFormat(timetable);
    }

    private Location generateRandomLocation() {
        return Location.builder()
                .setAddress(String.format("Str. %s, Cluj-Napoca", randomString(5)))
                .setLongitude(randomLongitude())
                .setLatitude(randomLatitude())
                .build();
    }

    private String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length).toLowerCase();
    }

    private static double randomLongitude() {
        return 23.6236 + (random.nextInt(2000) - 1000) / 1000.0;
    }

    private static double randomLatitude() {
        return 46.7712 + (random.nextInt(2000) - 1000) / 1000.0;
    }

    private List<CreateMemberServiceRequest> createServicesRequest(int domainID) {
        int rand = random.nextInt(4) + 1;
        List<CreateMemberServiceRequest> result = new ArrayList<>();
        for (int i = 0; i < rand; i++) {
            result.add(randomServiceRequest(domainID));
        }
        return result;
    }

    private CreateMemberServiceRequest randomServiceRequest(int domainID) {
        CreateMemberServiceRequest request = new CreateMemberServiceRequest();
        request.setName(randomString(10));
        request.setDomainID(domainID);
        request.setOrderNr(0);
        request.setDuration(random.nextInt(5) + 1);
        return request;
    }

    private short[][] createDefeaultTimetable() {
        short[][] array = new short[7][96];
        for (int i = 0; i < 5; i++) {
            for (int j = 36; j < 76; j++) {
                array[i][j] = 1;
            }
        }
        return array;
    }

}
