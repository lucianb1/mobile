package ro.hoptrop.service;

import ro.hoptrop.model.member.Member;

import java.util.List;

/**
 * Created by Luci on 13-Dec-16.
 */
public interface MemberService {

    List<Member> getMembersForCompany(int companyID);

    void registerByToken(int accountID, String token);

    void setDefaultTimetable(int memberID, short[][] hours);

}
