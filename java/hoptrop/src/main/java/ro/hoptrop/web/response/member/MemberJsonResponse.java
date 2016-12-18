package ro.hoptrop.web.response.member;

/**
 * Created by Luci on 18-Dec-16.
 */
public class MemberJsonResponse {

    private int id;
    private String name;

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
}
