package ro.hoptrop.web.response.member;

import java.util.List;

/**
 * Created by Luci on 18-Dec-16.
 */
public class MemberJsonResponse {

    private int id;
    private String name;
    private List<MemberServiceJsonResponse> service;

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

    public List<MemberServiceJsonResponse> getService() {
        return service;
    }

    public void setService(List<MemberServiceJsonResponse> service) {
        this.service = service;
    }
}
