package ro.hoptrop.service;

import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.member.MemberFeature;
import ro.hoptrop.model.timetable.DayTimetable;
import ro.hoptrop.model.timetable.WeekTimetable;
import ro.hoptrop.web.request.member.CreateMemberServiceRequest;
import ro.hoptrop.web.request.member.UpdateMemberServiceRequest;

import java.util.Date;
import java.util.List;

/**
 * Created by Luci on 13-Dec-16.
 */
public interface MemberService {

    List<Member> getMembersForCompany(int companyID);

    List<MemberFeature> getMemberServices(int memberID);

    void updateMemberServices(int memberID, List<UpdateMemberServiceRequest> request);

    void createMemberServices(int memberID, List<CreateMemberServiceRequest> request);

    void deleteMemberServices(int memberID, List<Integer> ids);

    Member registerByToken(int accountID, String token); //TODO return something

    void setDefaultTimetable(int memberID, String timetable);

    void updateDefaultTimetable(int memberID, String timetable);

    WeekTimetable getDefaultTimetable(int memberID);

    DayTimetable getDayTimetable(int memberID, Date date);

    void deleteMember(int companyID, int memberID); //company id for validation

}
