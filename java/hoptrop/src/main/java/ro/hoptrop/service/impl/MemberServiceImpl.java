package ro.hoptrop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.hoptrop.core.exceptions.BadRequestException;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.member.MemberFeature;
import ro.hoptrop.model.timetable.DayTimetable;
import ro.hoptrop.model.timetable.WeekTimetable;
import ro.hoptrop.model.token.member.MemberToken;
import ro.hoptrop.repository.*;
import ro.hoptrop.service.MemberService;
import ro.hoptrop.web.request.member.CreateMemberServiceRequest;
import ro.hoptrop.web.request.member.UpdateMemberServiceRequest;

import java.util.Date;
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
    private MemberRepository memberRepository;

    @Autowired
    private MemberTokenRepository memberTokenRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Override
    public List<Member> getMembersForCompany(int companyID) {
        return memberRepository.findMembersByCompany(companyID);
    }

    @Override
    public List<MemberFeature> getMemberServices(int memberID) {
        return memberRepository.findMemberServices(memberID);//TODO
    }

    @Override
    public void updateMemberServices(int memberID, List<UpdateMemberServiceRequest> request) {
        memberRepository.updateMemberServices(memberID, request);
    }

    @Override
    public void createMemberServices(int memberID, List<CreateMemberServiceRequest> request) {
        memberRepository.createMemberServices(memberID, request);
    }

    @Override
    public void deleteMemberServices(int memberID, List<Integer> ids) {
        memberRepository.deleteMemberServices(memberID, ids);
    }

    @Override
    public Member registerByToken(int accountID, String token) {
        Account account = accountRepository.findAccount(accountID);
        if (AccountType.USER.equals(account.getType())) {
            MemberToken memberToken = memberTokenRepository.findByToken(token);
            AccountType newType = memberToken.isAdmin() ? AccountType.MEMBER_ADMIN : AccountType.MEMBER;
            accountRepository.updateAccountType(accountID, newType);
            return memberRepository.createMember(accountID, memberToken.getCompanyID(), account.getName());
        } else {
            //TODO update the member in case of member admin swap
            throw new BadRequestException("The user is already a member");
        }

    }

    @Override
    public void createDefaultTimetable(int memberID, String timetable) {
        timetableRepository.createDefaultTimetable(memberID, null);
//        memberRepository.activateMember(memberID);
        //TODO set some flag
    }

    @Override
    public void updateDefaultTimetable(int memberID, String timetable) {

        timetableRepository.updateDefaultTimetable(memberID, null);
    }

    @Override
    public WeekTimetable getDefaultTimetable(int memberID) {
        return timetableRepository.getMemberDefaultTimetable(memberID);
    }

    @Override
    public DayTimetable getDayTimetable(int memberID, Date date) {
        return timetableRepository.getMemberDayTimetable(memberID, date);
    }

    @Override
    public void deleteMember(int companyID, int memberID) {

    }

}
