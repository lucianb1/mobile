package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.hoptrop.service.MemberService;

/**
 * Created by Luci on 13-Dec-16.
 */
//@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping
    public void register(String token) {

    }

    public void updateMemberDetails() {

    }

    @RequestMapping
    public void getMemberReviews() {

    }

    @RequestMapping
    public void getMemberDetails() {

    }



}
