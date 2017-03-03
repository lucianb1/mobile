package ro.hoptrop.web.request.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Luci on 14-Jan-17.
 */
public class NewAppointmentRequest {

    private Integer serviceID;

    @NotNull
    @Min(0)
    @Max(95)
    private Integer hour; // in quarter

    @JsonFormat(pattern = "dd/MM/yyy")
    private Date date;

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getServiceID() {
        return serviceID;
    }

    public Integer getHour() {
        return hour;
    }


}
