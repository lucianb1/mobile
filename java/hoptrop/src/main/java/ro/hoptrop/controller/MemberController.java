package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.timetable.DayTimetable;
import ro.hoptrop.model.timetable.WeekTimetable;
import ro.hoptrop.service.MemberService;
import ro.hoptrop.web.response.member.MemberFeatureJsonResponse;
import ro.hoptrop.web.response.member.MemberJsonResponse;
import ro.hoptrop.web.response.timetable.DayTimetableJsonResponse;
import ro.hoptrop.web.response.timetable.WeekTimetableJsonResponse;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Luci on 13-Dec-16.
 */
@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(String token) {

    }

    @RequestMapping(value = "/company/{companyID}", method = RequestMethod.GET)
    public List<MemberJsonResponse> getMembersForCompany(@PathVariable int companyID) {
        return memberService.getMembersForCompany(companyID).stream().map(item -> mapToMemberResponse(item)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{memberID}/domain/{domainID}/services", method = RequestMethod.GET)
    public List<MemberFeatureJsonResponse> getMemberServices(@PathVariable int memberID, @PathVariable int domainID) {
        return memberService.getMemberServices(memberID, domainID).stream().map(item -> MemberFeatureJsonResponse.from(item)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{memberID}/{date}", method = RequestMethod.GET)
    public DayTimetableJsonResponse getMemberTimetable(Integer memberID, Date date) {
        return mapToDayTimetableResponse(memberService.getDayTimetable(memberID, date));
    }

    @RequestMapping(value = "/{memberID}", method = RequestMethod.GET)
    public WeekTimetableJsonResponse getMemberDefaultTimetable(Integer memberID) {
        return mapToWeekTimetableResponse(memberService.getDefaultTimetable(memberID));
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
        return response;
    }

}
