package ro.hoptrop.web.request.appointment;

/**
 * Created by Luci on 14-Jan-17.
 */
public class NewAppointmentRequest {

    private Integer memberID;
    private Integer serviceID;
    private Integer hour; // in quarter
    private Integer day;

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMemberID() {
        return memberID;
    }

    public Integer getServiceID() {
        return serviceID;
    }

    public Integer getHour() {
        return hour;
    }
}
