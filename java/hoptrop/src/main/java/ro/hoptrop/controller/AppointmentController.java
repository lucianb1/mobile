package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(method = RequestMethod.POST, value ="/secure/appointment")
    public void makeAppointment(@Valid @RequestBody NewAppointmentRequest request, @AuthenticationPrincipal PrincipalUser principalUser) {
        appointmentService.makeAppointment(principalUser.getId(), request);
    }


}
