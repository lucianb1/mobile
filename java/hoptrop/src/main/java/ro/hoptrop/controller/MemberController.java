package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.timetable.DayTimetable;
import ro.hoptrop.model.timetable.WeekTimetable;
import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.service.MemberService;
import ro.hoptrop.web.request.timetable.UpdateWeekTimetableRequest;
import ro.hoptrop.web.response.member.MemberJsonResponse;
import ro.hoptrop.web.response.member.MemberServiceJsonResponse;
import ro.hoptrop.web.response.timetable.DayTimetableJsonResponse;
import ro.hoptrop.web.response.timetable.WeekTimetableJsonResponse;

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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(String token) {

    }

    @RequestMapping(value = "/companies/{companyID}/members", method = RequestMethod.GET)
    public List<MemberJsonResponse> getMembersForCompany(@PathVariable int companyID) {
        return memberService.getMembersForCompany(companyID).stream().map(this::mapToMemberResponse).collect(Collectors.toList());
    }

    @RequestMapping(value = "/members/{memberID}/services", method = RequestMethod.GET)
    public List<MemberServiceJsonResponse> getMemberServices(@PathVariable int memberID) {
        return memberService.getMemberServices(memberID).stream().map(MemberServiceJsonResponse::from).collect(Collectors.toList());
    }


    @RequestMapping(value = "/members/{memberID}/timetable", method = RequestMethod.GET)
    public DayTimetableJsonResponse getMemberTimetable(@PathVariable Integer memberID, @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy") Date date) {
        return mapToDayTimetableResponse(memberService.getDayTimetable(memberID, date));
    }

    @RequestMapping(value = "/{memberID}", method = RequestMethod.GET)
    public WeekTimetableJsonResponse getMemberDefaultTimetable(Integer memberID) {
        return mapToWeekTimetableResponse(memberService.getDefaultTimetable(memberID));
    }


    //TODO has member role
    @RequestMapping(value = "/secure/member/timetable/", method = RequestMethod.POST)
    public void createTimetable(@Valid @RequestBody UpdateWeekTimetableRequest request, @AuthenticationPrincipal PrincipalUser principalUser) {
        memberService.createDefaultTimetable(principalUser.getMemberID(), request.getTimetable());
    }


    public MemberJsonResponse mapToMemberResponse(Member member) {
        return new MemberJsonResponse()
                .setId(member.getId())
                .setName(member.getName());
    }

    public WeekTimetableJsonResponse mapToWeekTimetableResponse(WeekTimetable timetable) {
        WeekTimetableJsonResponse response = new WeekTimetableJsonResponse();
        return response;
    }

    public DayTimetableJsonResponse mapToDayTimetableResponse(DayTimetable timetable) {
        DayTimetableJsonResponse response = new DayTimetableJsonResponse();
        response.setDate(timetable.getDate());
        response.setTimetable(timetable.getTimetable());
        return response;
    }

}
