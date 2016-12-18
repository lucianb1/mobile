package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.service.MemberService;
import ro.hoptrop.web.response.member.MemberJsonResponse;

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

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public void getMemberDetails(@PathVariable int id) {

    }

    @RequestMapping(value = "/company/{companyID}", method = RequestMethod.GET)
    public List<MemberJsonResponse> getMembersForCompany(@PathVariable int companyID) {
        return memberService.getMembersForCompany(companyID).stream().map(item -> mapToMemberResponse(item)).collect(Collectors.toList());
    }


    public MemberJsonResponse mapToMemberResponse(Member member) {
        return new MemberJsonResponse()
                .setId(member.getId())
                .setName(member.getName());
    }

}
