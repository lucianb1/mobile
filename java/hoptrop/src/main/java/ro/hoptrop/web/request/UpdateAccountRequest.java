package ro.hoptrop.web.request;

import org.hibernate.validator.constraints.Length;

/**
 * Created by Luci on 11-Dec-16.
 */
public class UpdateAccountRequest {

    @Length(min = 6, max = 10)
    private String phone;

    @Length(min = 3, max = 50)
    private String name;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
