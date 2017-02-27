package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ro.hoptrop.core.converter.WeekTimetableConverter;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.timetable.DayTimetable;
import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.service.MemberService;
import ro.hoptrop.web.request.timetable.SetWeekTimetableRequest;
import ro.hoptrop.web.response.member.MemberJsonResponse;
import ro.hoptrop.web.response.member.MemberServiceJsonResponse;
import ro.hoptrop.web.response.timetable.DayTimetableJsonResponse;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Luci on 13-Dec-16.
 */
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/members/register", method = RequestMethod.POST)
    public Member register(@AuthenticationPrincipal PrincipalUser principalUser, String token) {
        return memberService.registerByToken(principalUser.getId(), token);
    }

    @RequestMapping(value = "/members/companies/{companyID}/members", method = RequestMethod.GET)
    public List<MemberJsonResponse> getMembersForCompany(@PathVariable int companyID) {
        return memberService.getMembersForCompany(companyID).stream().map(this::mapToMemberResponse).collect(Collectors.toList());
    }

    @RequestMapping(value = "/members/{memberID}/services", method = RequestMethod.GET)
    public List<MemberServiceJsonResponse> getMemberServices(@PathVariable int memberID) {
        return memberService.getMemberServices(memberID).stream().map(MemberServiceJsonResponse::from).collect(Collectors.toList());
    }


    @RequestMapping(value = "/members/{memberID}/timetable/day", method = RequestMethod.GET)
    public DayTimetableJsonResponse getMemberDayTimetable(@PathVariable Integer memberID, @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy") Date date) {
        return mapToDayTimetableResponse(memberService.getDayTimetable(memberID, date));
    }

    @RequestMapping(value = "/members/{memberID}/timetable/week", method = RequestMethod.GET)
    public DayTimetableJsonResponse getMemberWeekTimetable(@PathVariable Integer memberID, @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy") Date date) {
        return mapToDayTimetableResponse(memberService.getDayTimetable(memberID, date));
    }

    @RequestMapping(value = "/members/{memberID}", method = RequestMethod.GET)
    public String getMemberDefaultTimetable(Integer memberID) {
        return WeekTimetableConverter.toStringFormat(memberService.getDefaultTimetable(memberID).getWeekTimetable());
    }


    //TODO has member role
    @RequestMapping(value = "/secure/member/timetable/", method = RequestMethod.POST)
    public void createTimetable(@Valid @RequestBody SetWeekTimetableRequest request, @AuthenticationPrincipal PrincipalUser principalUser) {
        memberService.createDefaultTimetable(principalUser.getMemberID(), request.getTimetable());
    }


    public MemberJsonResponse mapToMemberResponse(Member member) {
        return new MemberJsonResponse()
                .setId(member.getId())
                .setName(member.getName());
    }


    public DayTimetableJsonResponse mapToDayTimetableResponse(DayTimetable timetable) {
        DayTimetableJsonResponse response = new DayTimetableJsonResponse();
        response.setDate(timetable.getDate());
        response.setTimetable(timetable.getTimetable());
        return response;
    }

}
