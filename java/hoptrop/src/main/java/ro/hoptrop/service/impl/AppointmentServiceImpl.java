package ro.hoptrop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.repository.MemberRepository;
import ro.hoptrop.repository.TimetableRepository;
import ro.hoptrop.service.AppointmentService;
import ro.hoptrop.web.request.appointment.NewAppointmentRequest;

/**
 * Created by Luci on 14-Jan-17.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Override
    public void makeAppointment(int accountID, NewAppointmentRequest request) {
        Member member = memberRepository.findMember(request.getMemberID());
//        timetableRepository.
    }


}
