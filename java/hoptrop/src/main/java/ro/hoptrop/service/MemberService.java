package ro.hoptrop.service;

import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.member.MemberFeature;
import ro.hoptrop.web.request.member.CreateMemberServiceRequest;
import ro.hoptrop.web.request.member.UpdateMemberServiceRequest;

import java.util.List;

/**
 * Created by Luci on 13-Dec-16.
 */
public interface MemberService {

    List<Member> getMembersForCompany(int companyID);

    List<Member> getActiveMembersForCompany(int companyID);

    List<MemberFeature> getMemberServices(int memberID, int domainID);

    void updateMemberServices(int memberID, List<UpdateMemberServiceRequest> request);

    void createMemberServices(int memberID, List<CreateMemberServiceRequest> request);

    void deleteMemberServices(int memberID, List<Integer> ids);

    Member registerByToken(int accountID, String token); //TODO return something

    void setDefaultTimetable(int memberID, short[][] hours);

    void deleteMember(int companyID, int memberID); //company id for validation

}
