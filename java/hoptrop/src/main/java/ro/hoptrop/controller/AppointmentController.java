package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.service.AppointmentService;
import ro.hoptrop.web.request.appointment.NewAppointmentRequest;

import javax.validation.Valid;

/**
 * Created by Luci on 13-Dec-16.
 */
@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(method = RequestMethod.POST, value ="/secure/appointment/member/{memberID}")
    public void makeAppointment(@Valid @RequestBody NewAppointmentRequest request,
                                @PathVariable Integer memberID,
                                @AuthenticationPrincipal PrincipalUser principalUser) {
        appointmentService.makeAppointment(memberID, principalUser.getId(), request);
    }


}
