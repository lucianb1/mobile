package ro.hoptrop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.hoptrop.core.converter.DayTimetableConverter;
import ro.hoptrop.core.exceptions.OperationNotAllowedException;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.member.MemberFeature;
import ro.hoptrop.model.timetable.DayTimetable;
import ro.hoptrop.model.timetable.TimetableFlags;
import ro.hoptrop.repository.AppointmentRepository;
import ro.hoptrop.repository.MemberRepository;
import ro.hoptrop.repository.TimetableRepository;
import ro.hoptrop.service.AppointmentService;
import ro.hoptrop.web.request.appointment.NewAppointmentRequest;

import java.util.Date;

/**
 * Created by Luci on 14-Jan-17.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void makeAppointment(int memberID, int userID, NewAppointmentRequest request) {
        Member member = memberRepository.findMember(memberID);
        DayTimetable dayTimetable = timetableRepository.getMemberDayTimetable(memberID, new Date());
        MemberFeature memberService = memberRepository.findMemberService(request.getServiceID());
        if (member.getId() != memberService.getMemberID()) {
            throw new RuntimeException("The service does not belong to this member");
        }
        int duration = memberService.getDuration();
        short[] timetable = dayTimetable.getTimetable();

        Integer startTime = request.getHour();
        for (int i = startTime; i < startTime + duration; i++) {
            if (timetable[i] != TimetableFlags.FREE_FLAG) {
                throw new OperationNotAllowedException();
            }
            timetable[i] = 1;
        }
        timetableRepository.updateDayTimetable(memberID, request.getDate(), DayTimetableConverter.fromShortAray(timetable));
        appointmentRepository.createAppointment(memberID, userID, request.getDate(), request.getHour(), request.getServiceID());
    }


}
