package ro.hoptrop.web.response.member;

import java.util.List;

/**
 * Created by Luci on 18-Dec-16.
 */
public class MemberJsonResponse {

    private int id;
    private String name;
    private List<MemberServiceJsonResponse> services;

    public int getId() {
        return id;
    }

    public MemberJsonResponse setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MemberJsonResponse setName(String name) {
        this.name = name;
        return this;
    }

    public List<MemberServiceJsonResponse> getServices() {
        return services;
    }

    public void setServices(List<MemberServiceJsonResponse> services) {
        this.services = services;
    }
}
