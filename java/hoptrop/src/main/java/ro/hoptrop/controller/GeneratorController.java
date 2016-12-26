package ro.hoptrop.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;
import ro.hoptrop.model.company.Company;
import ro.hoptrop.model.company.Location;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.token.member.MemberToken;
import ro.hoptrop.service.AccountService;
import ro.hoptrop.service.CompanyService;
import ro.hoptrop.service.DomainService;
import ro.hoptrop.service.MemberService;
import ro.hoptrop.web.request.member.CreateMemberServiceRequest;

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
        List<Integer> companyDomains = domainService.getAllCompanyDomains().stream().map(item -> item.getId()).collect(Collectors.toList());
        for (int i = 0; i < count; i++) {
            Integer domain = companyDomains.get(random.nextInt(companyDomains.size()));
            Company newCompany = companyService.createCompany(randomString(6), generateRandomLocation(), new HashSet<>(Arrays.asList(domain)), 0);
            Account user1 = createUser();
            Account user2 = createUser();

            MemberToken membersToken = companyService.getMembersToken(newCompany.getId());
            LOG.info("Members:");
            Member member1 = memberService.registerByToken(user1.getId(), membersToken.getToken());
            Member member2 = memberService.registerByToken(user2.getId(), membersToken.getToken());

            memberService.createMemberServices(member1.getId(), createServicesRequest(domain));
            memberService.createMemberServices(member2.getId(), createServicesRequest(domain));

            memberService.createDefaultTimetable(member1.getId(), createDefeaultTimetable());
            memberService.createDefaultTimetable(member2.getId(), createDefeaultTimetable());
            //TODO set timetable for activation
        }
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
