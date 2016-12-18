package ro.hoptrop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.token.member.MemberToken;
import ro.hoptrop.repository.*;
import ro.hoptrop.service.MemberService;

import java.util.List;

/**
 * Created by Luci on 13-Dec-16.
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MemberTokenRepository memberTokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Override
    public List<Member> getMembersForCompany(int companyID) {
        return memberRepository.findMembersByCompany(companyID);
    }

    @Override
    public void registerByToken(int accountID, String token) {
        MemberToken memberToken = memberTokenRepository.findByToken(token);
        Account account = accountRepository.findAccount(accountID);
        if (AccountType.USER.equals(account.getType())) {
            AccountType newType = memberToken.isAdmin() ? AccountType.MEMBER_ADMIN : AccountType.MEMBER;
            accountRepository.updateAccountType(accountID, newType);
            memberRepository.createMember(accountID, memberToken.getCompanyID(), account.getName());
        }
    }

    @Override
    public void setDefaultTimetable(int memberID, short[][] hours) {
        timetableRepository.updateDefaultTimetable(memberID, hours);
    }

}
