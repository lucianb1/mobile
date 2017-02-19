package ro.hoptrop.web.response.member;

import ro.hoptrop.model.member.MemberFeature;

/**
 * Created by Luci on 24-Dec-16.
 */
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.ANY)
public class MemberServiceJsonResponse {

    private int id;
    private String name;
    private int duration;

    public MemberServiceJsonResponse(int id, String name, int duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public static MemberServiceJsonResponse from(MemberFeature feature) {
        return new MemberServiceJsonResponse(feature.getId(), feature.getName(), feature.getDuration());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }
}
